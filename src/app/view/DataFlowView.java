package app.view;

import editor.command.CreateDataFlowEdge;
import editor.command.MoveDataFlowView;
import editor.container.ConnectionPoint;
import editor.util.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import model.DataFlowNode;

import java.util.List;

/**
 * Base class for all data flow node views. This class holds a listener that provides context specific
 * requirements to the events.
 */
public abstract class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;
    protected static final double INPUT_RADIUS = 10;
    protected static final double OUTPUT_RADIUS = 10;

    private MoveDataFlowView moveCommand;
    private CreateDataFlowEdge createEdge;


    public DataFlowView(DataFlowViewListener dataFlowViewListener) {
        this.dataFlowViewListener = dataFlowViewListener;

        // event handling for dragging (moving) the node (Mouse press, drag, release)

        this.setOnMousePressed(event -> moveCommand = new MoveDataFlowView(this));

        this.setOnMouseDragged(event -> {
            double newX = this.getTranslateX() + event.getX();
            double newY = this.getTranslateY() + event.getY();
            this.setLocation(newX,newY);
            moveCommand.setFinalX(this.getTranslateX());
            moveCommand.setFinalY(this.getTranslateY());
        });

        this.setOnMouseReleased(event -> {
            if (moveCommand.didMakeMovement()) {
                this.getDataFlowNode().setX(moveCommand.getFinalX());
                this.getDataFlowNode().setY(moveCommand.getFinalY());
                this.dataFlowViewListener.registerCommand(moveCommand, false);
            }
        });
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

                    // get event point in scene space and use that for rendering and computation
                    DataFlowEdgeView edgeView = createEdge.getEdgeView();
                    Point2D eventScenePoint = outputConnector.localToScene(event.getX(), event.getY());
                    edgeView.setEndX(eventScenePoint.getX());
                    edgeView.setEndY(eventScenePoint.getY());

                    List<ConnectionPoint> inputConnectionPoints = dataFlowViewListener.
                            getInputConnectionPoints(DataFlowView.this);

                    edgeView.setStroke(DataFlowEdgeView.UNCONNECTED_TYPES_COLOR);
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
                        edgeView.connectToNodeModels();
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

    public void setLocation(double x,double y){
        this.setTranslateX(x);
        this.setTranslateY(y);
        getDataFlowNode().setX(x);
        getDataFlowNode().setY(y);
    }

    public abstract DataFlowNode getDataFlowNode();

    public abstract int totalInputChannels();

    public abstract int totalOutputChannels();

    public abstract Circle getInputConnectorAt(int index);

    public abstract Circle getOutputConnectorAt(int index);

    public abstract Class getTypeForInput(int index);

    public abstract Class getTypeForOutput(int index);

}
