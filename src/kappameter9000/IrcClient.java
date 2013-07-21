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
            System.out.println("Error IrcClientCommand " + ex.getMessage());
        }
        
        try {
            
            // Find for kappa
            if(Static.channels.containsKey(channel)) {
                if(message.startsWith("Kappa ") || 
                        message.contains(" Kappa ") || 
                        message.equals("Kappa") || 
                        message.endsWith(" Kappa")) {

                    // Kappa found, increase kappaCount

                    Static.channels.get(channel).kappaAmount.incrementAndGet();
                }
            }
        
        } catch (Exception ex) {
            System.out.println("Error finding Kappa " + ex.getMessage());
        }

    }
    
}
