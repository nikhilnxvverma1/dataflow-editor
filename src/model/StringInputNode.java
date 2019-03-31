package model;

public class StringInputNode extends DataFlowNode {

    String string;

    public StringInputNode(double x, double y) {
        super(x, y);
    }

    public StringInputNode(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
