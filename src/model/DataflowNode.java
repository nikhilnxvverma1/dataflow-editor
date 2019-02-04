package model;

import java.util.LinkedList;
import java.util.List;

public abstract class DataflowNode {
    List<DataflowEdge> incomingEdges = new LinkedList<DataflowEdge>();
    List<DataflowEdge> outgoingEdges = new LinkedList<DataflowEdge>();

    public abstract int getNumberOfInputs();
    public abstract DataValueType inputTypeFor(int index);
    public abstract int getNumberOfOutputs();
    public abstract DataValueType outputTypeFor(int index);
}
