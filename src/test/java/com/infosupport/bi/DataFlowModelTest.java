package com.infosupport.bi;

import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class DataFlowModelTest {

    @Test
    public void compareShouldBeTrue() {
        DataFlow dataFlow = new DataFlow();
        Attribute sourceAttribute = dataFlow.createAttribute("carName", "Database1", "Car", 1, 1, 1);
        Attribute destinationAttribute = dataFlow.createAttribute("VehicleName", "DatabWarehouse", "Vehicle", 2, 2, 1);
        DestinationAttribute destination = new DestinationAttribute(sourceAttribute, "something", destinationAttribute);

        assertTrue(destination.compareDestinations(destinationAttribute, "something"));

    }

    @Test
    public void compareTransformationShouldBeFalse() {
        DataFlow dataFlow = new DataFlow();
        Attribute source1 = dataFlow.createAttribute("Autonaam", "Database", "Auto", 1, 2, 1);
        Attribute destinationAttribute = dataFlow.createAttribute("VehicleName", "DatabWarehouse", "Vehicle", 2, 2, 1);

        DestinationAttribute destination = new DestinationAttribute(source1, null, destinationAttribute);
        assertFalse(destination.compareDestinations(destinationAttribute, "Filter"));

    }

    @Test
    public void compareDestinationsShouldBeFalse() {
        DataFlow dataFlow = new DataFlow();
        Attribute source1 = dataFlow.createAttribute("Autonaam", "Database", "Auto", 1, 2, 1);
        Attribute destinationAttr1 = dataFlow.createAttribute("VehicleName", "DataWarehouse", "Vehicle", 2, 2, 1);
        Attribute destinationAttr2 = dataFlow.createAttribute("FietsNaam", "DataWarehouse", "Voertuig", 2, 2, 1);

        DestinationAttribute destination = new DestinationAttribute(source1, null, destinationAttr1);
        assertFalse(destination.compareDestinations(destinationAttr2, null));

    }

    @Test
    public void destinationWithMultipleSources() {
        DataFlow dataFlow = new DataFlow();
        Attribute source1 = dataFlow.createAttribute("Autonaam", "Database", "Auto", 1, 2, 1);
        Attribute source2 = dataFlow.createAttribute("carName", "Database1", "Car", 3, 4, 1);
        Attribute source3 = dataFlow.createAttribute("fiets", "Datab", "fietsen", 5, 6, 1);

        Attribute destination = dataFlow.createAttribute("Autonaam", "Database", "Auto", 7, 8, 1);
        Attribute destination2 = dataFlow.createAttribute("FietsID", "DataW", "Fiets", 9, 10, 1);

        dataFlow.createDestination(source1, "add", destination);
        dataFlow.createDestination(source3, null, destination2);
        dataFlow.createDestination(source2, "add", destination);

        for (DestinationAttribute temp : dataFlow.getDestinations()) {
            //System.out.println(temp.toString());

        }
        List<DestinationAttribute> tryThis = dataFlow.getDestinations();
        assertTrue(tryThis.get(0).getSourceAttributes().size() == 2);
    }

    @Test
    public void destinationsWithNoMultipleSources() {
        DataFlow dataFlow = new DataFlow();
        Attribute source1 = dataFlow.createAttribute("Autonaam", "Database", "Auto", 1, 2, 1);
        Attribute source2 = dataFlow.createAttribute("carName", "Database1", "Car", 3, 4, 1);
        Attribute source3 = dataFlow.createAttribute("fiets", "Datab", "fietsen", 5, 6, 1);

        Attribute destination = dataFlow.createAttribute("Autonaam", "Database", "Auto", 7, 8, 1);
        Attribute destination2 = dataFlow.createAttribute("FietsID", "DataW", "Fiets", 9, 10, 1);

        dataFlow.createDestination(source1, "add", destination);
        dataFlow.createDestination(source3, null, destination2);
        dataFlow.createDestination(source2, null, destination);

        assertTrue(dataFlow.getDestinations().size() == 3);

    }
}
