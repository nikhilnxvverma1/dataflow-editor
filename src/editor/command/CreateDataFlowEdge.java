package editor.command;

import app.view.DataFlowEdgeView;
import editor.container.FunctionDefinitionStructure;

public class CreateDataFlowEdge extends CanvasCommand{

    DataFlowEdgeView edgeView;
    FunctionDefinitionStructure structure;

    public CreateDataFlowEdge(DataFlowEdgeView edgeView, FunctionDefinitionStructure structure) {
        this.edgeView = edgeView;
        this.structure = structure;
    }

    @Override
    public void undo() {

        edgeView.disconnectFromNodes();

        // remove the view from the function's pane
        if(structure.pane.getChildren().contains(edgeView)){
            structure.pane.getChildren().remove(edgeView);
        }

        // remove any change listeners defined
        edgeView.removePositionalChangeListeners();

    }

    @Override
    public void redo() {

        edgeView.connectToNodes();

        // add the edge view to the function's pane
        structure.pane.getChildren().add(edgeView);
        edgeView.toBack();

        // add positional change listeners on connected nodes
        edgeView.addPositionalChangeListeners();

    }

    public DataFlowEdgeView getEdgeView() {
        return edgeView;
    }
}
