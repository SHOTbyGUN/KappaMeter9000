/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.Timer;
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

/**
 *
 * @author shotbygun
 */
public class Static {
    
    public static volatile FXMLLoader mainGui;
    public static volatile MainGuiController controller;
    public static Timer timer;
    
    public static volatile IrcClient ircbot = new IrcClient();
    
    public static volatile AtomicInteger kappaCount = new AtomicInteger(0);
    public static volatile AtomicInteger currentSecond = new AtomicInteger(0);
    public static volatile AtomicInteger currentSecondGraph = new AtomicInteger(0);
    public static volatile AtomicIntegerArray kappaPerSecond60 = new AtomicIntegerArray(60);
    public static volatile AtomicIntegerArray kappaPerMinuteGraphData = new AtomicIntegerArray(600);
    
    
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
