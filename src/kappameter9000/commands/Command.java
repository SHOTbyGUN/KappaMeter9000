/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000.commands;

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
        execute(cleanMessage(message), sender);
    }
    
    protected abstract void execute(String[] chunks, String sender);
    
    
    public String getCommandName() {
        return name;
    }
    
    private String[] cleanMessage(String message) {
        message = message.replace(name, "");
        message = message.trim();
        String[] chunks = message.split(" ");
        return chunks;
    }
    
}
