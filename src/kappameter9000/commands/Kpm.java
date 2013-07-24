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
public class Kpm extends Command {
    
    public Kpm() {
        super("!kpm");
    }

    @Override
    public String getHelp() {
        return "Type !kpm <ChannelName> to see that channels current kpm";
    }

    @Override
    protected void execute(String[] chunks, String sender) throws Exception {
        if(chunks != null && chunks.length > 0) {
            String channelName = Channel.makeChannelName(chunks[0]);
            if(Static.channels.containsKey(channelName)) {
                Channel channel = Static.channels.get(channelName);
                sendMessage("Current kpm for channel "
                        + channel.getCleanName()
                        + " is: "
                        + channel.getCurrentKpm(), sender);
            } else {
                sendMessage("Cannot find channel " 
                        + Channel.makeCleanChannelName(channelName), sender);
            }
        } else {
            sendMessage(getHelp(), sender);
        }
    }
    
}
