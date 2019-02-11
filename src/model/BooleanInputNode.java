package model;

public class BooleanInputNode extends DataFlowNode {

    private boolean value = false;

    public BooleanInputNode(double x, double y) {
        super(x, y);
    }

    @Override
    public int getNumberOfInputs() {
        return 0;
    }

    @Override
    public DataValueType inputTypeFor(int index) {
        return null;
    }

    @Override
    public int getNumberOfOutputs() {
        return 1;
    }

    @Override
    public DataValueType outputTypeFor(int index) {
        return DataValueType.BOOLEAN;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
