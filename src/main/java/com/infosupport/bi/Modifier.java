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
    
    result.append("SourceName: " + this.source.getName() + " ");
    result.append("SourceDB: " + this.source.getDbName()+ " ");
    result.append("Transformation: " + this.transformation+ " ");
    result.append("DestinationName: " + this.destination.getName()+ " ");
    result.append("DestinationDB: "+ this.destination.getDbName()+ " ");
    
    return result.toString();
    }
}
