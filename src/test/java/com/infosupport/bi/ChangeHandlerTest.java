/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MaartenKo
 */
public class ChangeHandlerTest {

    int sourceMap2;
    int destID2;
    String trans2;
    int sourceID2;
    int mappingSet2;
    String oldTrans2;
    int oldDest2;

    int sourceMap3;
    int destID3;
    String trans3;
    int sourceID3;
    int mappingSet3;
    String oldTrans3;
    int oldDest3;

    int sourceMapDes2;
    int destIDDes2;
    String newTransDes2;
    String oldTransDes2;
    int oldDestDes2;

    int sourceMapDes3;
    int destIDDes3;
    String newTransDes3;
    String oldTransDes3;
    int oldDestDes3;

    ChangeSource realChangeSource;
    ChangeSource anotherRealChangeSource;
    ChangeHandler changehandler;
    ChangeDestination realChangeDestination;
    ChangeDestination anotherRealChangeDestination;

    @Before
    public void createVariables() {
        changehandler = new ChangeHandler();
        sourceMap2 = 40;
        destID2 = 400;
        trans2 = "Iets";
        sourceID2 = 4;
        mappingSet2 = 7;
        oldTrans2 = "Verm";
        oldDest2 = 500;
        realChangeSource = new ChangeSource(sourceMap2, oldDest2, oldTrans2, sourceID2, mappingSet2, trans2, destID2);

        sourceMap3 = 50;
        destID3 = 500;
        trans3 = "Add";
        sourceID3 = 5;
        mappingSet3 = 8;
        oldTrans3 = "verm";
        oldDest3 = 800;
        anotherRealChangeSource = new ChangeSource(sourceMap3, oldDest3, oldTrans3, sourceID3, mappingSet3, trans3, destID3);

        sourceMapDes2 = 40;
        destIDDes2 = 400;
        newTransDes2 = "no transformation";
        oldTransDes2 = "iets";
        oldDestDes2 = 100;
        realChangeDestination = new ChangeDestination(sourceMapDes2, destIDDes2, newTransDes2, oldTransDes2, oldDestDes2);

        sourceMapDes3 = 50;
        destIDDes3 = 5000;
        newTransDes3 = "Distinct";
        oldTransDes3 = "no transformation";
        oldDestDes3 = 200;
        anotherRealChangeDestination = new ChangeDestination(sourceMapDes3, destIDDes3, newTransDes3, oldTransDes3, oldDestDes3);
    }

    @Test
    public void checkDoubleSourceEntriesShouldBeTrue() {

        int sourceMap1 = 20;
        int destID1 = 100;
        String trans1 = "Niks";
        int sourceID1 = 9;
        int mappingSet1 = 7;
        String oldTrans1 = "Nog niks";
        int oldDest1 = 200;

        ChangeSource firstSourceEntry = new ChangeSource(sourceMap1, destID1, trans1, sourceID1, mappingSet1, oldTrans1, oldDest1);
        changehandler.addChange(firstSourceEntry);
        ChangeSource changeBack = new ChangeSource(sourceMap1, oldDest1, oldTrans1, sourceID1, mappingSet1, trans1, destID1);
        changehandler.addChange(changeBack);

        changehandler.addChange(realChangeSource);

        changehandler.removeInverses();

        assertTrue(changehandler.getChangesMade().size() == 1);
    }

    @Test
    public void checkDoubleDestinationEntriesShouldBeTrue() {

        int sourceMap1 = 20;
        int destID1 = 200;
        String newTrans = "no transformation";
        String oldTrans = "verm";
        int oldDest = 100;

        ChangeDestination firstDestinationEntry = new ChangeDestination(sourceMap1, destID1, newTrans, oldTrans, oldDest);
        changehandler.addChange(firstDestinationEntry);
        ChangeDestination changeBack = new ChangeDestination(sourceMap1, oldDest, oldTrans, newTrans, destID1);
        changehandler.addChange(changeBack);
        changehandler.addChange(realChangeDestination);

        changehandler.removeInverses();

        assertTrue(changehandler.getChangesMade().size() == 1);

    }

    @Test
    public void checkDoubleSourceEntriesShouldBeFalse() {
        changehandler.addChange(realChangeSource);
        changehandler.addChange(anotherRealChangeSource);

        changehandler.removeInverses();
        assertFalse(changehandler.getChangesMade().size() == 1);

    }

    @Test
    public void checkDoubleDestinationEntriesShouldBeFalse() {
        changehandler.addChange(realChangeDestination);
        changehandler.addChange(anotherRealChangeDestination);

        changehandler.removeInverses();
        assertFalse(changehandler.getChangesMade().size() == 1);

    }

    @Test
    public void savesChangesShouldBeTrue() {

        assertTrue(changehandler.savesChanges() == SaveState.NO_CHANGE);
    }

    @Test
    public void changesCantBeSaved() {

        changehandler.addChange(realChangeDestination);
        changehandler.addChange(realChangeSource);
        changehandler.savesChanges();
        assertTrue(changehandler.savesChanges() == SaveState.SYNTAX);
    }

    @Test
    public void addChanges() {
        changehandler.addChange(realChangeSource);
        changehandler.addChange(realChangeDestination);

        assertTrue(changehandler.getChangesMade().size() == 2);

    }
}
