package model;

import java.util.LinkedList;
import java.util.List;

public abstract class DataFlowNode {
    List<DataFlowEdge> incomingEdges = new LinkedList<DataFlowEdge>();
    List<DataFlowEdge> outgoingEdges = new LinkedList<DataFlowEdge>();

    public abstract int getNumberOfInputs();
    public abstract DataValueType inputTypeFor(int index);
    public abstract int getNumberOfOutputs();
    public abstract DataValueType outputTypeFor(int index);
}
