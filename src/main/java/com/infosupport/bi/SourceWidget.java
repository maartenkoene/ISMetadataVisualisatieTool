package com.infosupport.bi;

import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author MaartenKo
 */
public class SourceWidget extends LabelWidget {

    private int sourceAttributeID;
    private String transformation;
    private int mappingID;
    private int mappingSetID;

    public SourceWidget(Scene scene) {
        super(scene);
    }

    public SourceWidget(Scene scene, String label) {
        super(scene, label);

    }

    @Override
    public String getLabel() {
        return super.getLabel(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLabel(String label) {
        super.setLabel(label); //To change body of generated methods, choose Tools | Templates.
    }

    public int getSourceAttributeID() {
        return sourceAttributeID;
    }

    public void setSourceAttributeID(int sourceAttributeID) {
        this.sourceAttributeID = sourceAttributeID;
    }

    public String getTransformation() {
        return transformation;
    }

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public int getMappingID() {
        return mappingID;
    }

    public void setMappingID(int mappingID) {
        this.mappingID = mappingID;
    }

    public int getMappingSetID() {
        return mappingSetID;
    }

    public void setMappingSetID(int mappingSetID) {
        this.mappingSetID = mappingSetID;
    }
}
