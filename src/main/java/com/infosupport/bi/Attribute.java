package com.infosupport.bi;

public class Attribute {
    private String name;
    private String dbName;
    private String tableName;
    private String color;
    
    public Attribute(String name, String dbName, String tableName, String color) {
        this.name = name;
        this.dbName = dbName;
        this.tableName = tableName;
        this.color = color;
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

    public String getColor() {
        return color;
    }
    
    
}
