/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

/**
 *
 * @author MaartenKo
 */
public class ChangeSource {
    
    private final int sourceMappingID;
    private final int destinationAttrID;
    private final String transformation;
    private final int sourceAttrID;
    private final int mappingSetID;
    
    private final String oldTransformation;
    private final int oldDestination;
    
    public ChangeSource(int sourceMappingID, int destinationAttrID, String transformation, int sourceAttrID, int mappingSetID, String oldTransformation, int oldDestination) {
        this.sourceMappingID = sourceMappingID;
        this.destinationAttrID = destinationAttrID;
        this.transformation = transformation;
        this.sourceAttrID = sourceAttrID;
        this.mappingSetID = mappingSetID;
        this.oldTransformation = oldTransformation;
        this.oldDestination = oldDestination;
    }
    
    public int getSourceMappingID() {
        return sourceMappingID;
    }
    
    public int getDestinationAttrID() {
        return destinationAttrID;
    }
    
    public String getTransformation() {
        return transformation;
    }
    
    public int getSourceAttrID() {
        return sourceAttrID;
    }
    
    public int getMappingSetID() {
        return mappingSetID;
    }
    
    public String getOldTransformation() {
        return oldTransformation;
    }
    
    public int getOldDestination() {
        return oldDestination;
    }
    
    public boolean compareDestinations(int newDestinationID, String newTransformation, int previousDestination, String previousTransformation, int newSourceMappingID) {
        if (oldDestination == newDestinationID && oldTransformation.equals(newTransformation) && destinationAttrID == previousDestination && transformation.equals(previousTransformation) && sourceMappingID == newSourceMappingID) {
            return true;
        } else {
            return false;
        }
    }
}
