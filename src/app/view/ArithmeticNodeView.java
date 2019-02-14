package app.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.ArithmeticNode;
import model.DataFlowNode;

/**
 * View for arithmetic data flow node having 2 inputs and one output
 */
public class ArithmeticNodeView extends DataFlowView{

    // radii
    private static final double MAIN_RADIUS = 50;
    private static final double INPUT_RADIUS = 10;
    private static final double OUTPUT_RADIUS = 10;

    //color
    private static final Paint MAIN_COLOR = Color.WHITESMOKE;
    private static final Paint INPUT_COLOR = Color.GRAY;
    private static final Paint OUTPUT_COLOR = Color.BISQUE;

    private Circle mainCircle = new Circle(MAIN_RADIUS);
    private Circle inputHandle1 = new Circle(INPUT_RADIUS);
    private Circle inputHandle2 = new Circle(INPUT_RADIUS);
    private Circle outputHandle = new Circle(OUTPUT_RADIUS);

    private ArithmeticNode arithmeticNode;

    public ArithmeticNodeView(ArithmeticNode arithmeticNode, DataFlowViewListener dataFlowViewListener) {
        super(dataFlowViewListener);
        this.arithmeticNode = arithmeticNode;
        initialize();
    }

    @Override
    public DataFlowNode getDataFlowNode() {
        return arithmeticNode;
    }

    private void initialize(){

        // position is based on the position of the arithmetic node
        this.setLayoutX(arithmeticNode.getX());
        this.setLayoutY(arithmeticNode.getY());

        // main circle
        mainCircle.setLayoutX(-MAIN_RADIUS);
        mainCircle.setLayoutY(-MAIN_RADIUS);
        mainCircle.setFill(MAIN_COLOR);
        mainCircle.setOpacity(0.3);

        // input handle 1
        inputHandle1.setLayoutX(0);
        inputHandle1.setLayoutY(0);
        inputHandle1.setFill(MAIN_COLOR);

        // input handle 2
        inputHandle2.setLayoutX(-INPUT_RADIUS);
        inputHandle2.setLayoutY(-INPUT_RADIUS);
        inputHandle2.setFill(INPUT_COLOR);

        // output handle
        outputHandle.setLayoutX(MAIN_RADIUS);
        outputHandle.setLayoutY(-MAIN_RADIUS);
        outputHandle.setFill(OUTPUT_COLOR);

        this.getChildren().addAll(inputHandle1,inputHandle2,outputHandle,mainCircle);
        //TODO setup event handling appropriately

        this.setStyle("-fx-border-color: red;"); //debugging purposes only(doesn't work)
    }
}
