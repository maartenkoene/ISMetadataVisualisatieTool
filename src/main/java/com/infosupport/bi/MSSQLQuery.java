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
import java.sql.Statement;
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

    public void updateDestination(int sourceMappingID, int destinationAttrID, String transformationString) {

        PreparedStatement statement;

        try {
            statement = connection.prepareStatement("UPDATE ISMetadata.ismd.Mapping SET [DestinationAttributeID] = ?, [Transformation] = ? WHERE [MappingID] = ? ");

            statement.setInt(1, destinationAttrID);
            statement.setString(2, transformationString);
            statement.setInt(3, sourceMappingID);

            statement.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString());
            System.out.println("Kan geen bestemming updaten");
        }

    }

    public boolean updateSource(int sourceMappingID, int destinationAttrID, String transformation, int sourceAttrID, int mappingSetID) throws SQLException {

        String deleteMappingRowStatement = "DELETE FROM ISMetadata.ismd.MappingRow WHERE MappingID = ?";

        String deleteMappingSetRow = "DELETE FROM ISMetadata.ismd.MappingMappingSet WHERE MappingID = ? AND MappingSetID = ?";

        String deleteMappingStatement = "DELETE FROM ISMetadata.ismd.Mapping WHERE MappingID = ?";

        String insertDestinationStatement = "INSERT INTO ISMetadata.ismd.Mapping ([DestinationAttributeID],"
                + "[Transformation],[Name]) VALUES (?,?, null)";

        String insertSourceStatement = "INSERT INTO ISMetadata.ismd.MappingRow "
                + "([MappingID],[SourceAttributeID],[ParameterNumber]) "
                + "VALUES (?,?, '1')";

        String insertMappingSet = "INSERT INTO ISMetadata.ismd.MappingMappingSet "
                + "([MappingID], [MappingSetID]) "
                + "VALUES (?, ?)";

        PreparedStatement preparedMappingRowDelete = null;
        PreparedStatement preparedMappingSetDelete = null;
        PreparedStatement preparedMappingDelete = null;
        PreparedStatement preparedInsertDestination = null;
        PreparedStatement preparedInsertSource = null;
        PreparedStatement preparedInsertMappingSet = null;

        try {
            connection.setAutoCommit(false);
            //Oude mappings verwijderen
            preparedMappingRowDelete = connection.prepareStatement(deleteMappingRowStatement);
            preparedMappingRowDelete.setInt(1, sourceMappingID);
            preparedMappingRowDelete.executeUpdate();

            preparedMappingSetDelete = connection.prepareStatement(deleteMappingSetRow);
            preparedMappingSetDelete.setInt(1, sourceMappingID);
            preparedMappingSetDelete.setInt(2, mappingSetID);
            preparedMappingSetDelete.executeUpdate();

            preparedMappingDelete = connection.prepareStatement(deleteMappingStatement);
            preparedMappingDelete.setInt(1, sourceMappingID);
            preparedMappingDelete.executeUpdate();

            //van deze de key terugkrijgen
            preparedInsertDestination = connection.prepareStatement(insertDestinationStatement, Statement.RETURN_GENERATED_KEYS);
            preparedInsertDestination.setInt(1, destinationAttrID);
            preparedInsertDestination.setString(2, transformation);
            preparedInsertDestination.executeUpdate();
            ResultSet keys = preparedInsertDestination.getGeneratedKeys();

            int mappingID = -1;
            while (keys.next()) {
                mappingID = keys.getInt(1);
            }
            if (mappingID != -1) {
                preparedInsertSource = connection.prepareStatement(insertSourceStatement);
                preparedInsertSource.setInt(1, mappingID);
                preparedInsertSource.setInt(2, sourceAttrID);
                preparedInsertSource.executeUpdate();

                preparedInsertMappingSet = connection.prepareStatement(insertMappingSet);
                preparedInsertMappingSet.setInt(1, mappingID);
                preparedInsertMappingSet.setInt(2, mappingSetID);
                preparedInsertMappingSet.executeUpdate();
            }
            connection.commit();
            return true;

        } catch (SQLException e) {
            connection.rollback();
            return false;
        } finally {
            if (preparedMappingRowDelete != null) {
                preparedMappingRowDelete.close();
            }
            if (preparedMappingSetDelete != null) {
                preparedMappingSetDelete.close();
            }
            if (preparedMappingDelete != null) {
                preparedMappingDelete.close();
            }
            if (preparedInsertDestination != null) {
                preparedInsertDestination.close();
            }
            if (preparedInsertSource != null) {
                preparedInsertSource.close();
            }
            if (preparedInsertMappingSet != null) {
                preparedInsertMappingSet.close();
            }
            connection.setAutoCommit(true);
            
        }

    }

}
