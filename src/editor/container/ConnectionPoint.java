package editor.container;

import app.view.DataFlowView;
import javafx.scene.shape.Circle;

/**
 * This data structure is used during the evaluation of node connections
 */
public class ConnectionPoint {
    public DataFlowView node;
    public int inputIndex;
    public Circle connector;

    public ConnectionPoint(DataFlowView node, int inputIndex, Circle connector) {
        this.node = node;
        this.inputIndex = inputIndex;
        this.connector = connector;
    }
}
