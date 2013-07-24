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
public class Who extends Command{
    
    public Who() {
        super("!who");
    }

    @Override
    public String getHelp() {
        return "Type !who <channelName> to see who has requested bot to that channel";
    }

    @Override
    protected void execute(String[] chunks, String sender) throws Exception {
        String channelName = Channel.makeChannelName(chunks[0]);
        if(Static.channels.containsKey(channelName)) {
            Channel channel = Static.channels.get(channelName);
            sendMessage("Channel "
                    + channel.getCleanName()
                    + " has been requested by: "
                    + Static.channels.get(channelName).getRequestedBy(), sender);
        } else {
            sendMessage("Channel "
                    + Channel.makeCleanChannelName(channelName)
                    + " was not found", sender);
        }
    }
    
}
