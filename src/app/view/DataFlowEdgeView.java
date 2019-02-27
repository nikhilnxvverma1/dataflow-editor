package app.view;

import javafx.scene.shape.Line;
import model.DataFlowEdge;

public class DataFlowEdgeView extends Line {
    private DataFlowEdge edge;

    public DataFlowEdgeView(DataFlowView from,DataFlowView to) {

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
