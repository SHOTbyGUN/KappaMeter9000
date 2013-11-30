/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author shotbygun
 */
public class KappaMeter9000 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Static.settingsGUI = new SettingsGUI();
        Static.settingsGUI.createStage();
        
        
        Static.mainGui = new FXMLLoader(getClass().getResource("MainGui.fxml"));
        Static.mainGui.load();
        //Parent root = Static.mainGui.load());
        Parent root = Static.mainGui.getRoot();
        Static.controller = Static.mainGui.getController();
        Static.controller.start();
        
        Scene scene = new Scene(root);
        
        stage.setTitle("KappaMeter9000");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                shutdown();
            }
        });
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
    public static void shutdown() {
        try {
            if(Static.ircbot != null) {
                Static.ircbot.disconnect();
                Static.ircbot.dispose();
            }
        } catch (Exception ex) {
            Static.log(ex.getMessage());
        } finally {
            Platform.exit();
        }
    }
}