/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import javafx.scene.chart.XYChart;

/**
 *
 * @author shotbygun
 */
public class Channel {
    
    public static final int graphLenght = 600;
    
    private String channelName, cleanChannelName;
    //private int record;
    
    public volatile AtomicInteger kappaAmount;
    public volatile AtomicIntegerArray kappaPerSecond60;
    public volatile AtomicIntegerArray kappaGraphData;
    
    private int sum, i, kpm;
    
    public Channel(String channelName) throws Exception {
        this.channelName = makeChannelName(channelName);
        this.cleanChannelName = makeCleanChannelName(channelName);
        this.kappaAmount = new AtomicInteger(0);
        this.kappaPerSecond60 = new AtomicIntegerArray(60);
        this.kappaGraphData = new AtomicIntegerArray(graphLenght);
        Static.ircbot.joinChannel(this.channelName);
    }
    
    public void removeThisChannel() {
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
    
    // THIS IS USED IN LIST VIEW OBJECT (hopefully)
    @Override
    public String toString() {
        return getCleanName();
    }
}
