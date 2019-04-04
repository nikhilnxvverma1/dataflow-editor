package app.view;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.ArgumentNode;
import model.DataFlowNode;

public class ArgumentNodeView extends DataFlowView{

    // radii
    private static final double MAIN_RECT_LENGTH = 100;
    private static final double MAIN_RECT_HEIGHT= 60;

    // color
    private static final Paint MAIN_COLOR = Color.DARKGRAY;

    private Rectangle mainRect = new Rectangle(MAIN_RECT_LENGTH,MAIN_RECT_HEIGHT);
    private Circle outputHandle = new Circle(OUTPUT_RADIUS);
    private TextField field = new TextField();

    private ArgumentNode argumentNode;

    public ArgumentNodeView(ArgumentNode argumentNode, DataFlowViewListener dataFlowViewListener) {
        super(dataFlowViewListener);
        this.argumentNode = argumentNode;
        initialize();
    }

    /**
     * Setup of views and events in this group. All event logic is handled by the listener
     */
    private void initialize(){

        // position is based on the position of the arithmetic node
        this.setTranslateX(argumentNode.getX());
        this.setTranslateY(argumentNode.getY());

        // main circle
        mainRect.setFill(MAIN_COLOR);
        mainRect.setLayoutY(-MAIN_RECT_HEIGHT/2);
        mainRect.setArcHeight(CORNER_ARC);
        mainRect.setArcWidth(CORNER_ARC);

        // output handle
        outputHandle.setLayoutX(MAIN_RECT_LENGTH + OUTPUT_RADIUS/3);
        outputHandle.setLayoutY(0);
        outputHandle.setFill(OUTPUT_COLOR);

        // input field

        field.setPrefWidth(MAIN_RECT_LENGTH-2*OUTPUT_RADIUS);
        field.setPrefHeight(MAIN_RECT_HEIGHT/2);
        field.setLayoutX(OUTPUT_RADIUS);
        field.setLayoutY(-MAIN_RECT_HEIGHT/4);
        field.setAlignment(Pos.CENTER_RIGHT);
        field.setDisable(true);

        this.getChildren().addAll(mainRect, field, outputHandle);

        setupOutputHandlers();

    }

    @Override
    public void postViewCreation() {
        field.setDisable(false);
    }

    @Override
    public DataFlowNode getDataFlowNode() {
        return argumentNode;
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
        return Object.class;
    }

    @Override
    public Class getTypeForOutput(int index) {
        return String.class;
    }
}
