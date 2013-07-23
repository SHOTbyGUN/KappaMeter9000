/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * FXML Controller class
 *
 * @author shotbygun
 */
public class MainGuiController implements Initializable {
    
    public static final double blue = 9d;
    public static final double green = 90d;
    public static final double yellow = 900d;
    public static final double red = 9000d;
    public static final double black = -1d;
    private double tempProgressBar;
    
    private boolean analyzing = false;
    
    private static final String[] barColorStyleClasses = { "black-bar", "red-bar", "yellow-bar", "green-bar", "blue-bar" };

    /**
     * Initializes the controller class.
     */
    @FXML
    LineChart<Integer, Integer> kpmLineChart;
    
    @FXML
    ProgressBar kpmProgressBar;
    
    @FXML
    Button buttonAnalyze;
    
    @FXML
    Label kpmNumber;
    
    @FXML
    Label versionLabel;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        versionLabel.setText(Static.version);
        kpmLineChart.setAnimated(false);
        kpmLineChart.setCreateSymbols(false);
        kpmProgressBar.getStylesheets().add(getClass().getResource("/css/myProgressBarStyle.css").toExternalForm());
    }
    
    private void setBarStyleClass(String barStyleClass) {
        kpmProgressBar.getStyleClass().removeAll(barColorStyleClasses);
        kpmProgressBar.getStyleClass().add(barStyleClass);
    }

    public void setKPM(int number) {
        kpmNumber.setText(Integer.toString(number));
        tempProgressBar = (double)number;
        if(number <= blue) {
            // Blue bar
            setBarStyleClass("blue-bar");
            kpmProgressBar.setProgress(tempProgressBar / blue);
        } else if(number <= green) {
            // Green bar
            setBarStyleClass("green-bar");
            kpmProgressBar.setProgress(tempProgressBar / green);
        } else if(number <= yellow) {
            // Yellow bar
            setBarStyleClass("yellow-bar");
            kpmProgressBar.setProgress(tempProgressBar / yellow);
        } else if (number <= red) {
            // Red bar
            setBarStyleClass("red-bar");
            kpmProgressBar.setProgress(tempProgressBar / red);
        } else {
            // Out of control
            setBarStyleClass("black-bar");
            kpmProgressBar.setProgress(black);
        }
        
    }
    
    public LineChart<Integer, Integer> getLineChart() {
        return kpmLineChart;
    }
    
    @FXML
    protected void startStopAction() {
        try {
            
            if(analyzing) {
                
                // if we are analyzing... we stop analyzing
                
                if(Static.ircbot.isConnected())
                    Static.ircbot.disconnect();
                
                if(Static.secondTimer != null)
                    Static.secondTimer.cancel();
                
                if(Static.expireTimer != null) {
                    Static.expireTimer.cancel();
                }
                
                buttonAnalyze.setText("Start");
                
                analyzing = false;
                
            } else {
                
                // We are not analyzing... start analyzing!
                
                if(Static.ircbot.isConnected()) {
                    Static.ircbot.disconnect();
                    Thread.sleep(500);
                }
                
                Static.ircbot.connect("irc.twitch.tv", 6667, "kakkaalumella");
                Static.ircbot.joinChannel(Static.homeChannel);
                
                // If there are channels... connect to them
                for (Map.Entry pairs : Static.channels.entrySet()) {
                    Channel channel = (Channel)pairs.getValue();
                    Static.ircbot.joinChannel(channel.getName());
                }
                
                Static.secondTimer = new Timer("SecondTask", true);
                Static.secondTimer.scheduleAtFixedRate(new SecondTask(), 1000, 1000);
                
                Static.expireTimer = new Timer("ExpireTask", true);
                Static.expireTimer.scheduleAtFixedRate(new ExpireTask(), 1000, ExpireTask.interval);
                
                
                buttonAnalyze.setText("Stop");
                
                analyzing = true;
            }
            
        } catch (Exception ex) {
            Static.showMessage(ex.getMessage());
        }
        
    }
    
    
    
}
