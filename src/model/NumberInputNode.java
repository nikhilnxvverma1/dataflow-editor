package model;

public class NumberInputNode extends DataflowNode{

    double value;

    public NumberInputNode(double value) {
        this.value = value;
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
        return DataValueType.NUMBER;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
