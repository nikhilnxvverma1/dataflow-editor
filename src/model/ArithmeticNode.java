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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
