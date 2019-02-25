package editor.command;

import app.view.DataFlowView;

public class MoveDataFlowView extends CanvasCommand {

    private DataFlowView dataFlowView;
    private double initialX;
    private double initialY;
    private double finalX;
    private double finalY;

    public MoveDataFlowView(DataFlowView dataFlowView) {
        this.dataFlowView = dataFlowView;
        this.initialX = dataFlowView.getTranslateX();
        this.initialY = dataFlowView.getTranslateY();
        this.finalX = this.initialX;
        this.finalY = this.initialY;
    }

    @Override
    public void undo() {
        dataFlowView.getDataFlowNode().setX(initialX);
        dataFlowView.getDataFlowNode().setY(initialY);
        dataFlowView.setTranslateX(initialX);
        dataFlowView.setTranslateY(initialY);
    }

    @Override
    public void redo() {
        dataFlowView.getDataFlowNode().setX(finalX);
        dataFlowView.getDataFlowNode().setY(finalY);
        dataFlowView.setTranslateX(finalX);
        dataFlowView.setTranslateY(finalY);
    }

    public double getInitialX() {
        return initialX;
    }

    public double getInitialY() {
        return initialY;
    }

    public void setFinalX(double finalX) {
        this.finalX = finalX;
    }

    public void setFinalY(double finalY) {
        this.finalY = finalY;
    }

    public double getFinalX() {
        return finalX;
    }

    public double getFinalY() {
        return finalY;
    }

    public boolean didMakeMovement(){
        return initialX!=finalX || initialY!=finalY;
    }
}
