package app.view;

import javafx.scene.Group;

public class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;

    public DataFlowView(DataFlowViewListener dataFlowViewListener) {
        this.dataFlowViewListener = dataFlowViewListener;
    }

}
