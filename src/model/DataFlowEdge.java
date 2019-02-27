package model;

public class DataFlowEdge {
    DataFlowNode from;
    DataFlowNode to;
    int fromOutputIndex = -1;
    int toInputIndex = -1;
}
