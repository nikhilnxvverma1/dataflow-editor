package app.view;

import editor.command.CreateDataFlowEdge;
import editor.command.MoveDataFlowView;
import editor.container.ConnectionPoint;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.DataFlowNode;

import java.util.LinkedList;
import java.util.List;

/**
 * Base class for all data flow node views. This class holds a listener that provides context specific
 * requirements to the events.
 */
public abstract class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;
    protected static final double INPUT_RADIUS = 5;
    protected static final double OUTPUT_RADIUS = 5;

    protected static final Paint INPUT_COLOR = Color.GRAY;
    protected static final Paint OUTPUT_COLOR = Color.GRAY;

    // arc
    protected static final double CORNER_ARC = 40;

    private MoveDataFlowView moveCommand;
    private CreateDataFlowEdge createEdge;

    private List<DataFlowEdgeView> incomingEdges = new LinkedList<>();
    private List<DataFlowEdgeView> outgoingEdges = new LinkedList<>();

    public DataFlowView(DataFlowViewListener dataFlowViewListener) {
        this.dataFlowViewListener = dataFlowViewListener;

        // event handling for dragging (moving) the node (Mouse press, drag, release)
        this.setOnMousePressed(event -> {
            this.dataFlowViewListener.requestSoleSelection(this);
            moveCommand = new MoveDataFlowView(this.dataFlowViewListener);
            event.consume();
        });

        this.setOnMouseDragged(event -> {
            moveCommand.moveAllNodesBy(event.getX(),event.getY());
            event.consume();
        });

        this.setOnMouseReleased(event -> {
            if (moveCommand.didMakeMovement()) {
                this.dataFlowViewListener.registerCommand(moveCommand, false);
            }
            event.consume();
        });
    }

    public void setLocation(double x,double y){
        this.setTranslateX(x);
        this.setTranslateY(y);
        getDataFlowNode().setX(x);
        getDataFlowNode().setY(y);
    }

    void setupOutputHandlers(){
        for (int i = 0; i < totalOutputChannels(); i++) {
            final int outputIndex = i;
            Circle outputConnector = getOutputConnectorAt(outputIndex);

            // mouse press
            outputConnector.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    DataFlowEdgeView edgeView = new DataFlowEdgeView(DataFlowView.this,outputIndex);
                    Point2D eventPoint = new Point2D(event.getX(), event.getY());
                    Point2D editSpacePoint = DataFlowEdgeView.connectorToEditSpace(outputConnector,eventPoint);
                    edgeView.setStartX(editSpacePoint.getX());
                    edgeView.setStartY(editSpacePoint.getY());
                    edgeView.setEndX(editSpacePoint.getX());
                    edgeView.setEndY(editSpacePoint.getY());
                    dataFlowViewListener.getCurrentStructure().group.getChildren().add(edgeView);
                    edgeView.toBack();

                    createEdge = new CreateDataFlowEdge(edgeView,dataFlowViewListener.getCurrentStructure());
                    event.consume();
                }
            });

            // mouse drag
            outputConnector.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    // get event point in edit space and use that for rendering and computation
                    DataFlowEdgeView edgeView = createEdge.getEdgeView();

                    Point2D eventPoint = new Point2D(event.getX(), event.getY());
                    Point2D editSpacePoint = DataFlowEdgeView.connectorToEditSpace(outputConnector,eventPoint);
                    edgeView.setEndX(editSpacePoint.getX());
                    edgeView.setEndY(editSpacePoint.getY());

                    List<ConnectionPoint> inputConnectionPoints = dataFlowViewListener.
                            getAvailableInputConnectionPoints(DataFlowView.this);

                    edgeView.setStroke(DataFlowEdgeView.UNCONNECTED_TYPES_COLOR);
                    edgeView.nullifyInputNode();
                    for(ConnectionPoint inputConnectionPoint:inputConnectionPoints){

                        // transform input connection point to edit space
                        Point2D originPoint = new Point2D(0,0);
                        Point2D inputEditSpacePoint = DataFlowEdgeView.connectorToEditSpace(inputConnectionPoint.connector,originPoint);

                        // check if the input connection point is close enough
                        if(inputEditSpacePoint.distance(editSpacePoint)<=OUTPUT_RADIUS){
                            // check if the types are compatible or not
                            edgeView.checkAndSetInputNode(inputConnectionPoint.node,inputConnectionPoint.index);
                        }
                    }

                    event.consume();
                }
            });

            // mouse release
            outputConnector.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    DataFlowEdgeView edgeView = createEdge.getEdgeView();
                    if(edgeView.getToView()!=null){
                        edgeView.setStroke(DataFlowEdgeView.CONNECTED_TYPES_COLOR);
                        edgeView.addPositionalChangeListeners();
                        edgeView.connectToNodes();
                        dataFlowViewListener.registerCommand(createEdge,false);
                    }else{

                        //remove the view from function definition's group
                        dataFlowViewListener.getCurrentStructure().group.getChildren().remove(edgeView);
                    }
                    event.consume();
                }
            });
        }
    }

    public List<DataFlowEdgeView> getIncomingEdges() {
        return incomingEdges;
    }

    public List<DataFlowEdgeView> getOutgoingEdges() {
        return outgoingEdges;
    }

    /**
     * Lifecycle callback for Data Flow view to do any post view creation handling. This method is called after
     * the user places a node on the workspace. Default implementation does nothing, but derived subclasses may
     * choose to override this method.
     */
    public void postViewCreation(){

    }

    public abstract DataFlowNode getDataFlowNode();

    public abstract int totalInputChannels();

    public abstract int totalOutputChannels();

    public abstract Circle getInputConnectorAt(int index);

    public abstract Circle getOutputConnectorAt(int index);

    public abstract Class getTypeForInput(int index);

    public abstract Class getTypeForOutput(int index);

}
