package app.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.DataFlowNode;
import model.module.FormatTextNode;

import java.util.ArrayList;
import java.util.List;

public class FormatTextView extends DataFlowView{

    private static final double GAP_INPUT_OUTPUT = 20;
    private static final double HEADER_HEIGHT = 30;
    private static final double HEADER_BOTTOM_MARGIN = 30;
    private static final double BOTTOM_INSET = 30;
    private static final double LABEL_HEIGHT = 20;
    private static final double CHANNEL_GAP = 10;
    private static final double CHANNEL_HEIGHT = 20;
    private static final Font FONT = new Font(16);
    private static final String HEADER_TEXT = "Format Text";
    private static final String OUTPUT_TEXT = "Output";
    private static final int ARGUMENT_WIDTH = 20;

    private FormatTextNode formatTextNode;

    private ArrayList<Circle> inputConnectors = new ArrayList<>();
    private ArrayList<Circle> outputConnectors = new ArrayList<>();

    public FormatTextView(FormatTextNode formatTextNode, DataFlowViewListener dataFlowViewListener) {
        super(dataFlowViewListener);
        this.formatTextNode = formatTextNode;
        initialize();
    }

    private void initialize(){
        double width = computeWidth();
        double height = computeHeight();
        // main rect
        Rectangle rect = new Rectangle(0,0,width,height);
        rect.setTranslateX(-width/2);
        rect.setTranslateY(-height/2);

        // header
        Label header = new Label(HEADER_TEXT);
        header.setPrefSize(width,LABEL_HEIGHT);
        header.setTranslateX(-width/2);
        header.setTranslateY(-height/2);
        header.setAlignment(Pos.CENTER);

        // divider
        Line divider = new Line(-width/2,LABEL_HEIGHT,width/2,LABEL_HEIGHT);
        divider.setTranslateX(0);
        divider.setTranslateY(-height/2);

        double inputCount = formatTextNode.getArguments().size();
        double inputSpacing = (height-HEADER_HEIGHT) / (inputCount+1);

        // input labels and connectors
        ArrayList<Label> inputLabels = new ArrayList<>();
        int i=0;
        for(Object arg : formatTextNode.getArguments()){
            String channelName = arg.toString();

            // position
            double x = 0 - width/2;
            double y = HEADER_HEIGHT + inputSpacing * (i+1) - height/2;

            // label
            Label inputLabel = new Label(channelName);
            inputLabel.setAlignment(Pos.CENTER_LEFT);
            inputLabel.setFont(FONT);
            inputLabel.setPrefSize(width,CHANNEL_HEIGHT);
            inputLabel.setTranslateX(INPUT_RADIUS);
            inputLabel.setTranslateY(-CHANNEL_HEIGHT/2);
            inputLabel.setLayoutX(x);
            inputLabel.setLayoutY(y);
            inputLabels.add(inputLabel);

            // connector
            Circle connector = new Circle(INPUT_RADIUS);
            connector.setLayoutX(x);
            connector.setLayoutY(y);
            inputConnectors.add(connector);
            i++;
        }

        // output labels and connectors
        double outputCount = 1;
        double outputSpacing = (height-HEADER_HEIGHT) / (outputCount+1);
        ArrayList<Label> outputLabels = new ArrayList<>();

        // position
        double x = width/2;
        double y = HEADER_HEIGHT + outputSpacing * (i+1) - height/2;

        // label
        Label outputLabel = new Label(OUTPUT_TEXT);
        outputLabel.setAlignment(Pos.CENTER_RIGHT);
        outputLabel.setFont(FONT);
        outputLabel.setPrefSize(width,CHANNEL_HEIGHT);
        outputLabel.setTranslateX(-(width+OUTPUT_RADIUS));
        outputLabel.setTranslateY(-CHANNEL_HEIGHT/2);
        outputLabel.setLayoutX(x);
        outputLabel.setLayoutY(y);
        outputLabels.add(outputLabel);

        // connector
        Circle connector = new Circle(OUTPUT_RADIUS);
        connector.setLayoutX(x);
        connector.setLayoutY(y);
        outputConnectors.add(connector);


        applyColor(rect,divider,header,inputLabels,outputLabels);
        this.getChildren().addAll(rect,header,divider);
        this.getChildren().addAll(inputLabels);
        this.getChildren().addAll(outputLabels);
        this.getChildren().addAll(inputConnectors);
        this.getChildren().addAll(outputConnectors);

        this.setupOutputHandlers();

    }

    private void applyColor(Rectangle rect, Line divider , Label header, List<Label> inputLabels, List<Label> outputLabels){
        Color backgroundColor = new Color(0.8,0.3,0.5,1);
        Color foregroundColor = new Color(1,1,1,1);

        // set fill colors
        rect.setFill(backgroundColor);
        rect.setStroke(foregroundColor);
        header.setTextFill(foregroundColor);
        divider.setStroke(foregroundColor);

        // fill all input labels
        for(Label l: inputLabels){
            l.setTextFill(foregroundColor);
        }

        // fill all output labels
        for(Label l: outputLabels){
            l.setTextFill(foregroundColor);
        }

    }

    private double computeWidth(){
        int headerLength = HEADER_TEXT.length();
        int biggestOutputLength = OUTPUT_TEXT.length();

        double size = FONT.getSize()/2;
        double sum =
                ARGUMENT_WIDTH * size +
                        GAP_INPUT_OUTPUT +
                        biggestOutputLength* size +
                        INPUT_RADIUS +
                        OUTPUT_RADIUS;
        if(sum<headerLength){
            return headerLength + GAP_INPUT_OUTPUT;
        }else{
            return sum;
        }
    }

    private double computeHeight(){
        double headerMarginInset = HEADER_HEIGHT + HEADER_BOTTOM_MARGIN + BOTTOM_INSET;
        double maxVerticalChannels = 1+ formatTextNode.getArguments().size();
        return headerMarginInset + maxVerticalChannels*(LABEL_HEIGHT+ CHANNEL_GAP);
    }

    @Override
    public DataFlowNode getDataFlowNode() {
        return formatTextNode;
    }

    @Override
    public int totalInputChannels() {
        return formatTextNode.getArguments().size();
    }

    @Override
    public int totalOutputChannels() {
        return 1;
    }

    @Override
    public Circle getInputConnectorAt(int index) {
        return inputConnectors.get(index);
    }

    @Override
    public Circle getOutputConnectorAt(int index) {
        return outputConnectors.get(index);
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
