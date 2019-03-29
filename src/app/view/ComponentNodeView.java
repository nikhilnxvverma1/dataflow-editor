package app.view;

import editor.container.ComponentTemplate;
import editor.util.Logger;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.component.ComponentNode;
import model.DataFlowNode;

import java.util.ArrayList;
import java.util.List;

public class ComponentNodeView extends DataFlowView {

    private static final double GAP_INPUT_OUTPUT = 20;
    private static final double HEADER_HEIGHT = 30;
    private static final double HEADER_BOTTOM_MARGIN = 30;
    private static final double BOTTOM_INSET = 30;
    private static final double LABEL_HEIGHT = 20;
    private static final double CHANNEL_GAP = 10;
    private static final double CHANNEL_HEIGHT = 20;
    private static final Font FONT = new Font(16);


    private ComponentTemplate componentTemplate;
    private ComponentNode componentNode;
//    private ArrayList<Class> inputChannels;
//    private ArrayList<Class> outputChannels;

    private ArrayList<Circle> inputConnectors = new ArrayList<>();
    private ArrayList<Circle> outputConnectors = new ArrayList<>();

    public ComponentNodeView(ComponentTemplate componentTemplate, DataFlowViewListener dataFlowViewListener) {
        super(dataFlowViewListener);
        this.componentTemplate = componentTemplate;
        this.componentNode = componentTemplate.getComponentNode();
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
        Label header = new Label(componentNode.getName());
        header.setPrefSize(width,LABEL_HEIGHT);
        header.setTranslateX(-width/2);
        header.setTranslateY(-height/2);
        header.setAlignment(Pos.CENTER);

        // divider
        Line divider = new Line(-width/2,LABEL_HEIGHT,width/2,LABEL_HEIGHT);
        divider.setTranslateX(0);
        divider.setTranslateY(-height/2);

        double inputCount = componentNode.getInputChannels().size();
        double inputSpacing = (height-HEADER_HEIGHT) / (inputCount+1);
        double longestInput = longestLength(componentNode.getInputChannelNames());

        // input labels and connectors
        ArrayList<Label> inputLabels = new ArrayList<>();
        int i=0;
        for(String channelName : componentNode.getInputChannelNames()){

            // position
            double x = 0 - width/2;
            double y = HEADER_HEIGHT + inputSpacing * (i+1) - height/2;

            // label
            Label inputLabel = new Label(channelName);
            inputLabel.setAlignment(Pos.CENTER_LEFT);
            inputLabel.setPrefSize(longestInput*FONT.getSize(),CHANNEL_HEIGHT);
            inputLabel.setFont(FONT);
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
        double outputCount = componentNode.getOutputChannels().size();
        double outputSpacing = (height-HEADER_HEIGHT) / (outputCount+1);
        double longestOutput = longestLength(componentNode.getInputChannelNames());
        ArrayList<Label> outputLabels = new ArrayList<>();
        i=0;
        for(String channelName : componentNode.getOutputChannelNames()){

            // position
            double x = width/2;
            double y = HEADER_HEIGHT + outputSpacing * (i+1) - height/2;

            // label
            Label outputLabel = new Label(channelName);
            outputLabel.setPrefSize(longestOutput*FONT.getSize(),CHANNEL_HEIGHT);
            outputLabel.setAlignment(Pos.CENTER_RIGHT);
            outputLabel.setFont(FONT);
            outputLabel.setTranslateX(-(longestOutput*FONT.getSize()+OUTPUT_RADIUS));
            outputLabel.setTranslateY(-CHANNEL_HEIGHT/2);
            outputLabel.setLayoutX(x);
            outputLabel.setLayoutY(y);
            outputLabels.add(outputLabel);

            // connector
            Circle connector = new Circle(OUTPUT_RADIUS);
            connector.setLayoutX(x);
            connector.setLayoutY(y);
            outputConnectors.add(connector);
            i++;
        }

        applyColor(rect,divider,header,inputLabels,outputLabels);
        this.getChildren().addAll(rect,header,divider);
        this.getChildren().addAll(inputLabels);
        this.getChildren().addAll(outputLabels);
        this.getChildren().addAll(inputConnectors);
        this.getChildren().addAll(outputConnectors);

        this.setupOutputHandlers();

    }

    private void applyColor(Rectangle rect, Line divider , Label header, List<Label> inputLabels, List<Label> outputLabels){
        Color backgroundColor = new Color(componentNode.getRedBg(),componentNode.getGreenBg(),componentNode.getBlueBg(),1);
        Color foregroundColor = new Color(componentNode.getRedFg(),componentNode.getGreenFg(),componentNode.getBlueFg(),1);

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

        // fill all connectors with foreground color
//        for(Circle circle : inputConnectors){
//            circle.setFill(foregroundColor);
//        }
//        for(Circle circle : outputConnectors){
//            circle.setFill(foregroundColor);
//        }

    }

    private double computeWidth(){
        int headerLength = componentNode.getName().length();
        int biggestInputLength = longestLength(componentNode.getInputChannelNames());
        int biggestOutputLength = longestLength(componentNode.getOutputChannelNames());

        double size = FONT.getSize()/2;
        double sum =
                biggestInputLength* size +
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
        double maxVerticalChannels = 0;
        if(componentNode.getInputChannels().size()>componentNode.getOutputChannels().size()){
            maxVerticalChannels = componentNode.getInputChannels().size();
        }else{
            maxVerticalChannels = componentNode.getOutputChannels().size();
        }

        return headerMarginInset + maxVerticalChannels*(LABEL_HEIGHT+ CHANNEL_GAP);
    }

    private String biggestWord(List<String> words){
        String biggest = null;
        for(String word : words){
            if(biggest == null || word.length()> biggest.length()){
                biggest = word;
            }
        }
        return biggest;
    }

    private int longestLength (List<String> words){
        String biggest = biggestWord(words);
        return biggest == null? 0 : biggest.length();
    }

//    /**
//     * Shortcut setter for channel types by the component template itself
//     * @param inputChannelsTypes array list of class per each input channel
//     * @param outputChannelsTypes array list of class per each output channel
//     */
//    public void setChannelTypes(ArrayList<Class> inputChannelsTypes,ArrayList<Class> outputChannelsTypes){
//        inputChannels = inputChannelsTypes;
//        outputChannels = outputChannelsTypes;
//    }

//    private void buildChannelsList(){
//        inputChannels = loadChannels(componentNode.getInputChannels());
//        outputChannels = loadChannels(componentNode.getOutputChannels());
//    }

    /**
     * Read off the list of channels and loads them
     * @param channels list of strings holding full path to class names
     * @return a list of class corresponding to the channels
     */
    private ArrayList<Class> loadChannels(ArrayList<String> channels){
        ArrayList<Class> loadedChannels = new ArrayList<Class>(channels.size());
        ClassLoader classLoader = ComponentNodeView.class.getClassLoader();
        for (int i = 0; i < channels.size(); i++) {
            try {
                Class<?> aClass = classLoader.loadClass(channels.get(i));
                loadedChannels.set(i,aClass);
            }catch (ClassNotFoundException e){
                Logger.error("Cant find channel class(input or output) for "+ componentNode.getName() +" at index"+i);
            }
        }
        return loadedChannels;
    }

    @Override
    public DataFlowNode getDataFlowNode() {
        return componentNode;
    }

    @Override
    public int totalInputChannels() {
        return componentTemplate.getInputTypes().size();
    }

    @Override
    public int totalOutputChannels() {
        return componentTemplate.getOutputTypes().size();
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
        return componentTemplate.getInputTypes().get(index);
    }

    @Override
    public Class getTypeForOutput(int index) {
        return componentTemplate.getOutputTypes().get(index);
    }
}
