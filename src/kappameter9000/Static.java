/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.SystemUtils;

/**
 *
 * @author shotbygun
 */
public class Static {
    
    public static KappaMeter9000 kappaMeter9000;
    
    // Main static variables
    public static final String APPNAME = "KappaMeter9000";
    public static final String version = "Version 1.05d";
    
    // Properties file
    public static String propertiesFile = getFolder() + FileSystems.getDefault().getSeparator() + "settings.properties";
    
    public static Settings settings = new Settings();
    
    // GUI Variables
    public static volatile SettingsGUI settingsGUI;
    public static volatile FXMLLoader mainGui;
    public static volatile MainGuiController controller;
    public static Timer secondTimer;
    
    public static volatile IrcClient ircbot;
    
    public static volatile ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();
    public static volatile ArrayList<String> kappas = new ArrayList<>();
    
    public static volatile AtomicInteger currentSecond = new AtomicInteger(0);
    public static volatile AtomicInteger currentSecondGraph = new AtomicInteger(0);
    
    
    // Static methods
    
    public static void showMessage(String message) {
        final Stage dialogStage = new Stage();
        dialogStage.setTitle("Error message");
        Button closeButton = new Button("Ok");
        closeButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                dialogStage.close();
            }
        });
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(VBoxBuilder.create().
            children(new Text(message), closeButton).
            alignment(Pos.CENTER).padding(new Insets(5)).build()));
        dialogStage.show();
    }
    
    
    public static void log(final String message) {
        if(Static.settingsGUI == null) {
            System.out.println("-- BEFORE INIT LOG MESSAGE --");
            System.out.println(message);
        } else {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Static.settingsGUI.writeSysLog(message);
                }
                
            });
        }
        
    }
    
    
    public static String getFolder() {
        
        String os = System.getProperty("os.name").toUpperCase();
        String folder;
        
        if(SystemUtils.IS_OS_WINDOWS) {
            folder = System.getenv("APPDATA");
        } else if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_UNIX) {
            folder = System.getProperty("user.home")
                    + FileSystems.getDefault().getSeparator()
                    + ".config";
        } else {
            log("unknown operating system, settings cannot be saved. please fix me"
                    + " @ https://github.com/SHOTbyGUN/KappaMeter9000/blob/master/src/kappameter9000/Static.java#L95");
            return null;
        }
        
        
        folder += FileSystems.getDefault().getSeparator() + APPNAME;
        return folder;
    }
}
