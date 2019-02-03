package model;

import java.util.LinkedList;
import java.util.List;

public abstract class DataflowNode {
    List<DataflowEdge> incomingEdges = new LinkedList<DataflowEdge>();
    List<DataflowEdge> outgoingEdges = new LinkedList<DataflowEdge>();

}
