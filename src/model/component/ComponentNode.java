package model.component;

import model.DataFlowNode;

import java.util.ArrayList;

public class ComponentNode extends DataFlowNode {
    private String name;
    private ArrayList<String> inputChannels = new ArrayList<>();
    private ArrayList<String> outputChannels = new ArrayList<>();
    private ArrayList<String> inputChannelNames = new ArrayList<>();
    private ArrayList<String> outputChannelNames = new ArrayList<>();
    private double redBg = 0.2;
    private double greenBg = 0.2;
    private double blueBg = 0.2;
    private double redFg = 1;
    private double greenFg = 1;
    private double blueFg = 1;

    public ComponentNode(double x, double y) {
        super(x, y);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public double getRedBg() {
        return redBg;
    }

    public void setRedBg(double redBg) {
        this.redBg = redBg;
    }

    public double getGreenBg() {
        return greenBg;
    }

    public void setGreenBg(double greenBg) {
        this.greenBg = greenBg;
    }

    public double getBlueBg() {
        return blueBg;
    }

    public void setBlueBg(double blueBg) {
        this.blueBg = blueBg;
    }

    public double getRedFg() {
        return redFg;
    }

    public void setRedFg(double redFg) {
        this.redFg = redFg;
    }

    public double getGreenFg() {
        return greenFg;
    }

    public void setGreenFg(double greenFg) {
        this.greenFg = greenFg;
    }

    public double getBlueFg() {
        return blueFg;
    }

    public void setBlueFg(double blueFg) {
        this.blueFg = blueFg;
    }
}
