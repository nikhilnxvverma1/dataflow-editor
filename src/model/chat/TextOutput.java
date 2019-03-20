package model.chat;

import model.DataFlowNode;

import java.util.LinkedList;

/**
 * Formatted text output node for chat. The argument list comprises of a list of primitive objects that can be used
 * in the formatted text.
 */
public class TextOutput extends DataFlowNode {

    private String text;
    private LinkedList<Object> arguments;

    public TextOutput() {
    }

    public TextOutput(String text, LinkedList<Object> arguments) {
        this.text = text;
        this.arguments = arguments;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LinkedList<Object> getArguments() {
        return arguments;
    }

    public void setArguments(LinkedList<Object> arguments) {
        this.arguments = arguments;
    }
}
