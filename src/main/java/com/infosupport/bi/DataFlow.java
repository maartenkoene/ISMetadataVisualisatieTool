package com.infosupport.bi;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFlow {

    private List<Attribute> attributes = new ArrayList<Attribute>();
    private List<Modifier> modifiers = new ArrayList<Modifier>();
    
    private MSSQLQuery queryBuilder;
    public DataFlow() {
    }

    public Attribute createAttribute(String name, String dbName, String tableName) {

        Attribute attribute = new Attribute(name, dbName, tableName);
        attributes.add(attribute);
        return attribute;
    }

    public Modifier addModifier(Attribute source, String transformation, Attribute destination) {
        if (!attributes.contains(destination) || !attributes.contains(source)) {
            throw new IllegalArgumentException("This is not a known attribute");
        }
        Modifier modifier = new Modifier(source, transformation, destination);
        modifiers.add(modifier);
        return modifier;
    }

    public List<Modifier> getModifiers() {
        return Collections.unmodifiableList(modifiers);
    }

    public void createDataflow(ResultSet rs) {

        try {
            while (rs.next()) {

                Attribute source = this.createAttribute(rs.getString(9),rs.getString(11) , rs.getString(10));
                Attribute destination = this.createAttribute(rs.getString(3), rs.getString(5), rs.getString(4));
                this.addModifier(source, rs.getString(6), destination);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
