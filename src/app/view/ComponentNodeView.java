package app.view;

import javafx.scene.shape.Circle;
import model.ComponentNode;
import model.DataFlowNode;

import java.util.ArrayList;

public class ComponentNodeView extends DataFlowView {

    private ComponentNode componentNode;
    private ArrayList<Class> inputChannels = new ArrayList<>();
    private ArrayList<Class> outputChannels = new ArrayList<>();

    private ArrayList<Circle> inputConnectors = new ArrayList<>();
    private ArrayList<Circle> outputConnectors = new ArrayList<>();

    public ComponentNodeView(ComponentNode componentNode, DataFlowViewListener dataFlowViewListener) {
        super(dataFlowViewListener);
        this.componentNode = componentNode;
        initialize();
    }

    private void initialize(){
        buildChannelsList();
    }

    private void buildChannelsList(){

    }

    @Override
    public DataFlowNode getDataFlowNode() {
        return componentNode;
    }

    @Override
    public int totalInputChannels() {
        return inputChannels.size();
    }

    @Override
    public int totalOutputChannels() {
        return outputChannels.size();
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
        return inputChannels.get(index);
    }

    @Override
    public Class getTypeForOutput(int index) {
        return inputChannels.get(index);
    }
}
