package app.view;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.ArithmeticNode;
import model.DataFlowNode;

/**
 * View for arithmetic data flow node having 2 inputs and one output
 */
public class ArithmeticNodeView extends DataFlowView{

    // radii
    private static final double MAIN_RADIUS = 20;

    //color
    private static final Paint MAIN_COLOR = Color.DARKGRAY;
    private static final Paint INPUT_COLOR = Color.GRAY;
    private static final Paint OUTPUT_COLOR = Color.GRAY;

    private Circle mainCircle = new Circle(MAIN_RADIUS);
    private Circle inputHandle1 = new Circle(INPUT_RADIUS);
    private Circle inputHandle2 = new Circle(INPUT_RADIUS);
    private Circle outputHandle = new Circle(OUTPUT_RADIUS);

    private static final Font FONT = new Font(MAIN_RADIUS);

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

    @Override
    public int totalInputChannels() {
        return 2;
    }

    @Override
    public int totalOutputChannels() {
        return 1;
    }

    @Override
    public Circle getInputConnectorAt(int index) {
        if(index==0){
            return inputHandle1;
        }else if(index == 1){
            return inputHandle2;
        }else{
            return null;
        }
    }

    @Override
    public Circle getOutputConnectorAt(int index) {
        if(index==0){
            return outputHandle;
        }else{
            return null;
        }
    }

    @Override
    public Class getTypeForInput(int index) {
        if(index == 0 || index == 1 ){
            return Double.class;
        }else{
            return null;
        }
    }

    @Override
    public Class getTypeForOutput(int index) {
        if(index == 0){
            return double.class;
        }else{
            return null;
        }
    }

    /**
     * Setup of views and events in this group. All event logic is handled by the listener
     */
    private void initialize(){

        // position is based on the position of the arithmetic node
        this.setTranslateX(arithmeticNode.getX());
        this.setTranslateY(arithmeticNode.getY());

        // main circle
        mainCircle.setFill(MAIN_COLOR);

        final double PADDED_RADIUS = MAIN_RADIUS + INPUT_RADIUS/3;

        // input handle 1
        inputHandle1.setLayoutX(Math.cos(Math.toRadians(210)) * PADDED_RADIUS);
        inputHandle1.setLayoutY(Math.sin(Math.toRadians(210)) * PADDED_RADIUS);
        inputHandle1.setFill(INPUT_COLOR);

        // input handle 2
        inputHandle2.setLayoutX(Math.cos(Math.toRadians(150)) * PADDED_RADIUS);
        inputHandle2.setLayoutY(Math.sin(Math.toRadians(150)) * PADDED_RADIUS);
        inputHandle2.setFill(INPUT_COLOR);

        // output handle
        outputHandle.setLayoutX(MAIN_RADIUS + OUTPUT_RADIUS/3);
        outputHandle.setLayoutY(0);
        outputHandle.setFill(OUTPUT_COLOR);

        // text
        Text text = new Text(getLabelBasedOnType());
        text.setLayoutX(-MAIN_RADIUS);
//        text.setLayoutY(-MAIN_RADIUS - 7); //extra estimated hard coded shift because of x-height
        text.setLayoutY(MAIN_RADIUS/4);
        text.setFont(FONT);
        text.setFill(Color.WHITE);
        text.setWrappingWidth(2*MAIN_RADIUS);
        text.setTextAlignment(TextAlignment.CENTER);
//        label.setStyle("-fx-background-color: red;"); //debugging purposes only

        this.getChildren().addAll(mainCircle,inputHandle1,inputHandle2,outputHandle,text);

        setupOutputHandlers();


    }

    /**
     * Checks against the type of arithmetic node and returns a string
     * @return string equivalent of arithmetic type
     */
    private String getLabelBasedOnType(){
        String label = null;
        switch (arithmeticNode.getType()){

            case ADD:
                label = "+";
                break;
            case SUBTRACT:
                label = "-";
                break;
            case MULTIPLY:
                label = "X";
                break;
            case DIVIDE:
                label = "/";
                break;
            case MODULO:
                label = "%";
                break;
        }
        return label;
    }
}
