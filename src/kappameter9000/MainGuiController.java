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
    
    private static final String[] barColorStyleClasses = { "black-bar", "red-bar", "yellow-bar", "green-bar", "blue-bar" };

    /**
     * Initializes the controller class.
     */
    @FXML
    LineChart<Integer, Integer> kpmLineChart;
    
    @FXML
    ProgressBar kpmProgressBar;
    
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
    
    public void start() {
        try {

            // We are not analyzing... start analyzing!

            Static.secondTimer = new Timer("SecondTask", true);
            Static.secondTimer.scheduleAtFixedRate(new SecondTask(), 1000, 1000);
            
        } catch (Exception ex) {
            Static.showMessage(ex.getMessage());
        }
        
    }
    
    
    
}
