package com.infosupport.bi;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class DataFlowModelTest {

    private Modifier modifier;
    private Attribute source;
    private Attribute destination;
    

    @Before
    public void createVariables() {
        source = new Attribute("AutoNaam", "Database2", "Auto", "Pink");
        destination = new Attribute("VehicleName", "DatabWarehouse", "Vehicle", "Green");
        modifier = new Modifier(source, "Addition", destination);

    }

    @Test
    public void createSimpleDataFlow() {
        DataFlow dataFlow = new DataFlow();
        Attribute sourceAttribute = dataFlow.createAttribute("carName", "Database1", "Car", "Blue");
        Attribute destinationAttribute = dataFlow.createAttribute("VehicleName", "DatabWarehouse", "Vehicle", "Green");

        Modifier firstModifier = dataFlow.addModifier(sourceAttribute, "multiplication", destinationAttribute);

        dataFlow.addModifierToDataFlowList(firstModifier);
        
        dataFlow.addModifierToDataFlowList(modifier);
        
                List<Modifier> modifiers;
        modifiers = dataFlow.getModifiers();

        for (Modifier row : modifiers) {
            System.out.println(row.toString());
        }
    }

}
