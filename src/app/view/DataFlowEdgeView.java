package app.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import model.DataFlowEdge;

public class DataFlowEdgeView extends Line {
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
    public DataFlowEdgeView(DataFlowView fromView, int fromIndex) {
        this.fromView = fromView;
        this.edge = new DataFlowEdge();
        this.edge.setFromOutputIndex(fromIndex);
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

        initialize();
    }

    private void initialize(){
        // TODO get the position of the output node of from

        // TODO get the position fo the input node of to
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

    public void setToView(DataFlowView toView) {
        this.toView = toView;
    }

    /**
     * Defines and adds  change listeners on the translation properties of connected nodes (i.e. fromView and toView)
     * such that whenever the position of those nodes changes, coordinates of this edge also change automatically
     */
    public void addPositionalChangeListeners(){


        outputXListener = (observable, oldValue, newValue) -> {
            Circle outputConnector = fromView.getOutputConnectorAt(edge.getFromOutputIndex());
            Point2D connectorPositionInScene = outputConnector.localToScene(0, 0);
            DataFlowEdgeView.this.setStartX(connectorPositionInScene.getX());
        };
        fromView.translateXProperty().addListener(outputXListener);

        outputYListener = (observable, oldValue, newValue) -> {
            Circle outputConnector = fromView.getOutputConnectorAt(edge.getFromOutputIndex());
            Point2D connectorPositionInScene = outputConnector.localToScene(0, 0);
            DataFlowEdgeView.this.setStartY(connectorPositionInScene.getY());
        };
        fromView.translateYProperty().addListener(outputYListener);

        inputXListener = (observable, oldValue, newValue) -> {
            Circle inputConnector = toView.getInputConnectorAt(edge.getToInputIndex());
            Point2D connectorPositionInScene = inputConnector.localToScene(0, 0);
            DataFlowEdgeView.this.setStartX(connectorPositionInScene.getX());
        };
        toView.translateXProperty().addListener(inputXListener);

        inputYListener = (observable, oldValue, newValue) -> {
            Circle inputConnector = toView.getInputConnectorAt(edge.getToInputIndex());
            Point2D connectorPositionInScene = inputConnector.localToScene(0, 0);
            DataFlowEdgeView.this.setStartY(connectorPositionInScene.getY());
        };
        toView.translateYProperty().addListener(inputYListener);

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
}
