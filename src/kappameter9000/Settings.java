/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author SHOT(by)GUN
 */
public class Settings {
    
    private Properties properties;
    private Properties defaults = new Properties();
    
    public Settings() {
        defaults.setProperty("ircServer", "irc.twitch.tv");
        // Event server is separate irc network from default irc.twitch.tv
        // Details: http://www.reddit.com/r/Twitch/comments/20ejf4/how_to_join_to_all_split_chat_channels_for_a/cg2s05v
        // Full chat server list available at: http://twitchstatus.com/
        // Note that it is not possible to join all servers with traditional IRC client (like this application)
        
        //second event server 199.9.251.213
        
        defaults.setProperty("ircPort", "6667");
        defaults.setProperty("ircUserName", "YourTwitchUserName");
        defaults.setProperty("ircPassword", "oauth:password");
        
        properties = new Properties(defaults);
        
        loadSettings();
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
    
    public final void loadSettings() {
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
            File propertiesSaveFile = new File(Static.propertiesFile);
            if(!propertiesSaveFile.getParentFile().exists()) {
                propertiesSaveFile.getParentFile().mkdirs();
            }
            FileOutputStream out = new FileOutputStream(Static.propertiesFile);
            properties.store(out, "---No Comment---");
            out.close();
            Static.log("Settings saved");
        } catch (Exception ex) {
            Static.log("Error saving settings: " + ex.toString());
        }
    }
    
}
