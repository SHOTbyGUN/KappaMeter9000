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
public class Remove extends Command {
    
    public Remove() {
        super("!remove");
    }

    @Override
    protected void execute(String[] chunks, String sender) {
        
        // Make sure we have #channel
        String channelName = Channel.makeChannelName(chunks[0]);
        
        // Does channel exist ?
        if(Static.channels.containsKey(channelName)) {
            
            // Get channel
            Channel channel = Static.channels.get(channelName);
            
            // Only requester can remove channel ... or shotbygun
            if(sender.equals(channel.getRequestedBy()) || sender.equals("shotbygun")) {
                channel.removeThisChannel();
                sendMessage(channel.getCleanName() + " removed", sender);
            } else {
                sendMessage("You cannot remove this channel "
                        + channel.getCleanName(), sender);
            }
        } else {
            sendMessage(Channel.makeCleanChannelName(channelName) + " not found", sender);
        }
    }

    @Override
    public String getHelp() {
        return "Type !remove !<channelName> to remove channel, NOTE: you can only remove channel you have requested yourself";
    }
    
}
