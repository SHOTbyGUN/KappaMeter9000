/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import org.jibble.pircbot.PircBot;

/**
 *
 * @author shotbygun
 */
public class IrcClient extends PircBot {
    
    public IrcClient() {
        this.setName("KappaMeter9000");
    }
    
    
    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        if(channel.equalsIgnoreCase(Static.controller.channelName.getText())) {
            if(message.startsWith("Kappa ") || 
                    message.contains(" Kappa ") || 
                    message.equals("Kappa") || 
                    message.endsWith(" Kappa")) {
                Static.kappaCount.incrementAndGet();
            }
        }
    }
    
}
