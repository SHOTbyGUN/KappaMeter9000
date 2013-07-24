/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import javafx.scene.chart.XYChart;

/**
 *
 * @author shotbygun
 */
public class Channel {
    
    public static final int graphLenght = 600;
    
    private String channelName, cleanChannelName, requestedBy;
    private Calendar expireTime;
    private boolean expirationNotified = false;
    //private int record;
    
    public volatile AtomicInteger kappaAmount;
    public volatile AtomicIntegerArray kappaPerSecond60;
    public volatile AtomicIntegerArray kappaGraphData;
    
    private int sum, i, kpm;
    private Timer greetingTimer;
    private Calendar greetingFireTime;
    
    public Channel(String channelName, String requestedBy) throws Exception {
        
        if(channelName.equals(Static.homeChannel)) {
            throw new Exception("Cannot join home channel");
        }
        
        this.channelName = channelName;
        this.cleanChannelName = makeCleanChannelName(channelName);
        this.requestedBy = requestedBy;
        this.expireTime = Calendar.getInstance();
        renewExpiration();
        this.kappaAmount = new AtomicInteger(0);
        this.kappaPerSecond60 = new AtomicIntegerArray(60);
        this.kappaGraphData = new AtomicIntegerArray(graphLenght);
        
        // Start greeting timer
        greetingTimer = new Timer("GreetingTask", true);
        greetingFireTime = Calendar.getInstance();
        greetingFireTime.add(Calendar.MINUTE, GreetingTask.greetAfterSeconds);
        greetingTimer.schedule(new GreetingTask(this), greetingFireTime.getTime());
    }
    
    public Calendar getExpireTime() {
        return expireTime;
    }
    
    public final void renewExpiration() {
        expirationNotified = false;
        expireTime.setTime(new Date());
        expireTime.add(Calendar.HOUR_OF_DAY, Static.expirationDefaultHours);
    }
    
    public void notifyExpiration() {
        if(!expirationNotified) {
            expirationNotified = true;
            Static.ircbot.sendMessage(Static.homeChannel, 
                    "Channel " 
                    + getCleanName() 
                    + " is about to expire with kpm "
                    + getCurrentKpm()
                    + ", type !renew " 
                    + getCleanName() 
                    + " to remove expiration");
        }
    }
    
    public void removeThisChannel() {
        if(greetingTimer != null)
            greetingTimer.cancel();
        Static.ircbot.partChannel(getName());
        Static.channels.remove(getName());
    }
    
    public String getName() {
        return channelName;
    }
    
    public String getCleanName() {
        return cleanChannelName;
    }
    
    public int getCurrentKpm() {
        return kappaGraphData.get(getX(1));
    }
    
    public String getRequestedBy() {
        return requestedBy;
    }
    
    public void addKappa() {
        kappaAmount.incrementAndGet();
    }
    
    public int saveKappa(int kpmSecond, int graphSecond) {
        kappaPerSecond60.set(kpmSecond, kappaAmount.getAndSet(0));
        
        kpm = calculateKappaPerMinute();
        
        kappaGraphData.set(graphSecond, kpm);
        
        return kpm;
    }
    
    private int calculateKappaPerMinute() {
        
        sum = 0;
        
        for(i = 0; i < 60; i++) {
            sum += kappaPerSecond60.get(i);
        }
        return sum;
    }
    
    
    public XYChart.Series generateChart() {
        
        XYChart.Series<Integer,Integer> data = new XYChart.Series<>();
        data.setName(cleanChannelName);
        
        for(i = 1; i < graphLenght + 1; i++) {
            data.getData().add(new XYChart.Data(i, kappaGraphData.get(getX(i))));
        }
        
        return data;
        
    }
        
    public static String makeCleanChannelName(String channel) {
        return channel.replace("#", "").toLowerCase();
    }
    
    public static String makeChannelName(String channel) {
        if(!channel.startsWith("#")) {
            channel = "#" + channel;
        }
        channel = channel.toLowerCase();
        return channel;
    }
    
    
    private int getX(int x) {
        
        // input number to decrease from currentSecond...
        x = Static.currentSecondGraph.get() - x;
        if(x < 0)
            x += graphLenght;
        
        return x;
    }
    
    
}
