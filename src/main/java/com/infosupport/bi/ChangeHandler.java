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

    private MSSQLQuery queryhandler;
    private List<Change> changesMade = new ArrayList<Change>();

    public ChangeHandler() {
    }

    public ChangeHandler(String dbString, String username, String password) {
        queryhandler = new MSSQLQuery(dbString, username, password);
    }

    public void removeInverses() {

        List<Change> inverses = new ArrayList<Change>();

        for (Change change : changesMade) {
            for (Change candidate : changesMade) {
                if (change != candidate && change.inverse(candidate)) {
                    if (!inverses.contains(change)) {
                        inverses.add(change);
                    }
                }
            }
        }
        changesMade.removeAll(inverses);
    }

    public void addChange(Change c) {
        this.changesMade.add(c);
    }

    public List<Change> getChangesMade() {
        return changesMade;
    }

    public SaveState savesChanges() {
        SaveState saved = SaveState.NO_CHANGE;
        boolean sourceChangeFound = false;
        boolean destinationChangeFound = false;

        for (Change change : changesMade) {
            if (change instanceof ChangeSource) {
                sourceChangeFound = true;
            } else if (change instanceof ChangeDestination) {
                destinationChangeFound = true;
            }
        }

        if (sourceChangeFound == true && destinationChangeFound == true) {
            return SaveState.SYNTAX;
        } else if (changesMade.isEmpty()) {
            return SaveState.NO_CHANGE;
        } else {

            for (Change change : changesMade) {
                if (change instanceof ChangeSource) {
                    ChangeSource changeSource = (ChangeSource) change;
                    String transWithId = changeSource.getTransformation();
                    String[] parts = transWithId.split(" id:");
                    String transformation = parts[0];

                    try {
                        if (transformation.equals("No transformation")) {
                            queryhandler.updateSource(changeSource.getSourceMappingID(), changeSource.getDestinationAttrID(), null,
                                    changeSource.getSourceAttrID(), changeSource.getMappingSetID());
                        } else {
                            queryhandler.updateSource(changeSource.getSourceMappingID(), changeSource.getDestinationAttrID(), transformation,
                                    changeSource.getSourceAttrID(), changeSource.getMappingSetID());
                        }
                        saved = SaveState.SUCCESSFULL;
                    } catch (Exception e) {
                        saved = SaveState.SYNTAX;
                    }
                } else if (change instanceof ChangeDestination) {
                    ChangeDestination changeDestination = (ChangeDestination) change;
                    String transWithdId = changeDestination.getTransformation();
                    String[] parts = transWithdId.split(" id:");
                    String transformation = parts[0];
                    try {
                        if (transformation.equals("No transformation")) {
                            queryhandler.updateDestination(changeDestination.getSourceMappingID(), changeDestination.getDestinationAttrID(), null);
                            saved = SaveState.SUCCESSFULL;
                        } else {
                            queryhandler.updateDestination(changeDestination.getSourceMappingID(), changeDestination.getDestinationAttrID(), transformation);
                            saved = SaveState.SUCCESSFULL;
                        }
                    } catch (Exception e) {
                        saved = SaveState.SYNTAX;
                    }
                }
            }
            return saved;
        }
    }
}
