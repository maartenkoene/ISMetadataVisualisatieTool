package com.infosupport.bi;

import java.util.ArrayList;
import java.util.List;

public class DataFlow {
    List<Attribute> attributes = new ArrayList<Attribute>();
    public Attribute createAttribute() {

        return new Attribute();
    }

    public void addModifier(Attribute source, String multiplication, Attribute destination) {
    }
}
