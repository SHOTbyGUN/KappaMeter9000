/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kappameter9000;

import java.util.Iterator;
import java.util.Map;
import java.util.TimerTask;
import javafx.application.Platform;

/**
 *
 * @author shotbygun
 */
public class SecondTask extends TimerTask {
    
    int kpmSecond = 0, graphSecond = 0;
    int sumKpm;
    
    // Loop variables
    /*
    private Map.Entry pairs, pairs2;
    private Iterator it, it2;
    private Channel channel, channel2;
    */

    @Override
    public void run() {
        
        try {

            kpmSecond = Static.currentSecond.getAndIncrement();
            graphSecond = Static.currentSecondGraph.getAndIncrement();

            // Check for int overflow
            if(Static.currentSecond.get() >= 60)
                Static.currentSecond.set(0);
            if(Static.currentSecondGraph.get() >= Channel.graphLenght)
                Static.currentSecondGraph.set(0);

            sumKpm = 0;

            for (Map.Entry pairs : Static.channels.entrySet()) {
                sumKpm += ((Channel) pairs.getValue()).saveKappa(kpmSecond, graphSecond);
            }
            /*
            // Terribad way to iterate trough hashmap
            it = Static.channels.entrySet().iterator();
            while (it.hasNext()) {
                pairs = (Map.Entry)it.next();
                channel = (Channel)pairs.getValue();

                // Save kappa, and get sumKappa
                sumKpm += channel.saveKappa(kpmSecond, graphSecond);
                //it.remove(); // avoids a ConcurrentModificationException
            }*/
        
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                
                try {
                    // Update combined kpm
                    Static.controller.setKPM(sumKpm);

                    // Update graphs
                    Static.controller.getLineChart().getData().clear();

                    for (Map.Entry pairs : Static.channels.entrySet()) {
                        Static.controller.getLineChart().getData().add(((Channel) pairs.getValue()).generateChart());
                    }
                    
                } catch (Exception ex) {
                    Static.log("Error JavaFX GUI: " + ex.getMessage());
                }
                
                
            }
        });
        
        } catch (Exception ex) {
            Static.log("Error SecondTask " + ex.getMessage());
        }
        
    }
    
    
    

    
    
}
