/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.ArrayList;
import kappameter9000.commands.*;
import org.jibble.pircbot.PircBot;

/**
 *
 * @author shotbygun
 */
public class IrcClient extends PircBot {
    
    private ArrayList<Command> commands = new ArrayList<>();
    
    public IrcClient() {
        this.setName("KappaMeter9000");
        commands.add(new Request());
        commands.add(new Remove());
        commands.add(new Help());
        commands.add(new Renew());
        commands.add(new Who());
        commands.add(new Kpm());
    }
    
    
    
    @Override
    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        
        try {
            // Process commands
            if(channel.equals(Static.homeChannel) && message.startsWith("!")) {
                for(Command command : commands) {
                    if(message.startsWith(command.getCommandName())) {
                        command.executeCommand(message, sender);
                    }
                }
            }
        } catch (Exception ex) {
            Static.log("Error IrcClientCommand " + ex.getMessage());
        }
        
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
    
    public ArrayList<Command> getCommands() {
        return commands;
    }
    
}
