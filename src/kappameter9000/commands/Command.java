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
public abstract class Command {
    
    private String name;
    
    public Command(String name) {
        this.name = name;
    }
    
    public void executeCommand(String message, String sender) {
        try {
            execute(cleanMessage(message), sender);
        } catch (Exception ex) {
            Static.log("Error in command: " + name + " "+ ex.getMessage());
        }
        
    }
    
    public abstract String getHelp();
    
    protected abstract void execute(String[] chunks, String sender) throws Exception;
    
    
    public String getCommandName() {
        return name;
    }
    
    private String[] cleanMessage(String message) {
        message = message.replace(name, "");
        message = message.trim();
        String[] chunks = message.split(" ");
        return chunks;
    }
    
    protected void sendMessage(String message, String sender) {
        Static.ircbot.sendMessage(Static.homeChannel, sender + ": " + message);
    }
    
}
