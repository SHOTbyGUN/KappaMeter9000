/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.TimerTask;

/**
 *
 * @author shotbygun
 */
public class GreetingTask extends TimerTask {

    private Channel channel;
    public static final int greetAfterSeconds = 60;
    
    public GreetingTask(Channel channel) {
        this.channel = channel;
    }
    
    @Override
    public void run() {
        Static.ircbot.sendMessage(channel.getName(), "I Was requested here! KPM Graph on my channel ;)");
    }
    
}
