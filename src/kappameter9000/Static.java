/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
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
import sun.misc.JavaxSecurityAuthKerberosAccess;

/**
 *
 * @author shotbygun
 */
public class Static {
    
    // Dynamic configuration values
    
    public static volatile int maxChannels = 10;
    
    
    // Main static variables
    
    public static final String homeChannel = "#kappameter9000";
    public static volatile FXMLLoader mainGui;
    public static volatile MainGuiController controller;
    public static Timer timer;
    
    public static volatile IrcClient ircbot = new IrcClient();
    
    //public static volatile List<Channel> channels = Collections.synchronizedList(new ArrayList<Channel>());
    public static volatile ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();
    
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
    
}
