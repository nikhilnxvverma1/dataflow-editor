package app.view;

import editor.util.Logger;
import javafx.scene.shape.Circle;
import model.component.ComponentNode;
import model.DataFlowNode;

import java.util.ArrayList;

public class ComponentNodeView extends DataFlowView {

    private ComponentNode componentNode;
    private ArrayList<Class> inputChannels;
    private ArrayList<Class> outputChannels;

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
        inputChannels = loadChannels(componentNode.getInputChannels());
        outputChannels = loadChannels(componentNode.getOutputChannels());
    }

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
