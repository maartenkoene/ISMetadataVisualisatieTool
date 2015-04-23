/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.sql.ResultSet;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MaartenKo
 */
public class DataFlowHandlerTest {

    private String connect;
    private String username;
    private String password;
    private int dataModelId;

    @Before
    public void createVariables() {

        connect = "jdbc:sqlserver://127.0.0.1:1433;databaseName=ISMetadata;integratedSecurity=true;";
        username = "Visualisation";
        password = "Info2015";
        dataModelId = 5;

    }

    @Test
    public void createDataFlowFromDB() {
        DataFlowHandler dataflowhandler = new DataFlowHandler(connect, username, password);

        ResultSet tijdelijk = dataflowhandler.getMappingSets(dataModelId);
        dataflowhandler.createMappingList(tijdelijk);

        for (Modifier temp : dataflowhandler.getDataFlow()) {
            //    System.out.println(temp.toString());
        }

        assertNotNull("Dataflow is leeg", dataflowhandler.getDataFlow());
    }
}
