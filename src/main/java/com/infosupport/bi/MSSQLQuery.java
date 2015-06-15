/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

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

    public Connection getConnection() {
        return connection;
    }

    public ResultSet getSystems() {

        PreparedStatement statement;
        ResultSet rs = null;

        try {
            statement = connection.prepareStatement("SELECT [DataModelID],"
                    + "[DataModelTypeID],"
                    + "[Name],"
                    + "[DatabaseName],"
                    + "[Description] "
                    + "FROM [ISMetadata].[ismd].[DataModel]");

            rs = statement.executeQuery();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println("Query systems faalt");
        }

        return rs;
    }

    public ResultSet getMappingSets(int dataModel) {

        PreparedStatement statement;
        ResultSet rs = null;
        String dataModelID = Integer.toString(dataModel);

        try {
            statement = connection.prepareStatement("SELECT [MappingSetID],"
                    + "[DestinationVersionID],[Name],"
                    + "[Description],[MappingVersion] "
                    + "FROM [ISMetadata].[ismd].[MappingSet]"
                    + "WHERE [DestinationVersionID] =?");
            statement.setString(1, dataModelID);

            rs = statement.executeQuery();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println("Query mappingset faalt");
        }

        return rs;
    }

    public ResultSet getMappings(int MappingSet) {

        PreparedStatement statement;
        ResultSet rs = null;
        String mappingSetId = Integer.toString(MappingSet);

        try {
            statement = connection.prepareStatement("SELECT Dest.MappingID, "
                    + "Dest.DestinationAttributeID, DestAttr.Name, "
                    + "DTabel.Name, DDatabase.Name, Dest.Transformation, "
                    + "Src.MappingID, Src.SourceAttributeID, SrcAttr.Name, "
                    + "STabel.Name,SDatabase.Name, MappingSetID "
                    + "FROM ISMetadata.ismd.MappingMappingSet MMS "
                    + "LEFT JOIN ISMetadata.ismd.Mapping Dest "
                    + "ON MMS.MappingID = Dest.MappingID "
                    + "LEFT JOIN ISMetadata.ismd.MappingRow Src "
                    + "ON Src.MappingID = MMS.MappingID "
                    + "LEFT JOIN ISMetadata.ismd.Attribute DestAttr "
                    + "ON Dest.DestinationAttributeID = DestAttr.AttributeID "
                    + "LEFT JOIN ISMetadata.ismd.Entity DTabel "
                    + "ON DestAttr.EntityID = DTabel.EntityID "
                    + "LEFT JOIN ISMetadata.ismd.DataModel DDatabase "
                    + "ON DTabel.DataModelID = DDatabase.DataModelID "
                    + "LEFT JOIN ISMetadata.ismd.Attribute SrcAttr "
                    + "ON Src.SourceAttributeID = SrcAttr.AttributeID "
                    + "LEFT JOIN ISMetadata.ismd.Entity STabel "
                    + "ON SrcAttr.EntityID = STabel.EntityID "
                    + "LEFT JOIN ISMetadata.ismd.DataModel SDatabase "
                    + "ON STabel.DataModelID = SDatabase.DataModelID "
                    + "WHERE MappingSetID =?");

            statement.setString(1, mappingSetId);

            rs = statement.executeQuery();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println("Query mappings faalt");
        }

        return rs;
    }

    public void updateDestination(int sourceMappingID, int destinationAttrID, String transformationString) throws SQLException {

        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE ISMetadata.ismd.Mapping SET [DestinationAttributeID] = ?, [Transformation] = ? WHERE [MappingID] = ? ");

            statement.setInt(1, destinationAttrID);
            statement.setString(2, transformationString);
            statement.setInt(3, sourceMappingID);

            statement.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            connection.rollback();
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println("Kan geen bestemming updaten");
        } finally {
            if (statement != null) {
                statement.close();
            }
            connection.setAutoCommit(true);
        }

    }

    public boolean updateSource(int sourceMappingID, int destinationAttrID, String transformation) throws SQLException {

        PreparedStatement statement = null;

        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement("UPDATE ISMetadata.ismd.Mapping SET [DestinationAttributeID] = ?, [Transformation] = ? WHERE [MappingID] = ? ");

            statement.setInt(1, destinationAttrID);
            statement.setString(2, transformation);
            statement.setInt(3, sourceMappingID);

            statement.executeUpdate();
            connection.commit();
            return true;

        } catch (Exception e) {
            connection.rollback();
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println("Kan geen bestemming updaten");
            return false;
        } finally {
            if (statement != null) {
                statement.close();
            }
            connection.setAutoCommit(true);
        }

    }

}
