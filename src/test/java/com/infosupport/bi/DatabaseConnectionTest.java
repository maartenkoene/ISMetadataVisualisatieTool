/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.sql.Connection;
import java.sql.ResultSet;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author MaartenKo
 */
public class DatabaseConnectionTest {

    private String connect;
    private String username;
    private String password;
    private int dataModelId;
    private int mappingSetId;

    @Before
    public void databaseVariables() {
        connect = "jdbc:sqlserver://127.0.0.1:1433;databaseName=ISMetadata;integratedSecurity=true;";
        username = "Visualisation";
        password = "Info2015";
        dataModelId = 5;
        mappingSetId = 1;
    }

    @Test
    public void testConnection() {
        Connector conn = new Connector(connect, username, password);
        Connection connection = conn.dbConnect();

        assertNotNull("Connection faalt", connection);
    }

    @Test
    public void testGetSystems() {
        MSSQLQuery mssqlQuery = new MSSQLQuery(connect, username, password);

        ResultSet rs = mssqlQuery.getSystems();

        try {
            while (rs.next()) {
                //       System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query faalt");
        }

        assertNotNull("Geen systemen op gehaald", rs);

    }

    @Test
    public void testGetMappingSets() {
        MSSQLQuery mssqlQuery = new MSSQLQuery(connect, username, password);

        ResultSet rs = mssqlQuery.getMappingSets(dataModelId);

        try {
            while (rs.next()) {
                //     System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query faalt");
        }

        assertNotNull("Geen mappings opgehaald", rs);

    }

    @Test
    public void testGetMappings() {
        MSSQLQuery mssqlQuery = new MSSQLQuery(connect, username, password);

        ResultSet rs = mssqlQuery.getMappings(mappingSetId);

        try {
            while (rs.next()) {
                //   System.out.println(rs.getString(9) + " " + rs.getString(6) + " " + rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query faalt");
        }
        
        assertNotNull("Geen mappings opgehaald", rs);

    }
}
