package model;

public class BooleanInputNode extends DataFlowNode {

    private boolean value = false;

    public BooleanInputNode(double x, double y) {
        super(x, y);
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
