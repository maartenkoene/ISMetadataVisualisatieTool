package com.infosupport.bi;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFlow {

    private final List<DestinationAttribute> destinations;
    private int foundDestinationNumber;

    public DataFlow() {
        destinations = new ArrayList<DestinationAttribute>();
    }

    public Attribute createAttribute(String name, String dbName, String tableName, int attributeID, int mappingID, int mappingSetID) {
        Attribute attribute = new Attribute(name, dbName, tableName, attributeID, mappingID, mappingSetID);
        return attribute;
    }

    public void createDestination(Attribute source, String transformation, Attribute destination) {

        if (destinations.isEmpty()) {
            DestinationAttribute destinationAttr = new DestinationAttribute(source, transformation, destination);
            destinations.add(destinationAttr);
        } else {
            boolean destinationNumber = this.loopThroughDestinations(transformation, destination);
            if (destinationNumber == true) {
                destinations.get(foundDestinationNumber).AddSource(source);
            } else {
                DestinationAttribute destinationAttr = new DestinationAttribute(source, transformation, destination);
                destinations.add(destinationAttr);
            }
        }
    }

    public boolean loopThroughDestinations(String transformation, Attribute destination) {
        int i;
        boolean found = false;
        for (i = 0; i < destinations.size(); i++) {
            if (destinations.get(i).compareDestinations(destination, transformation)) {
                foundDestinationNumber = i;
                found = true;
                break;
            }
        }
        return found;
    }

    public List<DestinationAttribute> getDestinations() {
        return Collections.unmodifiableList(destinations);
    }

    public void createDataflow(ResultSet rs) {

        try {
            while (rs.next()) {

                Attribute source = this.createAttribute(rs.getString(9), rs.getString(11), rs.getString(10), rs.getInt(8), rs.getInt(7), rs.getInt(12));
                Attribute destination = this.createAttribute(rs.getString(3), rs.getString(5), rs.getString(4), rs.getInt(2),rs.getInt(1),rs.getInt(12));

                this.createDestination(source, rs.getString(6), destination);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
