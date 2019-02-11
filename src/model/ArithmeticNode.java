package model;

public class ArithmeticNode extends DataFlowNode {

    protected Type type = Type.ADD;


    public enum Type{
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        MODULO,
    }

    public ArithmeticNode(double x, double y) {
        super(x, y);
    }

    @Override
    public int getNumberOfInputs() {
        return 2;
    }

    @Override
    public DataValueType inputTypeFor(int index) {
        return DataValueType.NUMBER;
    }

    @Override
    public int getNumberOfOutputs() {
        return 1;
    }

    @Override
    public DataValueType outputTypeFor(int index) {
        return DataValueType.NUMBER;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
