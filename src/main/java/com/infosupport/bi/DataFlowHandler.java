/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author MaartenKo
 */
public class DataFlowHandler {

    private DataFlow dataFlow;
    private final MSSQLQuery queryhandler;

    public DataFlowHandler(String dbString, String username, String password) {
        queryhandler = new MSSQLQuery(dbString, username, password);    
    }

    public List<DestinationAttribute> getDataFlow() {

        return dataFlow.getDestinations();
    }

    public ResultSet getSystems() {

        return queryhandler.getSystems();
    }

    public void createMappingList(ResultSet rs) {
         dataFlow = new DataFlow();
                try {
            while (rs.next()) {
                ResultSet result = queryhandler.getMappings(rs.getInt(1));
                dataFlow.createDataflow(result);
            }
        } catch (Exception e) {
            System.out.println("Kan geen mappinglist maken");
        }
        
    }

    public ResultSet getMappingSets(int dataModel) {

        return queryhandler.getMappingSets(dataModel);
    }

}
