/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author MaartenKo
 */
public class TransformationWidget extends LabelWidget {

    private final Widget destination;

    public TransformationWidget(Scene scene, Widget destination) {
        super(scene);
        this.destination = destination;
    }

    public String getDestinationText() {
        LabelWidget labelText = (LabelWidget) destination;
        return labelText.getLabel();
    }

}
