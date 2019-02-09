package app.view;

import javafx.scene.shape.Circle;
import model.ArithmeticNode;
import model.DataFlowNode;

/**
 * View for arithmetic data flow node having 2 inputs and one output
 */
public class ArithmeticNodeView extends DataFlowView{

    // TODO write their positions and radii
    private Circle mainCircle = new Circle();
    private Circle inputHandle1 = new Circle();
    private Circle inputHandle2 = new Circle();
    private Circle outputHandle = new Circle();

    private ArithmeticNode arithmeticNode;

    public ArithmeticNodeView(DataFlowViewListener dataFlowViewListener) {
        super(dataFlowViewListener);
        initialize();
    }

    @Override
    public DataFlowNode getDataFlowNode() {
        return arithmeticNode;
    }

    private void initialize(){
        this.getChildren().addAll(mainCircle,inputHandle1,inputHandle2,outputHandle);
        //TODO setup event handling appropriately
    }
}
