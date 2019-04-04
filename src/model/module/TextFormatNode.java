package model.module;

import model.DataFlowNode;

import java.util.ArrayList;

public class TextFormatNode extends DataFlowNode {
    private String text;
    private ArrayList<Object> arguments;

    public TextFormatNode(String text, ArrayList<Object> arguments) {
        this.text = text;
        this.arguments = arguments;
    }

    public TextFormatNode(double x, double y) {
        super(x, y);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Object> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<Object> arguments) {
        this.arguments = arguments;
    }
}
