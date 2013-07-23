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
            
            // Check for asterix
            String channelName = Channel.makeChannelName(chunks[0]);
            
            if(Static.channels.containsKey(channelName)) {
                sendMessage("Channel already exists", sender);
            } else {
                // Check ok, new Channel
                Channel channel = null;
                
                try {
                    channel = new Channel(channelName, sender);
                    Static.channels.put(channel.getName(), channel);
                    Static.ircbot.joinChannel(channel.getName());
                    Static.ircbot.sendMessage(channel.getCleanName() + " request successful", sender);
                } catch (Exception ex) {
                    Static.log("Error joining channel " + channelName + " "+ ex.getMessage());
                    if(channel != null) {
                        channel.removeThisChannel();
                    }
                }
                
            }
        } else {
            sendMessage("Maximum amount of channels already in use: " + Static.maxChannels, sender);
        }
        
    }

    @Override
    public String getHelp() {
        return "Type !request !<channelName> to request me to analyze that specific channel";
    }
    
    
    
}
