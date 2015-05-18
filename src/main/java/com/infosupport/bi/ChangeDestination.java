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
public class ChangeDestination {

    private final int sourceMappingID;
    private final int destinationAttrID;
    private final String transformation;

    private final String oldTransformation;
    private final int oldDestinationAttrID;

    public ChangeDestination(int sourceMappingID, int destinationAttrID, String transformation, String oldTransformation, int oldDestinationAttrID) {
        this.sourceMappingID = sourceMappingID;
        this.destinationAttrID = destinationAttrID;
        this.transformation = transformation;
        this.oldTransformation = oldTransformation;
        this.oldDestinationAttrID = oldDestinationAttrID;
    }

    public boolean compareDestinations(int newDestinationID, String newTransformation, int previousDestination, String previousTransformation) {
        if (oldDestinationAttrID == newDestinationID && oldTransformation.equals(newTransformation) && destinationAttrID == previousDestination && transformation.equals(previousTransformation)) {
            return true;
        } else {
            return false;
        }
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

    public String getOldTransformation() {
        return oldTransformation;
    }

    public int getOldDestinationAttrID() {
        return oldDestinationAttrID;
    }

}
