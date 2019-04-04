package model;

public class ArgumentNode extends DataFlowNode{

    private String name;

    public ArgumentNode(String name) {
        this.name = name;
    }

    public ArgumentNode(double x, double y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
