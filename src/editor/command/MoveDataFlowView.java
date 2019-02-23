package editor.command;

import app.view.DataFlowView;

public class MoveDataFlowView implements Command {

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

    }

    @Override
    public void redo() {

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

    public boolean didMakeMovement(){
        return initialX!=finalX || initialY!=finalY;
    }
}
