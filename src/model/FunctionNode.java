package model;

import java.util.ArrayList;

public class FunctionNode extends DataflowNode{

    private String name;
    private ArrayList<DataValueType> arguments = new ArrayList<>();
    private ArrayList<DataValueType> output = new ArrayList<>();

    public FunctionNode(String name) {
        this.name = name;
    }

    @Override
    public int getNumberOfInputs() {
        return arguments.size();
    }

    @Override
    public DataValueType inputTypeFor(int index) {
        return arguments.get(index);
    }

    @Override
    public int getNumberOfOutputs() {
        return output.size();
    }

    @Override
    public DataValueType outputTypeFor(int index) {
        return output.get(index);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
