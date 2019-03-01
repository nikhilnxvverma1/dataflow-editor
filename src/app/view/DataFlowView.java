package app.view;

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
 * Base class for all data flow node views. This class holds a listener that handles all business logic
 * of the events.
 */
public abstract class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;
    protected static final double INPUT_RADIUS = 10;
    protected static final double OUTPUT_RADIUS = 10;

    private MoveDataFlowView moveCommand;


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
            List<ConnectionPoint> inputConnectionPoints = dataFlowViewListener.getInputConnectionPoints(this);

            // mouse press
            outputConnector.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    event.consume();
                }
            });

            // mouse drag
            outputConnector.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    for(ConnectionPoint inputConnectionPoint:inputConnectionPoints){

                        // transform both event point and input connection point to scene space
                        Point2D eventScenePoint = outputConnector.localToScene(event.getX(), event.getY());
                        Point2D inputScenePoint = inputConnectionPoint.connector.localToScene(0, 0);

                        // check if they are close enough
                        if(inputScenePoint.distance(eventScenePoint)<=OUTPUT_RADIUS){

                            // check if the types are compatible or not
                            Class inputType = inputConnectionPoint.node.getTypeForInput(inputConnectionPoint.index);
                            Class outputType = getTypeForOutput(outputIndex);
                            if (inputType.isAssignableFrom(outputType)){

                                // make the connection
                                Logger.debug("compatible types");
                            }else{
                                Logger.debug("incompatible types");
                            }
                        }else{
                            Logger.debug("Not joining");
                        }
                    }

                    event.consume();
                }
            });

            // mouse release
            outputConnector.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
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
