package app.view;

import editor.command.MoveDataFlowView;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import model.DataFlowNode;

/**
 * Base class for all data flow node views. This class holds a listener that handles all business logic
 * of the events.
 */
public abstract class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;

    private MoveDataFlowView moveCommand;

    protected EventHandler<MouseEvent> pressed = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            moveCommand = new MoveDataFlowView(DataFlowView.this);
        }
    };

    protected EventHandler<MouseEvent> dragged = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            double newX = DataFlowView.this.getTranslateX() + event.getX();
            double newY = DataFlowView.this.getTranslateY() + event.getY();
            DataFlowView.this.setTranslateX(newX);
            DataFlowView.this.setTranslateY(newY);
            moveCommand.setFinalX(DataFlowView.this.getTranslateX());
            moveCommand.setFinalY(DataFlowView.this.getTranslateY());
        }
    };

    protected EventHandler<MouseEvent> released = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(moveCommand.didMakeMovement()){
                DataFlowView.this.getDataFlowNode().setX(moveCommand.getFinalX());
                DataFlowView.this.getDataFlowNode().setY(moveCommand.getFinalY());
                DataFlowView.this.dataFlowViewListener.registerCommand(moveCommand,false);
            }
        }
    };

    public DataFlowView(DataFlowViewListener dataFlowViewListener) {
        this.dataFlowViewListener = dataFlowViewListener;

        // event handling for dragging (moving) the node
        this.setOnMousePressed(pressed);
        this.setOnMouseDragged(dragged);
        this.setOnMouseReleased(released);
    }

    public abstract DataFlowNode getDataFlowNode();

}
