/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author MaartenKo
 */
public class Connector {

    private String dbConnect;
    private String dbUsername;
    private String dbPassword;
    private Connection conn;

    /**
     *
     * @param connect
     * @param username Visualisation
     * @param password Info2015
     */
    public Connector(String connect, String username, String password) {
        this.dbConnect = connect;
        this.dbUsername = username;
        this.dbPassword = password;
    }

    public Connection dbConnect() {

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbConnect, dbUsername, dbPassword);
            System.out.println("Connected");

            return conn;
        } catch (Exception e) {
            System.out.println("Niet Connected");
            e.printStackTrace();
            return null;
        }
    }

    public Connection getConn() {
        return conn;
    }

}
