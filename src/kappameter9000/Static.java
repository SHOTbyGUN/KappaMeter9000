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

/**
 *
 * @author shotbygun
 */
public class Static {
    
    // Main static variables
    public static final String APPNAME = "KappaMeter9000";
    public static final String version = "Dreamhack Edition - Version 1.04";
    
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
    
    
    public static void log(String message) {
        Static.settingsGUI.writeSysLog(message);
    }
    
    
    public static String getFolder() {
        String folder = System.getenv("APPDATA");
        folder += FileSystems.getDefault().getSeparator() + APPNAME;
        return folder;
    }
}
