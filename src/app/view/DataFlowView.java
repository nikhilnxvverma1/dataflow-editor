package app.view;

import editor.command.MoveDataFlowView;
import editor.util.Logger;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import model.DataFlowNode;

/**
 * Base class for all data flow node views. This class holds a listener that handles all business logic
 * of the events.
 */
public abstract class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;


    private MoveDataFlowView moveCommand;

    EventHandler<MouseEvent> outputConnectorPressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Logger.debug("pressed connection");
            event.consume();
        }
    };
    EventHandler<MouseEvent> outputConnectorDragged = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Logger.debug("dragged connection");
            event.consume();
        }
    };
    EventHandler<MouseEvent> outputConnectorReleased = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Logger.debug("released connection");
            event.consume();
        }
    };

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
