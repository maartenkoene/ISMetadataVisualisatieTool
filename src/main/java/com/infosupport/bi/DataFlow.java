package com.infosupport.bi;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFlow {

    private List<DestinationAttribute> destinations;

    public DataFlow() {
        destinations = new ArrayList<DestinationAttribute>();
    }

    public Attribute createAttribute(String name, String dbName, String tableName) {
        Attribute attribute = new Attribute(name, dbName, tableName);
        return attribute;
    }

    public void createDestination(Attribute source, String transformation, Attribute destination) {

        if (destinations.isEmpty()) {
            DestinationAttribute destinationAttr = new DestinationAttribute(source, transformation, destination);
            destinations.add(destinationAttr);
        } else {
            int destinationNumber = this.loopThroughDestinations(transformation, destination);
            if (destinationNumber == 10000) {
                DestinationAttribute destinationAttr = new DestinationAttribute(source, transformation, destination);
                destinations.add(destinationAttr);
            } else {
                destinations.get(destinationNumber).AddSource(source);
            }
        }
    }

    public int loopThroughDestinations(String transformation, Attribute destination) {
        int result = 10000;
        int i;
        for (i = 0; i < destinations.size(); i++) {
            if (destinations.get(i).compareDestinations(destination, transformation)) {
                result = i;
                break;
            } else {
                result = 10000;
            }

        }
        return result;
    }

    public List<DestinationAttribute> getDestinations() {
        return Collections.unmodifiableList(destinations);
    }

    public void createDataflow(ResultSet rs) {

        try {
            while (rs.next()) {

                Attribute source = this.createAttribute(rs.getString(9), rs.getString(11), rs.getString(10));
                Attribute destination = this.createAttribute(rs.getString(3), rs.getString(5), rs.getString(4));

                this.createDestination(source, rs.getString(6), destination);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
