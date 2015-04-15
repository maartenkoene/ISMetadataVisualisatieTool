package com.infosupport.bi;

public class Attribute {

    private String name;
    private String dbName;
    private String tableName;

    public Attribute(String name, String dbName, String tableName) {
        this.name = name;
        this.dbName = dbName;
        this.tableName = tableName;
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

}
