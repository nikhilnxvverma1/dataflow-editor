package model;

public class ConditionalNode extends DataFlowNode {
    protected Type type = Type.LESS_THAN;

    public enum Type{
        LESS_THAN,
        LESS_THAN_EQUAL_TO,
        GREATER_THAN,
        GREATER_THAN_EQUAL_TO,
        EQUALS,
        AND,
        OR,
        NOT
    }

    public ConditionalNode(double x, double y) {
        super(x, y);
    }

    @Override
    public int getNumberOfInputs() {
        if(type==Type.NOT){
            return 1;
        }else{
            return 2;
        }
    }

    @Override
    public DataValueType inputTypeFor(int index) {
        return DataValueType.BOOLEAN;
    }

    @Override
    public int getNumberOfOutputs() {
        return 1;
    }

    @Override
    public DataValueType outputTypeFor(int index) {
        return DataValueType.BOOLEAN;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
