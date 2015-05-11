package com.infosupport.bi;


import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author MaartenKo
 */
public class DestinationWidget extends LabelWidget {
    private String transformation;
    private int mappingID;
    private int mappingSetID;
    private int destinationAttributeID;

    public DestinationWidget(Scene scene) {
        super(scene);
    }
    
    public DestinationWidget(Scene scene, String label) {
        super(scene, label);
        
    }
    @Override
    public String getLabel() {
        return  super.getLabel(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLabel(String label) {
        super.setLabel(label); //To change body of generated methods, choose Tools | Templates.
    }

    

}