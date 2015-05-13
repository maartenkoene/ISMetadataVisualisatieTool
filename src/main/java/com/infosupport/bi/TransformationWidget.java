package com.infosupport.bi;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

/**
 *
 * @author MaartenKo
 */
public class TransformationWidget extends LabelWidget {

    private String transformation;
    private int mappingSetID;
    private int destinationAttributeID;
    private List<Integer> sourcesMappingID = new ArrayList();

    public TransformationWidget(Scene scene) {
        super(scene);
    }

    public TransformationWidget(Scene scene, String label) {
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

    public void setTransformation(String transformation) {
        this.transformation = transformation;
    }

    public int getMappingSetID() {
        return mappingSetID;
    }

    public void setMappingSetID(int mappingSetID) {
        this.mappingSetID = mappingSetID;
    }

    public int getDestinationAttributeID() {
        return destinationAttributeID;
    }

    public void setDestinationAttributeID(int destinationAttributeID) {
        this.destinationAttributeID = destinationAttributeID;
    }

    public List<Integer> getSourcesMappingID() {
        return sourcesMappingID;
    }

    public void addSource(int sourceMappingID) {
        sourcesMappingID.add(sourceMappingID);
    }

    public String getTransformation() {
        return transformation;
    }

    public void removeItemFromSourcesMappingID(int sourceID){
        int i =0;
        while (i < sourcesMappingID.size()){
            if(sourceID == sourcesMappingID.get(i)){
            sourcesMappingID.remove(i);
                break;
            }
            i++;
        }}
}
