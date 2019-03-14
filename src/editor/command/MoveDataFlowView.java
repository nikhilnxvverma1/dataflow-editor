package editor.command;

import app.view.DataFlowView;
import app.view.DataFlowViewListener;

import java.util.List;

public class MoveDataFlowView extends CanvasCommand {

    private List<DataFlowView> nodesToMove;
    private DataFlowViewListener listener;
    private double deltaX;
    private double deltaY;

    public MoveDataFlowView(DataFlowViewListener listener) {
        this.nodesToMove = listener.currentSelection();
        this.listener = listener;
    }

    @Override
    public void undo() {
        for(DataFlowView each : nodesToMove){
            double initialX = each.getDataFlowNode().getX() - deltaX;
            double initialY = each.getDataFlowNode().getY() - deltaY;
            each.getDataFlowNode().setX(initialX);
            each.getDataFlowNode().setY(initialY);
            each.setTranslateX(initialX);
            each.setTranslateY(initialY);
        }
        listener.movedBy(-deltaX,-deltaY);
    }

    @Override
    public void redo() {

        for(DataFlowView each : nodesToMove){
            double finalX = each.getDataFlowNode().getX() + deltaX;
            double finalY = each.getDataFlowNode().getY() + deltaY;
            each.getDataFlowNode().setX(finalX);
            each.getDataFlowNode().setY(finalY);
            each.setTranslateX(finalX);
            each.setTranslateY(finalY);
        }

        listener.movedBy(deltaX,deltaY);
    }

    public boolean didMakeMovement(){
        return deltaX!=0 || deltaY!=0;
    }

    public void moveAllNodesBy(double dx,double dy){
        //move the selection highlight by the delta difference
        listener.movedBy(dx,dy);

        // move each node by delta difference
        for(DataFlowView each : nodesToMove){
            double newX = each.getTranslateX() + dx;
            double newY = each.getTranslateY() + dy;
            each.setLocation(newX,newY);
        }
        deltaX+=dx;
        deltaY+=dy;
    }
}
