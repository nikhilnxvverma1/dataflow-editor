package app.view;

import javafx.scene.Group;
import model.DataFlowNode;

/**
 * Base class for all data flow node views. This class holds a listener that handles all business logic
 * of the events.
 */
public abstract class DataFlowView extends Group {

    private DataFlowViewListener dataFlowViewListener;

    public DataFlowView(DataFlowViewListener dataFlowViewListener) {
        this.dataFlowViewListener = dataFlowViewListener;
    }

    public abstract DataFlowNode getDataFlowNode();

}
