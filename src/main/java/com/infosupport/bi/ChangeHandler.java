/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MaartenKo
 */
public class ChangeHandler {

    private List<Object> changesList = new ArrayList<Object>();

    public void setChangesList(Object o) {
        this.changesList.add(o);
    }

    public List<Object> getChangesList() {
        return changesList;
    }

    public void checkDoubleEntries() {
        for (Object firstObjectToCompare : changesList) {
            if (firstObjectToCompare instanceof ChangeSource) {
                ChangeSource originalSourceCheck = (ChangeSource) firstObjectToCompare;
                for (Object object2 : changesList) {
                    if (object2 instanceof ChangeSource) {
                        ChangeSource compareSourceCheck = (ChangeSource) object2;
                        if (compareSourceCheck.compareDestinations(originalSourceCheck.getDestinationAttrID(), originalSourceCheck.getTransformation(),
                                originalSourceCheck.getOldDestination(), originalSourceCheck.getOldTransformation())) {
                            changesList.remove(firstObjectToCompare);
                            changesList.remove(object2);
                            break;
                        }
                    }
                }

            } else if (firstObjectToCompare instanceof ChangeDestination) {
                ChangeDestination originalDestinationCheck = (ChangeDestination) firstObjectToCompare;
                for (Object object3 : changesList) {
                    if (object3 instanceof ChangeDestination) {
                        ChangeDestination compareDestinationCheck = (ChangeDestination) object3;
                        if (compareDestinationCheck.compareDestinations(originalDestinationCheck.getDestinationAttrID(), originalDestinationCheck.getTransformation(),
                                originalDestinationCheck.getOldDestinationAttrID(), originalDestinationCheck.getOldTransformation())) {
                            changesList.remove(firstObjectToCompare);
                            changesList.remove(object3);
                            break;
                        }
                    }
                }
            }
        }
    }
}
