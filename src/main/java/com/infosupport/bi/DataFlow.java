package com.infosupport.bi;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataFlow {

    private List<Attribute> attributes = new ArrayList<Attribute>();
    private List<Modifier> modifiers = new ArrayList<Modifier>();
    private MSSQLQuery queryBuilder;

    public DataFlow(String dbString, String username, String password) {
        queryBuilder = new MSSQLQuery(dbString, username, password);
    }

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

    public List<Modifier> createDataflow(int mappingSetId) {

        ResultSet rs = queryBuilder.getMappings(mappingSetId);

        try {
            while (rs.next()) {

                Attribute source = this.createAttribute(rs.getString(3), null, null);
                Attribute destination = this.createAttribute(rs.getString(7), null, null);
                this.addModifier(source, rs.getString(4), destination);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.getModifiers();
    }

}
