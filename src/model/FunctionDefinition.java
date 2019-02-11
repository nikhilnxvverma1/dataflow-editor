package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Function definition holds the directed graph that makes up the dataflow diagram.
 * There can be multiple function definitions which can be used switched across.
 */
public class FunctionDefinition {

    private String name;
    private boolean isMain;
    List<DataFlowNode> dataFlowNodes = new LinkedList<>();
    List<DataFlowNode> startingDataFlowNodes = new LinkedList<>();
    List<DataFlowNode> endingDataFlowNodes = new LinkedList<>();

    public FunctionDefinition(boolean isMain,String name) {
        this.isMain = isMain;
        if(this.isMain){
            this.name = "main";
        }else{
            this.name = name;
        }
    }

    public String getName() {
        if(isMain){
            return "main";
        }
        return name;
    }

    public void setName(String name) {
        if(!isMain){
            this.name = name;
        }
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
        if (this.isMain) {
            this.name = "main";
        }
    }

    public List<DataFlowNode> getDataFlowNodes() {
        return dataFlowNodes;
    }

    public void setDataFlowNodes(List<DataFlowNode> dataFlowNodes) {
        this.dataFlowNodes = dataFlowNodes;
    }

    public List<DataFlowNode> getStartingDataFlowNodes() {
        return startingDataFlowNodes;
    }

    public void setStartingDataFlowNodes(List<DataFlowNode> startingDataFlowNodes) {
        this.startingDataFlowNodes = startingDataFlowNodes;
    }

    public List<DataFlowNode> getEndingDataFlowNodes() {
        return endingDataFlowNodes;
    }

    public void setEndingDataFlowNodes(List<DataFlowNode> endingDataFlowNodes) {
        this.endingDataFlowNodes = endingDataFlowNodes;
    }
}
