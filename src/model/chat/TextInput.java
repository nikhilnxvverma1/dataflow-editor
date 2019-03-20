package model.chat;

import model.DataFlowNode;

/**
 * Simple Text input node model that takes in text
 */
public class TextInput extends DataFlowNode {

    private String text;

    public TextInput() {
    }

    public TextInput(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
