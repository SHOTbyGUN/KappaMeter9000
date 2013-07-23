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
public class Renew extends Command {

    public Renew() {
        super("!renew");
    }
    
    @Override
    public String getHelp() {
        return "Type !renew to restart channels expiration timer";
    }

    @Override
    protected void execute(String[] chunks, String sender) throws Exception {
        String channelName = Channel.makeChannelName(chunks[0]);
        if(Static.channels.containsKey(channelName)) {
            Channel channel = Static.channels.get(channelName);
            channel.renewExpiration();
            sendMessage("Expiration renewed for channel " + channel.getCleanName(), sender);
        } else {
            sendMessage("Channel " + Channel.makeCleanChannelName(channelName) + " not found", sender);
        }
    }
    
}
