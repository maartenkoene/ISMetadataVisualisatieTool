package com.infosupport.bi;

import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class DataFlowModelTest {

    private Modifier modifier;
    private Attribute source;
    private Attribute destination;
    

    @Before
    public void createVariables() {
        source = new Attribute("AutoNaam", "Database2", "Auto");
        destination = new Attribute("VehicleName", "DatabWarehouse", "Vehicle");
        modifier = new Modifier(source, "Addition", destination);

    }  
    
    @Test(expected=IllegalArgumentException.class)
    public void shouldOnlyCreateModifiersWithKnownDestinationAttribute() {
        DataFlow dataFlow = new DataFlow();
        Attribute source = dataFlow.createAttribute("Autonaam", "Database", "Auto");
        Attribute unknownDestination = new Attribute("VehicleName", "DataWarehouse", "Vehicle");
        
        dataFlow.addModifier(source, "multiplication", unknownDestination);
    }
    
    @Test
    public void shouldCreateModifiers() {
        DataFlow dataFlow = new DataFlow();
        Attribute sourceAttribute = dataFlow.createAttribute("carName", "Database1", "Car");
        Attribute destinationAttribute = dataFlow.createAttribute("VehicleName", "DatabWarehouse", "Vehicle");

        Modifier aModifier = dataFlow.addModifier(sourceAttribute, "multiplication", destinationAttribute);

        assertThat(dataFlow.getModifiers(), hasItems(aModifier));
    }
    
    @Test(expected=UnsupportedOperationException.class)
    public void shouldNotBePossibleToChangeTheModifierList(){
        DataFlow dataFlow = new DataFlow();
        
        List<Modifier> modifiers = dataFlow.getModifiers();
        
        modifiers.add(new Modifier(null, "a", null));
    }

}
