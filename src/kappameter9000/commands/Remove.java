/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000.commands;

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
        if(sender.equalsIgnoreCase("shotbygun")) {
            if(Static.channels.containsKey(chunks[0])) {
                Static.channels.remove(chunks[0]);
                Static.ircbot.partChannel(chunks[0]);
                Static.ircbot.sendMessage(Static.homeChannel, sender + ": " + chunks[0] + " removed");
            }
        }
    }
    
}
