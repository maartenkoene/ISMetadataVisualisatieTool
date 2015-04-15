/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

    @Before
    public void databaseVariables() {
        connect = "jdbc:sqlserver://127.0.0.1:1433;databaseName=ISMetadata;integratedSecurity=true;";
        username = "Visualisation";
        password = "Info2015";
    }

    @Test
    public void testConnection() {
        Connector conn = new Connector();
        Statement statement;
        Connection connection = conn.dbConnect(connect, username, password);

        try {
            statement = connection.createStatement();
            String queryString = "SELECT [MappingSetID], [DestinationVersionID], [Name], [Description],[MappingVersion] FROM [ISMetadata].[ismd].[MappingSet] WHERE [DestinationVersionID] = 5";
            ResultSet rs = statement.executeQuery(queryString);

            while (rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query faalt");
        }

        try {
            statement = connection.createStatement();
            String queryString = "SELECT [DataModelID]"
                    + "      ,[DataModelTypeID]"
                    + "      ,[Name]"
                    + "      ,[DatabaseName]"
                    + "      ,[Description]"
                    + "  FROM [ISMetadata].[ismd].[DataModel]";
            ResultSet rs = statement.executeQuery(queryString);

            while (rs.next()) {
                System.out.println(rs.getString(1)+ " "+rs.getString(2)+ " "+rs.getString(3)+ " "+rs.getString(4));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query faalt");
        }
    }
}
