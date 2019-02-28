package model;

public class DataFlowEdge {
    DataFlowNode from;
    DataFlowNode to;
    int fromOutputIndex = -1;
    int toInputIndex = -1;

    public DataFlowNode getFrom() {
        return from;
    }

    public void setFrom(DataFlowNode from) {
        this.from = from;
    }

    public DataFlowNode getTo() {
        return to;
    }

    public void setTo(DataFlowNode to) {
        this.to = to;
    }

    public int getFromOutputIndex() {
        return fromOutputIndex;
    }

    public void setFromOutputIndex(int fromOutputIndex) {
        this.fromOutputIndex = fromOutputIndex;
    }

    public int getToInputIndex() {
        return toInputIndex;
    }

    public void setToInputIndex(int toInputIndex) {
        this.toInputIndex = toInputIndex;
    }
}
