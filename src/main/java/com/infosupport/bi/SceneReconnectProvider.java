package com.infosupport.bi;

import java.awt.Point;
import java.util.ArrayList;
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
            
            System.out.println("Oude destinationID: " + trans.getDestinationAttributeID());
            dest.setTransformation(trans.getTransformation());
            oldDest.setTransformation(null);
            trans.setDestinationAttributeID(dest.getDestinationAttributeID());
            
            System.out.println("We hebben een transformatie: " + trans.getTransformation()+" met nieuwe destination: "+ trans.getDestinationAttributeID());
            System.out.println("Dit is de nieuwe bestemming: " + dest.getDestinationAttributeID() + " " + dest.getLabel());

        } else if (source instanceof SourceWidget && replacement instanceof TransformationWidget) {
            SourceWidget origin = (SourceWidget) source;
            TransformationWidget trans = (TransformationWidget) replacement;
            TransformationWidget oldTrans = (TransformationWidget) target;
            System.out.println("We hebben een source: " + origin.getSourceAttributeID() + " " + origin.getLabel());
            System.out.println("Dit is de nieuwe transformatie: " + trans.getLabel());
            System.out.println("De oude transformatie"+ oldTrans.getLabel());
            
            oldTrans.removeItemFromSourcesMappingID(origin.getSourceAttributeID());
            trans.addSource(origin.getSourceAttributeID());
            
            ArrayList<Integer> sourceMappings = (ArrayList) trans.getSourcesMappingID();
            for(Integer integer : sourceMappings){
            System.out.println("SourceID: "+ integer.toString());
            }
        }

        if (replacementWidget == null) {
        } else if (reconnectingSource) {
            scene.setEdgeSource(edge, replacementNode);
        } else {
            scene.setEdgeTarget(edge, replacementNode);
        }
        scene.validate();
    }

}
