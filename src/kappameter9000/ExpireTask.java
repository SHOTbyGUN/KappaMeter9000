/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

/**
 *
 * @author shotbygun
 */
public class ExpireTask extends TimerTask {
    
    // Second times 60
    public static final long interval = 1000 * 60;

    @Override
    public void run() {
        
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(new Date());
        
        Calendar notifyTime = Calendar.getInstance();
        notifyTime.setTime(new Date());
        notifyTime.add(Calendar.MINUTE, Static.expirationNotificationMinsBefore);
        
        Channel channel;
        
        for(Map.Entry channelKey : Static.channels.entrySet()) {
            channel = (Channel) channelKey.getValue();
            if(channel.getExpireTime().before(currentTime)) {
                channel.removeThisChannel();
                Static.ircbot.sendMessage(Static.homeChannel, "Channel " + channel.getCleanName() +  " has expired");
            } else if (channel.getExpireTime().before(notifyTime)) {
                channel.notifyExpiration();
            }
        }
    }
    
}
