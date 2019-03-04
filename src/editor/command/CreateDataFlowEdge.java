package editor.command;

import app.view.DataFlowEdgeView;
import editor.container.FunctionDefinitionStructure;
import model.DataFlowNode;

public class CreateDataFlowEdge extends CanvasCommand{

    DataFlowEdgeView edgeView;
    FunctionDefinitionStructure structure;

    public CreateDataFlowEdge(DataFlowEdgeView edgeView, FunctionDefinitionStructure structure) {
        this.edgeView = edgeView;
        this.structure = structure;
    }

    @Override
    public void undo() {

        // remove the edge model from the edge list of associated nodes
        DataFlowNode fromNode = edgeView.getEdge().getFrom();
        fromNode.getOutgoingEdges().remove(edgeView.getEdge());

        DataFlowNode toNode = edgeView.getEdge().getTo();
        toNode.getIncomingEdges().remove(edgeView.getEdge());

        // remove the view from the function's group
        if(structure.group.getChildren().contains(edgeView)){
            structure.group.getChildren().remove(edgeView);
        }

        // remove any change listeners defined
        edgeView.removePositionalChangeListeners();

    }

    @Override
    public void redo() {

        // add the edge model to the edge list of associated nodes
        DataFlowNode fromNode = edgeView.getEdge().getFrom();
        fromNode.getOutgoingEdges().add(edgeView.getEdge());

        DataFlowNode toNode = edgeView.getEdge().getTo();
        toNode.getIncomingEdges().add(edgeView.getEdge());

        // add the edge view to the function's group
        structure.group.getChildren().add(edgeView);

        // add positional change listeners on connected nodes
        edgeView.addPositionalChangeListeners();

    }
}
