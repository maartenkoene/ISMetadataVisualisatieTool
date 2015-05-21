package com.infosupport.bi;

import java.awt.Point;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MaartenKo
 */
public class SceneReconnectProvider implements ReconnectProvider {

    private String edge;
    private String originalNode;
    private String replacementNode;

    private GraphScene scene;
    //dit moet hier echt weg... maar hoe?
    private ChangeHandler changehandler = new ChangeHandler("jdbc:sqlserver://127.0.0.1:1433;databaseName=ISMetadata;integratedSecurity=true;", "Visualisation", "Info2015");

    public SceneReconnectProvider(GraphScene scene) {
        this.scene = scene;
    }

    public void reconnectingStarted(ConnectionWidget connectionWidget, boolean reconnectingSource) {
    }

    public void reconnectingFinished(ConnectionWidget connectionWidget, boolean reconnectingSource) {
    }

    public boolean isSourceReconnectable(ConnectionWidget connectionWidget) {
        return false;
    }

    public boolean isTargetReconnectable(ConnectionWidget connectionWidget) {
        Object object = scene.findObject(connectionWidget);
        edge = scene.isEdge(object) ? (String) object : null;
        originalNode = edge != null ? (String) scene.getEdgeTarget(edge) : null;
        return originalNode != null;
    }

    public ConnectorState isReplacementWidget(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
        Object object = scene.findObject(replacementWidget);
        Widget originalSource = connectionWidget.getSourceAnchor().getRelatedWidget();
        replacementNode = scene.isNode(object) ? (String) object : null;
        if (replacementNode != null) {
            if (originalSource instanceof SourceWidget && replacementWidget instanceof TransformationWidget) {
                return ConnectorState.ACCEPT;
            } else if (originalSource instanceof TransformationWidget && replacementWidget instanceof DestinationWidget) {
                return ConnectorState.ACCEPT;
            }
        }
        return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }

    public boolean hasCustomReplacementWidgetResolver(Scene scene) {
        return false;
    }

    public Widget resolveReplacementWidget(Scene scene, Point sceneLocation) {
        return null;
    }

    public void reconnect(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
        Widget source = connectionWidget.getSourceAnchor().getRelatedWidget();
        Widget target = connectionWidget.getTargetAnchor().getRelatedWidget();
        Widget replacement = replacementWidget;

        if (source instanceof TransformationWidget && replacement instanceof DestinationWidget) {
            TransformationWidget trans = (TransformationWidget) source;
            DestinationWidget dest = (DestinationWidget) replacement;
            DestinationWidget oldDest = (DestinationWidget) target;

            dest.setTransformation(trans.getTransformation());
            trans.setDestinationAttributeID(dest.getDestinationAttributeID());
            oldDest.setTransformation(null);

            for (Integer integer : trans.getSourcesMappingID()) {
                ChangeDestination changeDestination = new ChangeDestination(integer.intValue(), dest.getDestinationAttributeID(),
                        trans.getTransformation(), trans.getTransformation(), oldDest.getDestinationAttributeID());
                changehandler.addChange(changeDestination);
            }

        } else if (source instanceof SourceWidget && replacement instanceof TransformationWidget) {
            SourceWidget origin = (SourceWidget) source;
            TransformationWidget trans = (TransformationWidget) replacement;
            TransformationWidget oldTrans = (TransformationWidget) target;

            oldTrans.removeItemFromSourcesMappingID(origin.getSourceAttributeID());
            trans.addSource(origin.getSourceAttributeID());

            ChangeSource changeSource = new ChangeSource(origin.getMappingID(), trans.getDestinationAttributeID(),
                    trans.getTransformation(), origin.getSourceAttributeID(), origin.getMappingSetID(), oldTrans.getTransformation(),
                    oldTrans.getDestinationAttributeID());
            changehandler.addChange(changeSource);

        }

        if (replacementWidget == null) {
        } else if (reconnectingSource) {
            scene.setEdgeSource(edge, replacementNode);
        } else {
            scene.setEdgeTarget(edge, replacementNode);
        }
        scene.validate();
    }

    public ChangeHandler getChangehandler() {
        return changehandler;
    }

}
