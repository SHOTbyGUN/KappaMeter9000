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
public class Help extends Command {
    
    public Help() {
        super("!help");
    }

    @Override
    protected void execute(String[] chunks, String sender) throws Exception {
        if(chunks != null && chunks.length > 0 && chunks[0].startsWith("!")) {
            
            // Parameter exists
            String commandName = chunks[0];
            for(Command command : Static.ircbot.getCommands()) {
                if(command.getCommandName().equalsIgnoreCase(commandName)) {
                    sendMessage(command.getHelp(), sender);
                    return;
                }
            }
            sendMessage("Unknown command " + commandName, sender);
            
        } else {
            
            // No parameters
            
            String messageOut = "";
            for(Command command : Static.ircbot.getCommands()) {
                messageOut += command.getCommandName() + " ";
            }
            sendMessage("list of commands: " + messageOut, sender);
        }
    }

    @Override
    public String getHelp() {
        return "say !help to get list of commands and !help !<commandname> to get help about that specific command";
    }
    
}
