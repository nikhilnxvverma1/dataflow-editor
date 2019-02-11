package app.view;

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

        this.getChildren().addAll(mainCircle,inputHandle1,inputHandle2,outputHandle);
        //TODO setup event handling appropriately
    }
}
