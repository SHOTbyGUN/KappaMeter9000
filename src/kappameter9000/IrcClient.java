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
    
    public IrcClient(String name) {
        this.setName(name);
        this.setVerbose(true);
    }
    
    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        try {
            
            // Find for kappa
            if(Static.channels.containsKey(channel)) {
                if(message.contains("Kappa")) {

                    // Kappa found, increase kappaCount

                    Static.channels.get(channel).kappaAmount.incrementAndGet();
                }
            }
        
        } catch (Exception ex) {
            Static.log("Error finding Kappa " + ex.getMessage());
        }

    }
    
}
