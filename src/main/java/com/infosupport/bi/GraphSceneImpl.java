/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infosupport.bi;

import java.awt.Point;
import java.util.List;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
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
    private WidgetAction reconnectAction;

    public GraphSceneImpl(List<DestinationAttribute> dataflow) {
        mainLayer = new LayerWidget(this);
        connectionLayer = new LayerWidget(this);
        interactionLayer = new LayerWidget(this);
        reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider(this));

        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interactionLayer);

        //Dit moet nog worden gescheiden in functionaliteit zodat het OO is
        int i = 0;
        int xtransformation = 460;
        int y = 10;
        int xdestination = 850;
        int xsource = 10;

        //Gaat door de hele lijst van de data flow
        while (i < dataflow.size()) {
            //Maakt een tranformatiewidget aan
            String transformationString = dataflow.get(i).getTransformation();
            if (transformationString == null) {
                transformationString = "No transformation";
            }
            Widget transformation = addNode(transformationString);
            transformation.setPreferredLocation(new Point(xtransformation, y));

            //Maakt een bestemming aan 
            String destinationAttrName = "Name: " + dataflow.get(i).getDestination().getName() + " Table: "
                    + dataflow.get(i).getDestination().getTableName() + " DB: "
                    + dataflow.get(i).getDestination().getDbName();

            Widget destination = addNode(destinationAttrName);
            destination.setPreferredLocation(new Point(xdestination, y));

            //Maakt koppeling tussen transformatie en bestemming aan
            String transDestConn = "transDest" + y;
            this.addEdge(transDestConn);

            this.setEdgeSource(transDestConn, transformationString);
            this.setEdgeTarget(transDestConn, destinationAttrName);

            //Gaat door lijst van de bronattributen en maakt deze aan en koppelt deze aan de transformatie
            int ySource = y;
            for (Attribute sourceAttr : dataflow.get(i).getSourceAttributes()) {
                String sourceAttrName = "Name: " + sourceAttr.getName()
                        + " Table: " + sourceAttr.getTableName()
                        + " DB: " + sourceAttr.getDbName();

                Widget source = addNode(sourceAttrName);
                source.setPreferredLocation(new Point(xsource, ySource));

                String sourceTrans = "sourceTrans" + ySource;
                this.addEdge(sourceTrans);

                this.setEdgeSource(sourceTrans, sourceAttrName);
                this.setEdgeTarget(sourceTrans, transformationString);
                ySource += 20;
            }

            i++;
            y += 100;
        }

        getActions().addAction(ActionFactory.createZoomAction());
    }

    @Override
    protected Widget attachNodeWidget(String n) {
        LabelWidget widget = new TransformationWidget(this);

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
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
        connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
        connection.getActions().addAction(createSelectAction());
        connection.getActions().addAction(reconnectAction);
        connectionLayer.addChild(connection);
        return connection;
    }

    @Override
    protected void attachEdgeSourceAnchor(String edge, String oldSourceNode, String sourceNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget sourceNodeWidget = findWidget(sourceNode);
        widget.setSourceAnchor(sourceNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(sourceNodeWidget, true) : null);
    }

    @Override
    protected void attachEdgeTargetAnchor(String edge, String oldTargetNode, String targetNode) {
        ConnectionWidget widget = (ConnectionWidget) findWidget(edge);
        Widget targetNodeWidget = findWidget(targetNode);
        widget.setTargetAnchor(targetNodeWidget != null ? AnchorFactory.createFreeRectangularAnchor(targetNodeWidget, true) : null);
    }

}
