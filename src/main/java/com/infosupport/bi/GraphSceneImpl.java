/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.awt.Point;
import java.util.List;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author MaartenKo
 */
public class GraphSceneImpl extends GraphScene<String, String> {

    private final LayerWidget mainLayer;
    private final LayerWidget connectionLayer;
    private final LayerWidget interactionLayer;

    public GraphSceneImpl(List<DestinationAttribute> dataflow) {
        mainLayer = new LayerWidget(this);
        connectionLayer = new LayerWidget(this);
        interactionLayer = new LayerWidget(this);

        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interactionLayer);

        //Dit moet nog worden gescheiden in functionaliteit zodat het OO is
        int i = 0;
        int xtransformation = 460;
        int y = 10;
        int xdestination = 850;
        int xsource = 10;
        while (i < dataflow.size()) {
            String transformationString = dataflow.get(i).getTransformation();
            if (transformationString == null) {
                transformationString = "No transformation";
            }
            Widget transformation = addNode(transformationString);
            transformation.setPreferredLocation(new Point(xtransformation, y));

            String destinationAttrName = "Name: " + dataflow.get(i).getDestination().getName() + " Table: "
                    + dataflow.get(i).getDestination().getTableName() + " DB: "
                    + dataflow.get(i).getDestination().getDbName();

            Widget destination = addNode(destinationAttrName);
            destination.setPreferredLocation(new Point(xdestination, y));

            ConnectionWidget transDestConn = this.createConnection(transformation, destination);
            connectionLayer.addChild(transDestConn);
            int ySource = y;
            for (Attribute sourceAttr : dataflow.get(i).getSourceAttributes()) {
                String sourceAttrName = "Name: " + sourceAttr.getName()
                        + " Table: " + sourceAttr.getTableName()
                        + " DB: " + sourceAttr.getDbName();

                Widget source = addNode(sourceAttrName);
                source.setPreferredLocation(new Point(xsource, ySource));

                ConnectionWidget sourceTrans = this.createConnection(source, transformation);
                connectionLayer.addChild(sourceTrans);
                ySource += 20;
            }

            i++;
            y += 100;
        }

        getActions().addAction(ActionFactory.createZoomAction());
    }

    private ConnectionWidget createConnection(Widget source, Widget destination) {
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        connection.setSourceAnchor(AnchorFactory.createRectangularAnchor(source));
        connection.setTargetAnchor(AnchorFactory.createRectangularAnchor(destination));
        return connection;
    }

    @Override
    protected Widget attachNodeWidget(String n) {
        LabelWidget widget = new LabelWidget(this);

        widget.getActions().addAction(
                ActionFactory.createAlignWithMoveAction(
                        mainLayer,
                        interactionLayer,
                        ActionFactory.createDefaultAlignWithMoveDecorator()));

        widget.setBorder(BorderFactory.createLineBorder());
        widget.setOpaque(true);

        widget.setLabel(n);
        mainLayer.addChild(widget);

        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(String e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeSourceAnchor(String e, String n, String n1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void attachEdgeTargetAnchor(String e, String n, String n1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
