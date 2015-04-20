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
    private MSSQLQuery queryhandler;

    public DataFlowHandler(String dbString, String username, String password) {
        queryhandler = new MSSQLQuery(dbString, username, password);
        dataFlow = new DataFlow();
    }

    public void createMappingList(int mappingSetId) {
        ResultSet rs = queryhandler.getMappings(mappingSetId);

        dataFlow.createDataflow(rs);
    }

    public List<Modifier> getDataFlow() {
        return dataFlow.getModifiers();
    }

}
