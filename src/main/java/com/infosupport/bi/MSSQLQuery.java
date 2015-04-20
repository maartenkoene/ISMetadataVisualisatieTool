/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author MaartenKo
 */
public class MSSQLQuery {

    private final Connector connect;
    private final Connection connection;

    public MSSQLQuery(String dbString, String username, String password) {
        connect = new Connector(dbString, username, password);
        connection = connect.dbConnect();
    }

    public ResultSet getSystems() {

        Statement statement;
        ResultSet rs = null;

        try {
            statement = connection.createStatement();

            String query = "SELECT [DataModelID],"
                    + "[DataModelTypeID],"
                    + "[Name],"
                    + "[DatabaseName],"
                    + "[Description] "
                    + "FROM [ISMetadata].[ismd].[DataModel]";

            rs = statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query systems faalt");
        }

        return rs;
    }

    public ResultSet getMappingSets(int dataModel) {

        Statement statement;
        ResultSet rs = null;
        String dataModelID = Integer.toString(dataModel);

        try {
            statement = connection.createStatement();

            String query = "SELECT [MappingSetID],"
                    + "[DestinationVersionID],[Name],"
                    + "[Description],[MappingVersion] "
                    + "FROM [ISMetadata].[ismd].[MappingSet]"
                    + "WHERE [DestinationVersionID] =" + dataModelID;

            rs = statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query mappingset faalt");
        }

        return rs;
    }
    
    
    public ResultSet getMappings(int MappingSet){
    
        Statement statement;
        ResultSet rs = null;
        String mappingSetId= Integer.toString(MappingSet);
        
                try {
            statement = connection.createStatement();

            String query = "SELECT Dest.MappingID, "
                    + "Dest.DestinationAttributeID, "
                    + "DestAttr.Name, Dest.Transformation, "
                    + "Src.MappingID, Src.SourceAttributeID, "
                    + "SrcAttr.Name "
                    + "FROM ISMetadata.ismd.MappingMappingSet MMS "
                    + "LEFT JOIN ISMetadata.ismd.Mapping Dest "
                    + "ON MMS.MappingID = Dest.MappingID "
                    + "LEFT JOIN ISMetadata.ismd.MappingRow Src "
                    + "ON Src.MappingID = MMS.MappingID "
                    + "LEFT JOIN ISMetadata.ismd.Attribute DestAttr "
                    + "ON Dest.DestinationAttributeID = DestAttr.AttributeID "
                    + "LEFT JOIN ISMetadata.ismd.Attribute SrcAttr "
                    + "ON Src.SourceAttributeID = SrcAttr.AttributeID "
                    + "WHERE MappingSetID =" + mappingSetId;

            rs = statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Query mappings faalt");
        }
        
        
        return rs;
    }

}
