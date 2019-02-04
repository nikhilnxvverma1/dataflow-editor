package model;

public class ArithmeticNode extends DataflowNode {

    protected Type type = Type.ADD;

    public enum Type{
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        MODULO,
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
