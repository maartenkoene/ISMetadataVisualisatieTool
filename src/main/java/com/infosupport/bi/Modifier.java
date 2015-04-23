package com.infosupport.bi;

public class Modifier {
    private final Attribute source;
    private final String transformation;
    private final Attribute destination;

    public Modifier(Attribute source, String transformation, Attribute destination) {
        this.source = source;
        this.transformation = transformation;
        this.destination = destination;
    }
    
    @Override
    public String toString(){
    StringBuilder result = new StringBuilder();
    
    result.append("SourceName: ").append(this.source.getName()).append(" ");
    result.append("SourceDB: " ).append(this.source.getDbName()).append(" ");
    result.append("Transformation: ").append(this.transformation).append(" ");
    result.append("DestinationName: ").append(this.destination.getName()).append(" ");
    result.append("DestinationDB: ").append(this.destination.getDbName()).append(" ");
    
    return result.toString();
    }
}
