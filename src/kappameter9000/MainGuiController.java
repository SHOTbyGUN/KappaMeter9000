/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author shotbygun
 */
public class MainGuiController implements Initializable {
    
    private boolean analyzing = false;

    /**
     * Initializes the controller class.
     */
    @FXML
    LineChart<Integer, Integer> kpmLineChart;
    
    @FXML
    ProgressBar kpmProgressBar;
    
    @FXML
    TextField channelName;
    
    @FXML
    Button buttonAnalyze;
    
    @FXML
    Label kpmNumber;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        kpmLineChart.setAnimated(false);
        kpmLineChart.setCreateSymbols(false);
    }    

    public void setKPM(int number) {
        kpmNumber.setText(Integer.toString(number));
        kpmProgressBar.setProgress((double)number / 100d);
    }
    
    public LineChart<Integer, Integer> getLineChart() {
        return kpmLineChart;
    }
    
    @FXML
    protected void channelGo() {
        try {
            
            if(analyzing) {
                
                // if we are analyzing... we stop analyzing
                
                if(Static.ircbot.isConnected())
                    Static.ircbot.disconnect();
                
                if(Static.timer != null)
                    Static.timer.cancel();
                
                buttonAnalyze.setText("Analyze");
                
                analyzing = false;
                
            } else {
                
                // We are not analyzing... start analyzing!
                
                if(Static.ircbot.isConnected()) {
                    Static.ircbot.disconnect();
                    Thread.sleep(500);
                }
                
                Static.ircbot.connect("irc.twitch.tv", 6667, "kakkaalumella");
                Static.ircbot.joinChannel(channelName.getText());
                
                Static.timer = new Timer("SecondTask", true);
                Static.timer.scheduleAtFixedRate(new SecondTask(), 1000, 1000);
                
                buttonAnalyze.setText("Stop");
                
                analyzing = true;
            }
            
        } catch (Exception ex) {
            Static.showMessage(ex.getMessage());
        }
        
    }
    
    
    
}
