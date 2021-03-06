package app.view;

import editor.util.Logger;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.DataFlowEdge;
import model.DataFlowNode;

public class DataFlowEdgeView extends Line {

    public static final Paint INCOMPATIBLE_TYPES_COLOR = Color.RED;
    public static final Paint COMPATIBLE_TYPES_COLOR = Color.LIGHTGREEN;
    public static final Paint UNCONNECTED_TYPES_COLOR = new Color(0,0,0,0.3);
    public static final Paint CONNECTED_TYPES_COLOR = Color.BLACK;
    public static final double STROKE_WIDTH = 2;
    private DataFlowEdge edge;
    private DataFlowView fromView;
    private DataFlowView toView;
    private ChangeListener<Number> outputXListener;
    private ChangeListener<Number> outputYListener;
    private ChangeListener<Number> inputXListener;
    private ChangeListener<Number> inputYListener;


    /**
     * Constructs a partial data flow edge view without the ending endpoint useful for handling event cases
     * @param fromView the data flow node view from which this event originated
     * @param fromIndex the index of the output connector of the said data flow node
     */
    DataFlowEdgeView(DataFlowView fromView, int fromIndex) {
        this.fromView = fromView;
        this.edge = new DataFlowEdge();
        this.edge.setFrom(fromView.getDataFlowNode());
        this.edge.setFromOutputIndex(fromIndex);
        this.setStroke(UNCONNECTED_TYPES_COLOR);
        this.setStrokeWidth(DataFlowEdgeView.STROKE_WIDTH);

    }

    /**
     * Constructs a full data flow edge view useful when creating the edge view from memory
     * @param fromView the data flow node view from which this connection starts (output side)
     * @param fromIndex the index of the output connector of the said output data flow node
     * @param toView the data flow node view to which this connection ends (input side)
     * @param toIndex the index of the input connector of the said input data flow node
     */
    public DataFlowEdgeView(DataFlowView fromView, int fromIndex, DataFlowView toView, int toIndex) {

        this.fromView = fromView;
        this.toView = toView;

        this.edge = new DataFlowEdge();
        this.edge.setFrom(fromView.getDataFlowNode());
        this.edge.setFromOutputIndex(fromIndex);
        this.edge.setTo(toView.getDataFlowNode());
        this.edge.setToInputIndex(toIndex);

        this.setStroke(CONNECTED_TYPES_COLOR);
        this.setStrokeWidth(DataFlowEdgeView.STROKE_WIDTH);
        addPositionalChangeListeners();
    }

    public DataFlowEdge getEdge() {
        return edge;
    }

    public DataFlowView getFromView() {
        return fromView;
    }

    public void setFromView(DataFlowView fromView) {
        this.fromView = fromView;
    }

    public DataFlowView getToView() {
        return toView;
    }

    /**
     * Attempts to set the input node if the types match. If the types don't match, it simply sets it to null.
     * Also colors the edge appropriately to suggest the status of the type match.
     * @param inputNode the input node
     * @param inputIndex the index of the channel of the input node
     * @return true if the types match and the node is set, false otherwise
     */
    boolean checkAndSetInputNode(DataFlowView inputNode, int inputIndex) {

        // check if the types are compatible or not
        Class inputType = inputNode.getTypeForInput(inputIndex);
        Class outputType = fromView.getTypeForOutput(edge.getFromOutputIndex());
        if (inputType.isAssignableFrom(outputType)){ // compatible

            //change the color of the edge to indicate compatibility
            this.setStroke(DataFlowEdgeView.COMPATIBLE_TYPES_COLOR);

            // make the connection
            this.toView = inputNode;
            this.edge.setTo(inputNode.getDataFlowNode());
            this.edge.setToInputIndex(inputIndex);
            return true;
        }else{ // incompatible

            // change the color of the edge to indicate incompatibility
            this.setStroke(DataFlowEdgeView.INCOMPATIBLE_TYPES_COLOR);

            // nullify input node
            nullifyInputNode();
            return false;
        }
    }

    /** Nulls out the input node and sets the input index to -1 */
    void nullifyInputNode(){
        this.toView = null;
        this.edge.setTo(null);
        this.edge.setToInputIndex(-1);
    }

    /**
     * Defines and adds  change listeners on the translation properties of connected nodes (i.e. fromView and toView)
     * such that whenever the position of those nodes changes, coordinates of this edge also change automatically
     */
    public void addPositionalChangeListeners(){


        outputXListener = (observable, oldValue, newValue) -> {
            Circle outputConnector = fromView.getOutputConnectorAt(edge.getFromOutputIndex());
            Point2D connectorPositionInEditSpace = connectorToEditSpace(outputConnector,Point2D.ZERO);
            DataFlowEdgeView.this.setStartX(connectorPositionInEditSpace.getX());
        };
        fromView.translateXProperty().addListener(outputXListener);

        outputYListener = (observable, oldValue, newValue) -> {
            Circle outputConnector = fromView.getOutputConnectorAt(edge.getFromOutputIndex());
            Point2D connectorPositionInEditSpace = connectorToEditSpace(outputConnector,Point2D.ZERO);
            DataFlowEdgeView.this.setStartY(connectorPositionInEditSpace.getY());
        };
        fromView.translateYProperty().addListener(outputYListener);

        inputXListener = (observable, oldValue, newValue) -> {
            Circle inputConnector = toView.getInputConnectorAt(edge.getToInputIndex());
            Point2D connectorPositionInEditSpace = connectorToEditSpace(inputConnector,Point2D.ZERO);
            DataFlowEdgeView.this.setEndX(connectorPositionInEditSpace.getX());
        };
        toView.translateXProperty().addListener(inputXListener);

        inputYListener = (observable, oldValue, newValue) -> {
            Circle inputConnector = toView.getInputConnectorAt(edge.getToInputIndex());
            Point2D connectorPositionInEditSpace = connectorToEditSpace(inputConnector,Point2D.ZERO);
            DataFlowEdgeView.this.setEndY(connectorPositionInEditSpace.getY());
        };
        toView.translateYProperty().addListener(inputYListener);

    }

    private void updatePositionEndpoints(){
        Circle outputConnector = fromView.getOutputConnectorAt(edge.getFromOutputIndex());
        Point2D outputPositionInEditSpace = connectorToEditSpace(outputConnector,Point2D.ZERO);
        DataFlowEdgeView.this.setStartX(outputPositionInEditSpace.getX());
        DataFlowEdgeView.this.setStartY(outputPositionInEditSpace.getY());

        Circle inputConnector = toView.getInputConnectorAt(edge.getToInputIndex());
        Point2D inputPositionInEditSpace = connectorToEditSpace(inputConnector,Point2D.ZERO);
        DataFlowEdgeView.this.setEndX(inputPositionInEditSpace.getX());
        DataFlowEdgeView.this.setEndY(inputPositionInEditSpace.getY());

    }

    /**
     * Removes any change listeners defined on the translation properties of the associated nodes
     */
    public void removePositionalChangeListeners(){
        fromView.translateXProperty().removeListener(outputXListener);
        fromView.translateYProperty().removeListener(outputYListener);
        toView.translateXProperty().removeListener(inputXListener);
        toView.translateYProperty().removeListener(inputYListener);
    }

    /**
     * Adds the edge view and model to the edge list of associated nodes (for both node models and node views)
     */
    public void connectToNodes(){
        // add the edge model to the edge list of associated node models
        DataFlowNode fromNode = edge.getFrom();
        fromNode.getOutgoingEdges().add(edge);

        DataFlowNode toNode = edge.getTo();
        toNode.getIncomingEdges().add(edge);

        // add the edge view (this) to the edge list of associated node views
        fromView.getOutgoingEdges().add(this);
        toView.getIncomingEdges().add(this);
    }

    /**
     * Removes the edge view and model from the edge list of associated nodes (for both node models and views)
     */
    public void disconnectFromNodes(){

        // remove the edge model from the edge list of associated node models
        DataFlowNode fromNode = edge.getFrom();
        fromNode.getOutgoingEdges().remove(edge);

        DataFlowNode toNode = edge.getTo();
        toNode.getIncomingEdges().remove(edge);

        // remove the edge view (this) from the edge list of associated node views
        fromView.getOutgoingEdges().remove(this);
        toView.getIncomingEdges().remove(this);
    }

    /**
     * Converts a point from the local space of a connector to edit space by making intermediately
     * converting into group space first. This method only works if the connector is a direct child of
     * the group
     * @param connector a node that is a direct child of a {@link DataFlowView} group
     * @param p the point in the local space of the connector
     * @return a point in the edit space (or informally, this will be the structure's group)
     */
    public static Point2D connectorToEditSpace(Node connector, Point2D p){
        Point2D groupSpace = connector.localToParent(p);
        return connector.getParent().localToParent(groupSpace);
    }
}
