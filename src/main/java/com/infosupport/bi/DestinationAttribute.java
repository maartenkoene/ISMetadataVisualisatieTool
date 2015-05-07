package com.infosupport.bi;

import java.util.ArrayList;
import java.util.List;

public class DestinationAttribute {

    private final List<Attribute> sourceAttributes = new ArrayList<Attribute>();
    private final String transformation;
    private final Attribute destination;

    public DestinationAttribute(Attribute source, String transformation, Attribute destination) {
        this.sourceAttributes.add(source);
        this.transformation = transformation;
        this.destination = destination;
    }

    public void AddSource(Attribute source) {
        this.sourceAttributes.add(source);
    }

    public String getTransformation() {
        return transformation;
    }

    public Attribute getDestination() {
        return destination;
    }

    public List<Attribute> getSourceAttributes() {
        return sourceAttributes;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        int i = 0;
        while (i < sourceAttributes.size()) {
            result.append("SourceName").append(i).append(": ").append(sourceAttributes.get(i).getName()).append(" ");
            result.append("SourceDB").append(i).append(": ").append(sourceAttributes.get(i).getDbName()).append(" ");
            result.append("AttribuutID").append(i).append(": ").append(sourceAttributes.get(i).getAttributeID()).append(" ");
            result.append("MappingID").append(i).append(": ").append(sourceAttributes.get(i).getMappingID()).append(" ");
            i++;
        }
        result.append("Transformation: ").append(this.transformation).append(" ");
        result.append("DestinationName: ").append(this.destination.getName()).append(" ");
        result.append("DestinationDB: ").append(this.destination.getDbName()).append(" ");
        result.append("AttribuutID").append(i).append(": ").append(this.destination.getAttributeID()).append(" ");
        result.append("MappingID").append(i).append(": ").append(this.destination.getMappingID()).append(" ");
        return result.toString();
    }

    public boolean compareDestinations(Attribute destination, String transformation) {

        if (transformation == null || this.transformation == null) {
            return false;
        } else {
            return this.transformation.equals(transformation) && this.destination.compareAttributes(destination);
        }

    }
}
