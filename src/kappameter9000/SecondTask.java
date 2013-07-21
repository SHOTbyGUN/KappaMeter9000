/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.TimerTask;
import javafx.application.Platform;
import javafx.scene.chart.XYChart;

/**
 *
 * @author shotbygun
 */
public class SecondTask extends TimerTask {
    
    int kappaAmount;
    int i, sum, kpm;
    int graphLenght = 600;

    @Override
    public void run() {
        // Get kappa amount in a second
        kappaAmount = Static.kappaCount.getAndSet(0);
        // Set kappa amount to 60 second pool
        Static.kappaPerSecond60.set(Static.currentSecond.getAndIncrement(), kappaAmount);
        if(Static.currentSecond.get() >= 60)
            Static.currentSecond.set(0);
        
        kpm = calculateKappaPerMinute();
        Static.kappaPerMinuteGraphData.set(Static.currentSecondGraph.getAndIncrement(), kpm);
        if(Static.currentSecondGraph.get() >= graphLenght)
            Static.currentSecondGraph.set(0);
        
        
        
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                Static.controller.setKPM(kpm);
                Static.controller.getLineChart().getData().setAll(generateChart());
            }
        });
        
    }
    
    private int calculateKappaPerMinute() {
        
        sum = 0;
        
        for(i = 0; i < 60; i++) {
            sum += Static.kappaPerSecond60.get(i);
        }
        return sum;
    }
    
    private XYChart.Series generateChart() {
        
        XYChart.Series<Integer,Integer> data = new XYChart.Series<>();
        data.setName("KPM");
        
        for(i = 1; i < graphLenght + 1; i++) {
            data.getData().add(new XYChart.Data(i, Static.kappaPerMinuteGraphData.get(getX(i))));
        }
        
        return data;
        
    }
    
    
    private int getX(int x) {
        
        // input number to decrease from currentSecond...
        x = Static.currentSecondGraph.get() - x;
        if(x < 0)
            x += graphLenght;
        
        return x;
    }
    
    
}
