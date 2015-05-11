package com.infosupport.bi;

public class Attribute {

    private final String name;
    private final String dbName;
    private final String tableName;
    private final int attributeID;
    private final int mappingID;
    private final int mappingSetID;

    public Attribute(String name, String dbName, String tableName, int attributeID, int mappingID, int mappingSetID) {
        this.name = name;
        this.dbName = dbName;
        this.tableName = tableName;
        this.attributeID = attributeID;
        this.mappingID = mappingID;
        this.mappingSetID = mappingSetID;
    }

    public String getName() {
        return name;
    }

    public String getDbName() {
        return dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public int getAttributeID() {
        return attributeID;
    }

    public int getMappingID() {
        return mappingID;
    }

    public int getMappingSetID() {
        return mappingSetID;
    }
    
    public boolean compareAttributes(Attribute aNewAttribute) {
        return this.name.equals(aNewAttribute.name) && this.dbName.equals(aNewAttribute.dbName) && this.tableName.equals(aNewAttribute.tableName);
    }

}
