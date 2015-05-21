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
public class ChangeDestination implements Change {

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

    public boolean inverse(Change candidate) {
        if (candidate instanceof ChangeDestination) {
            ChangeDestination castCandidate = (ChangeDestination) candidate;
            return (castCandidate.destinationAttrID == this.oldDestinationAttrID && castCandidate.transformation.equals(this.oldTransformation) && castCandidate.oldDestinationAttrID == this.destinationAttrID && castCandidate.oldTransformation.equals(this.transformation));
        } else {
            return false;
        }
    }

}
