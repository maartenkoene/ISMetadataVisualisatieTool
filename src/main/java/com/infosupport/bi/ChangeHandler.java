/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author MaartenKo
 */
public class ChangeHandler {

    private MSSQLQuery queryhandler;

    public ChangeHandler() {
    }

    public ChangeHandler(String dbString, String username, String password) {
        queryhandler = new MSSQLQuery(dbString, username, password);
    }

    private List<Object> changesList = new ArrayList<Object>();

    public void setChangesList(Object o) {
        this.changesList.add(o);
    }

    public List<Object> getChangesList() {
        return changesList;
    }

    public void checkDoubleEntries() {

        for (int i = 0; i < changesList.size(); i++) {
            if (changesList.get(i) instanceof ChangeSource) {
                ChangeSource originalSourceCheck = (ChangeSource) changesList.get(i);
                for (int j = 0; j < changesList.size(); j++) {
                    if (changesList.get(j) instanceof ChangeSource) {
                        ChangeSource compareSourceCheck = (ChangeSource) changesList.get(j);
                        if (compareSourceCheck.compareDestinations(originalSourceCheck.getDestinationAttrID(), originalSourceCheck.getTransformation(),
                                originalSourceCheck.getOldDestination(), originalSourceCheck.getOldTransformation())) {
                            changesList.set(i, null);
                            changesList.set(j, null);
                            break;
                        }
                    }
                }

            } else if (changesList.get(i) instanceof ChangeDestination) {
                ChangeDestination originalDestinationCheck = (ChangeDestination) changesList.get(i);
                for (int k = 0; k < changesList.size(); k++) {
                    if (changesList.get(k) instanceof ChangeDestination) {
                        ChangeDestination compareDestinationCheck = (ChangeDestination) changesList.get(k);
                        if (compareDestinationCheck.compareDestinations(originalDestinationCheck.getDestinationAttrID(), originalDestinationCheck.getTransformation(),
                                originalDestinationCheck.getOldDestinationAttrID(), originalDestinationCheck.getOldTransformation())) {
                            changesList.set(i, null);
                            changesList.set(k, null);
                            break;
                        }
                    }
                }
            }
        }
        changesList.removeAll(Collections.singleton(null));
    }

    public boolean savesChanges() {
        boolean saved = false;
        for (Object object : changesList) {
            if (object instanceof ChangeSource) {
                ChangeSource change = (ChangeSource) object;
                String transWithId = change.getTransformation();
                String[] parts = transWithId.split(" id:");
                String transformation = parts[0];

                try {
                    if (transformation.equals("No transformation")) {
                        queryhandler.updateSource(change.getSourceMappingID(), change.getDestinationAttrID(), null,
                                change.getSourceAttrID(), change.getMappingSetID());
                    } else {
                        queryhandler.updateSource(change.getSourceMappingID(), change.getDestinationAttrID(), transformation,
                                change.getSourceAttrID(), change.getMappingSetID());
                    }
                    saved = true;
                } catch (Exception e) {
                    saved = false;
                }
            } else if (object instanceof ChangeDestination) {
                ChangeDestination change = (ChangeDestination) object;
                String transWithdId = change.getTransformation();
                String[] parts = transWithdId.split(" id:");
                String transformation = parts[0];
                try {
                    if (transformation.equals("No transformation")) {
                        queryhandler.updateDestination(change.getSourceMappingID(), change.getDestinationAttrID(), null);
                    } else {
                        queryhandler.updateDestination(change.getSourceMappingID(), change.getDestinationAttrID(), transformation);
                    }
                } catch (Exception e) {
                    saved = false;
                }
            }
        }
        return saved;
    }
}
