package app.view;

import javafx.scene.Group;
import model.DataFlowNode;

public abstract class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;

    public DataFlowView(DataFlowViewListener dataFlowViewListener) {
        this.dataFlowViewListener = dataFlowViewListener;
    }

    public abstract DataFlowNode getDataFlowNode();

}
