package app.view;

import javafx.scene.shape.Line;
import model.DataFlowEdge;

public class DataFlowEdgeView extends Line {
    private DataFlowEdge edge;
    private DataFlowView fromView;
    private DataFlowView toView;

    public DataFlowEdgeView(DataFlowView fromView,int fromIndex, DataFlowView toView, int toIndex) {

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
}
