package app.view;

import editor.command.CreateDataFlowEdge;
import editor.command.MoveDataFlowView;
import editor.container.ConnectionPoint;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
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
                    Point2D outputConnectorScenePoint = outputConnector.localToScene(event.getX(), event.getY());
                    edgeView.setStartX(outputConnectorScenePoint.getX());
                    edgeView.setStartY(outputConnectorScenePoint.getY());
                    edgeView.setEndX(outputConnectorScenePoint.getX());
                    edgeView.setEndY(outputConnectorScenePoint.getY());
                    dataFlowViewListener.getCurrentStructure().pane.getChildren().add(edgeView);
                    edgeView.toBack();

                    createEdge = new CreateDataFlowEdge(edgeView,dataFlowViewListener.getCurrentStructure());
                    event.consume();
                }
            });

            // mouse drag
            outputConnector.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    // get event point in scene space and use that for rendering and computation
                    DataFlowEdgeView edgeView = createEdge.getEdgeView();
                    Point2D eventScenePoint = outputConnector.localToScene(event.getX(), event.getY());
                    edgeView.setEndX(eventScenePoint.getX());
                    edgeView.setEndY(eventScenePoint.getY());

                    List<ConnectionPoint> inputConnectionPoints = dataFlowViewListener.
                            getAvailableInputConnectionPoints(DataFlowView.this);

                    edgeView.setStroke(DataFlowEdgeView.UNCONNECTED_TYPES_COLOR);
                    edgeView.nullifyInputNode();
                    for(ConnectionPoint inputConnectionPoint:inputConnectionPoints){

                        // transform input connection point to scene space
                        Point2D inputScenePoint = inputConnectionPoint.connector.localToScene(0, 0);

                        // check if the input connection point is close enough
                        if(inputScenePoint.distance(eventScenePoint)<=OUTPUT_RADIUS){
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

                        //remove the view from function definition's pane
                        dataFlowViewListener.getCurrentStructure().pane.getChildren().remove(edgeView);
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

    public abstract DataFlowNode getDataFlowNode();

    public abstract int totalInputChannels();

    public abstract int totalOutputChannels();

    public abstract Circle getInputConnectorAt(int index);

    public abstract Circle getOutputConnectorAt(int index);

    public abstract Class getTypeForInput(int index);

    public abstract Class getTypeForOutput(int index);

}
