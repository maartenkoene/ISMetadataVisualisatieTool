package com.infosupport.bi;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DataFlowModelTest {

//    @Test(expected = IllegalArgumentException.class)
//    public void shouldOnlyCreateModifiersWithKnownDestinationAttribute() {
//        DataFlow dataFlow = new DataFlow();
//        Attribute source = dataFlow.createAttribute("Autonaam", "Database", "Auto");
//        Attribute unknownDestination = new Attribute("VehicleName", "DataWarehouse", "Vehicle");
//
//        dataFlow.addModifier(source, "multiplication", unknownDestination);
//    }

//    @Test(expected = UnsupportedOperationException.class)
//    public void shouldNotBePossibleToChangeTheModifierList() {
//        DataFlow dataFlow = new DataFlow();
//
//        List<DestinationAttribute> modifiers = dataFlow.getDestinations();
//
//        modifiers.add(new DestinationAttribute(null, "a", null));
//    }

    @Test
    public void compareShouldBeTrue() {
        DataFlow dataFlow = new DataFlow();
        Attribute sourceAttribute = dataFlow.createAttribute("carName", "Database1", "Car", 1, 1);
        Attribute destinationAttribute = dataFlow.createAttribute("VehicleName", "DatabWarehouse", "Vehicle",2,2);
        DestinationAttribute destination = new DestinationAttribute(sourceAttribute, "something",destinationAttribute);
        
        
        assertTrue(destination.compareDestinations(destinationAttribute, "something"));
        
    }
    
       @Test
    public void destinationWithMultipleSources(){
        DataFlow dataFlow = new DataFlow();
        Attribute source1 = dataFlow.createAttribute("Autonaam", "Database", "Auto",1,2);
        Attribute source2 = dataFlow.createAttribute("carName", "Database1", "Car",3,4);
        Attribute source3 = dataFlow.createAttribute("fiets", "Datab", "fietsen",5,6);
        
        Attribute destination = dataFlow.createAttribute("Autonaam", "Database", "Auto",7,8);
        Attribute destination2 = dataFlow.createAttribute("FietsID", "DataW", "Fiets",9,10);

        dataFlow.createDestination(source1, "add", destination);
        dataFlow.createDestination(source3, null, destination2);
        dataFlow.createDestination(source2, "add", destination);
    
                for (DestinationAttribute temp : dataFlow.getDestinations()) {
                System.out.println(temp.toString());
        }
    }
}
