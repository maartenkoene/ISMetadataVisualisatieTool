package com.infosupport.bi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFlow {

    List<Attribute> attributes = new ArrayList<Attribute>();
    List<Modifier> modifiers = new ArrayList<Modifier>();

    public Attribute createAttribute(String name, String dbName, String tableName) {

        Attribute attribute = new Attribute(name, dbName, tableName);
        attributes.add(attribute);
        return attribute;
    }

    public Modifier addModifier(Attribute source, String transformation, Attribute destination) {
        if (!attributes.contains(destination)||!attributes.contains(source)) {
            throw new IllegalArgumentException("This is not a known attribute"); 
        }
        Modifier modifier = new Modifier(source, transformation, destination);
        modifiers.add(modifier);
        return modifier;
    }

    public List<Modifier> getModifiers() {
        return Collections.unmodifiableList(modifiers);
    }
    
    
}
