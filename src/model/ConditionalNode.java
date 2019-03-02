package model;

public class ConditionalNode extends DataFlowNode {
    protected Type type = Type.LESS_THAN;

    public enum Type{
        LESS_THAN,
        LESS_THAN_EQUAL_TO,
        GREATER_THAN,
        GREATER_THAN_EQUAL_TO,
        EQUALS,
        NOT_EQUALS,
        AND,
        OR,
        NOT
    }

    public ConditionalNode(double x, double y) {
        super(x, y);
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
