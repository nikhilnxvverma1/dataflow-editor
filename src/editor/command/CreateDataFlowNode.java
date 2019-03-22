package editor.command;

import app.view.DataFlowView;
import editor.container.FunctionDefinitionStructure;

public class CreateDataFlowNode extends CanvasCommand{

    private DataFlowView dataFlowView;
    private FunctionDefinitionStructure structure;

    public CreateDataFlowNode(DataFlowView dataFlowView, FunctionDefinitionStructure structure) {
        this.dataFlowView = dataFlowView;
        this.structure = structure;
    }

    @Override
    public void undo() {

        // remove the node from the model
        structure.functionDefinition.getDataFlowNodes().remove(dataFlowView.getDataFlowNode());

        // remove the view from the FunctionDefinitionStructure pane
        structure.pane.getChildren().remove(dataFlowView);

        // remove from the structure's view list
        structure.nodeViewList.remove(dataFlowView);
    }

    @Override
    public void redo() {

        //add the model
        structure.functionDefinition.getDataFlowNodes().add(dataFlowView.getDataFlowNode());

        // add the view in the FunctionDefinitionStructure pane
        if (!structure.pane.getChildren().contains(dataFlowView)){
            structure.pane.getChildren().add(dataFlowView);
        }

        // add to the structure's view list
        structure.nodeViewList.add(dataFlowView);
    }
}
