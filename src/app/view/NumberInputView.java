package app.view;

import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.DataFlowNode;
import model.NumberInputNode;

/**
 * Data flow View with a text field that allows for number input
 */
public class NumberInputView extends DataFlowView{
    // radii
    private static final double MAIN_RECT_LENGTH = 100;
    private static final double MAIN_RECT_HEIGHT= 60;

    // color
    private static final Paint MAIN_COLOR = Color.WHITESMOKE;
    private static final Paint OUTPUT_COLOR = Color.BISQUE;

    private Rectangle mainRect = new Rectangle(MAIN_RECT_LENGTH,MAIN_RECT_HEIGHT);
    private Circle outputHandle = new Circle(OUTPUT_RADIUS);

    private static final Font FONT = new Font(80);

    private NumberInputNode numberInputNode;

    public NumberInputView(NumberInputNode numberInputNode,DataFlowViewListener dataFlowViewListener) {
        super(dataFlowViewListener);
        this.numberInputNode = numberInputNode;
        initialize();
    }

    /**
     * Setup of views and events in this group. All event logic is handled by the listener
     */
    private void initialize(){

        // position is based on the position of the arithmetic node
        this.setTranslateX(numberInputNode.getX());
        this.setTranslateY(numberInputNode.getY());

        // main circle
        mainRect.setFill(MAIN_COLOR);
        mainRect.setLayoutY(-MAIN_RECT_HEIGHT/2);

        // output handle
        outputHandle.setLayoutX(MAIN_RECT_LENGTH + OUTPUT_RADIUS/3);
        outputHandle.setLayoutY(0);
        outputHandle.setFill(OUTPUT_COLOR);

        // input field

        Spinner<Integer> field = new Spinner<>();
        field.setPrefWidth(MAIN_RECT_LENGTH);
        field.setPrefHeight(MAIN_RECT_HEIGHT/2);
        field.setLayoutX(0);
        field.setLayoutY(-MAIN_RECT_HEIGHT/4);

        this.getChildren().addAll(outputHandle, mainRect, field);

        setupOutputHandlers();

    }

    @Override
    public DataFlowNode getDataFlowNode() {
        return numberInputNode;
    }

    @Override
    public int totalInputChannels() {
        return 0;
    }

    @Override
    public int totalOutputChannels() {
        return 1;
    }

    @Override
    public Circle getInputConnectorAt(int index) {
        return null;
    }

    @Override
    public Circle getOutputConnectorAt(int index) {
        return index==0?outputHandle:null;
    }

    @Override
    public Class getTypeForInput(int index) {
        return null;
    }

    @Override
    public Class getTypeForOutput(int index) {
        return double.class;
    }
}
