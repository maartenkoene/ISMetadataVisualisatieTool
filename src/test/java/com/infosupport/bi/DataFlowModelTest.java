package com.infosupport.bi;

import org.junit.Test;

public class DataFlowModelTest {
    @Test
    public void createSimpleModifier() {
        Attribute source = new Attribute();
        Attribute destination = new Attribute();

        Modifier modifier = new Modifier(source, "multiplication", destination);
    }

    @Test
    public void createSimpleDataFlow() {
        DataFlow dataFlow = new DataFlow();
        Attribute source = dataFlow.createAttribute();
        Attribute destination = dataFlow.createAttribute();

        dataFlow.addModifier(source, "multiplication", destination);
    }
}
