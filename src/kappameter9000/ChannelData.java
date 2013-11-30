/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author shotbygun
 */
public class ChannelData implements Serializable {
    
    public static final String ALL_USERS = "/all";
    public static final String ALL_MODS = "/mods";
    
    private String channelName;
    private int kpmRecord;
    private LinkedList<String> authorizedUsers;
    
    public ChannelData(String channelName) {
        this.channelName = channelName;
        authorizedUsers = new LinkedList<>();
    }

    public String getChannelName() {
        return channelName;
    }

    public int getKpmRecord() {
        return kpmRecord;
    }

    public void setKpmRecord(int kpmRecord) {
        this.kpmRecord = kpmRecord;
    }
    
    public boolean isAuthorized(String name) {
        
        if(authorizedUsers.contains(ALL_USERS))
            return true;
        
        if(authorizedUsers.contains(name))
            return true;
        
        if(authorizedUsers.contains(ALL_MODS)) {
            // TODO
        }
        
        return false;
    }
    
}
