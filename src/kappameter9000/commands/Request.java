/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000.commands;

import kappameter9000.Channel;
import kappameter9000.Static;

/**
 *
 * @author shotbygun
 */
public class Request extends Command {
    
    public Request() {
        super("!request");
    }

    @Override
    public void execute(String[] chunks, String sender) {
        
        // max channels check
        if(Static.channels.size() < Static.maxChannels || sender.equalsIgnoreCase("shotbygun")) {
            
            
        
            String newChannel = chunks[0];
            
            // Check for asterix
            if(!newChannel.startsWith("#")) {
                newChannel = "#" + newChannel;
            }
            
            if(Static.channels.containsKey(newChannel)) {
                Static.ircbot.sendMessage(Static.homeChannel, sender + ": " + "Channel already included");
            } else {
                // Check ok, new Channel
                Channel channel = new Channel(newChannel);
                Static.channels.put(newChannel, channel);
                Static.ircbot.joinChannel(channel.getName());
                Static.ircbot.sendMessage(Static.homeChannel, sender + ": " + channel.getCleanName() + " request successful");
            }
        } else {
            Static.ircbot.sendMessage(
                    Static.homeChannel, sender + 
                    ": " + 
                    "Maximum amount of channels already in use: " 
                    + Static.maxChannels);
        }
        
    }
    
    
    
}
