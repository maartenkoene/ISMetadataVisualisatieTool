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
}
