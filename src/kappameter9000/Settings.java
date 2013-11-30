/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author s12100
 */
public class Settings {
    
    private Properties properties;
    private Properties defaults = new Properties();
    
    public Settings() {
        defaults.setProperty("ircServer", "irc.twitch.tv");
        defaults.setProperty("ircPort", "6667");
        defaults.setProperty("ircUserName", "KappaMeter9000");
        defaults.setProperty("ircPassword", "oauth:g4yvostjhbvgumwh9v9vpi70kp7xzff");
        
        properties = new Properties(defaults);
    }
    
    
    public String get(String key) {
        String value = properties.getProperty(key);
        if(value != null)
            return value;
        else
            return "";
    }
    
    public void put(String key, String value) {
        properties.setProperty(key, value);
    }
    
    public void loadSettings() {
        try {
            FileInputStream in = new FileInputStream(Static.propertiesFile);
            properties.load(in);
            in.close();
            Static.log("Settings loaded");
        } catch (Exception ex) {
            Static.log("Error loading settings: " + ex.toString());
        }
    }
    
    public void saveSettings() {
        try {
            FileOutputStream out = new FileOutputStream(Static.propertiesFile);
            properties.store(out, "---No Comment---");
            out.close();
            Static.log("Settings saved");
        } catch (Exception ex) {
            Static.log("Error saving settings: " + ex.toString());
        }
    }
    
}
