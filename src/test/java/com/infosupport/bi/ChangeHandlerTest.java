/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author MaartenKo
 */
public class ChangeHandlerTest {

    @Test
    public void checkDoubleSourceEntriesShouldBeTrue() {
        ChangeHandler changehandler = new ChangeHandler();
        int sourceMap1 = 20;
        int destID1 = 100;
        String trans1 = "Niks";
        int sourceID1 = 9;
        int mappingSet1 = 7;
        String oldTrans1 = "Nog niks";
        int oldDest1 = 200;

        int sourceMap2 = 40;
        int destID2 = 400;
        String trans2 = "Iets";
        int sourceID2 = 4;
        int mappingSet2 = 7;
        String oldTrans2 = "Verm";
        int oldDest2 = 500;

        ChangeSource firstSourceEntry = new ChangeSource(sourceMap1, destID1, trans1, sourceID1, mappingSet1, oldTrans1, oldDest1);
        changehandler.setChangesList(firstSourceEntry);
        ChangeSource changeBack = new ChangeSource(sourceMap1, oldDest1, oldTrans1, sourceID1, mappingSet1, trans1, destID1);
        changehandler.setChangesList(changeBack);
        ChangeSource realChange = new ChangeSource(sourceMap2, oldDest2, oldTrans2, sourceID2, mappingSet2, trans2, destID2);
        changehandler.setChangesList(realChange);

        changehandler.checkDoubleEntries();

        assertTrue(changehandler.getChangesList().size() == 1);
    }

    @Test
    public void checkDoubleDestinationEntriesShouldBeTrue() {
        ChangeHandler changehandler = new ChangeHandler();
        int sourceMap1 = 20;
        int destID1 = 200;
        String newTrans = "no transformation";
        String oldTrans = "verm";
        int oldDest = 100;

        int sourceMap2 = 40;
        int destID2 = 400;
        String newTrans2 = "no transformation";
        String oldTrans2 = "iets";
        int oldDest2 = 100;

        ChangeDestination firstDestinationEntry = new ChangeDestination(sourceMap1, destID1, newTrans, oldTrans, oldDest);
        changehandler.setChangesList(firstDestinationEntry);
        ChangeDestination changeBack = new ChangeDestination(sourceMap1, oldDest, oldTrans, newTrans, destID1);
        changehandler.setChangesList(changeBack);
        ChangeDestination realChange = new ChangeDestination(sourceMap2, destID2, newTrans2, oldTrans2, oldDest2);
        changehandler.setChangesList(realChange);

        changehandler.checkDoubleEntries();

        assertTrue(changehandler.getChangesList().size() == 1);

    }
}
