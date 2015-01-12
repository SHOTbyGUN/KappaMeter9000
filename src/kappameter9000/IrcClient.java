/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import org.apache.commons.lang3.StringUtils;
import org.jibble.pircbot.PircBot;

/**
 *
 * @author shotbygun
 */
public class IrcClient extends PircBot {
    
    public IrcClient(String name) {
        this.setName(name);
        //this.setVerbose(true);
    }
    
    private int i;
    private int numKappas;
    
    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        try {
            
            // Find for kappa
            if(Static.channels.containsKey(channel)) {
                for(i = 0; i < Static.kappas.size(); i++) {
                    
                    // OLD 
                    /*
                    if(message.contains(Static.kappas.get(i))) {
                        // Kappa found, increase kappaCount
                        Static.channels.get(channel).addKappa();
                        break;
                    }
                    */
                    
                    // NEW
                    
                    numKappas = StringUtils.countMatches(message, Static.kappas.get(i));
                    Static.channels.get(channel).addKappas(numKappas);
                    
                    
                }
            }
        
        } catch (Exception ex) {
            Static.log("Error finding Kappa " + ex.getMessage());
        }

    }

    @Override
    protected void onDisconnect() {
        Static.log("Disconnected");
    }

    @Override
    protected void onConnect() {
        Static.log("Connected");
    }

    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        Static.log(sender + "@" + hostname + ": " + message);
    }

    @Override
    protected void onUnknown(String line) {
        Static.log("ServerMessage: " + line);
    }
    
    
    
}
