package model;

import java.util.LinkedList;
import java.util.List;

public abstract class DataFlowNode {

    // position of the data flow node in the canvas, anchor point is in the center
    private double x;
    private double y;

    List<DataFlowEdge> incomingEdges = new LinkedList<DataFlowEdge>();
    List<DataFlowEdge> outgoingEdges = new LinkedList<DataFlowEdge>();

    public DataFlowNode(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
