package model;

import java.util.ArrayList;

public class FunctionNode extends DataFlowNode {

    private String name;
    private ArrayList<DataValueType> arguments = new ArrayList<>();
    private ArrayList<DataValueType> output = new ArrayList<>();

    public FunctionNode(double x, double y, String name) {
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
