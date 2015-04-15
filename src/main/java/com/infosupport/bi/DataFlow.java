package com.infosupport.bi;

import java.util.ArrayList;
import java.util.List;

public class DataFlow {

    List<Attribute> attributes = new ArrayList<Attribute>();
    List<Modifier> modifiers = new ArrayList<Modifier>();

    public Attribute createAttribute(String name, String dbName, String tableName, String color) {

        Attribute attribute = new Attribute(name, dbName, tableName, color);

        return attribute;
    }

    public Modifier addModifier(Attribute source, String transformation, Attribute destination) {

        Modifier modifier = new Modifier(source, transformation, destination);

        return modifier;
    }

    public void addModifierToDataFlowList(Modifier modifier) {

        modifiers.add(modifier);
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }
    
    
}
