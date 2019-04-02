package model;

import javafx.scene.control.ColorPicker;
import model.util.Color;

public class ColorInputNode extends DataFlowNode {

    private Color color = new Color();

    public ColorInputNode(double x, double y) {
        super(x, y);
        ColorPicker c = new ColorPicker();
    }

    public ColorInputNode(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
