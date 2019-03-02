package model;

public class NumberInputNode extends DataFlowNode {

    double value = 0;

    public NumberInputNode(double x, double y) {
        super(x, y);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
