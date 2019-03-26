package model;

import java.util.ArrayList;

public class ComponentNode extends DataFlowNode{
    private ArrayList<String> inputChannels = new ArrayList<>();
    private ArrayList<String> outputChannels = new ArrayList<>();
    private ArrayList<String> inputChannelNames = new ArrayList<>();
    private ArrayList<String> outputChannelNames = new ArrayList<>();
    private double red = 0.2;
    private double green = 0.2;
    private double blue = 0.2;


    public ComponentNode(double x, double y) {
        super(x, y);
    }

    public ArrayList<String> getInputChannels() {
        return inputChannels;
    }

    public void setInputChannels(ArrayList<String> inputChannels) {
        this.inputChannels = inputChannels;
    }

    public ArrayList<String> getOutputChannels() {
        return outputChannels;
    }

    public void setOutputChannels(ArrayList<String> outputChannels) {
        this.outputChannels = outputChannels;
    }

    public ArrayList<String> getInputChannelNames() {
        return inputChannelNames;
    }

    public void setInputChannelNames(ArrayList<String> inputChannelNames) {
        this.inputChannelNames = inputChannelNames;
    }

    public ArrayList<String> getOutputChannelNames() {
        return outputChannelNames;
    }

    public void setOutputChannelNames(ArrayList<String> outputChannelNames) {
        this.outputChannelNames = outputChannelNames;
    }

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }
}
