package com.infosupport.bi;

import java.sql.ResultSet;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class DataFlowModelTest {

    private Attribute source;
    private Attribute destination;
    private String connect;
    private String username;
    private String password;
    private int mappingSetId;
    private int dataModelId;

    @Before
    public void createVariables() {
        source = new Attribute("AutoNaam", "Database2", "Auto");
        destination = new Attribute("VehicleName", "DatabWarehouse", "Vehicle");
        connect = "jdbc:sqlserver://127.0.0.1:1433;databaseName=ISMetadata;integratedSecurity=true;";
        username = "Visualisation";
        password = "Info2015";
        mappingSetId = 1;
        dataModelId = 5;

    }

    @Test(expected = IllegalArgumentException.class)
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

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotBePossibleToChangeTheModifierList() {
        DataFlow dataFlow = new DataFlow();

        List<Modifier> modifiers = dataFlow.getModifiers();

        modifiers.add(new Modifier(null, "a", null));
    }

    @Test
    public void createDataFlowFromDB() {
        DataFlowHandler dataflowhandler = new DataFlowHandler(connect, username, password);

        ResultSet tijdelijk = dataflowhandler.getMappingSets(dataModelId);
        dataflowhandler.createMappingList(tijdelijk);

        dataflowhandler.getDataFlow();
        
        for(Modifier temp : dataflowhandler.getDataFlow()){
        //    System.out.println(temp.toString());
        }

        assertNotNull("Dataflow is leeg",dataflowhandler.getDataFlow());
    }
}
