package editor.command;

import app.view.DataFlowEdgeView;
import app.view.DataFlowView;
import editor.container.FunctionDefinitionStructure;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import java.util.List;

public class DeleteDataFlowNodes implements Command{
    private List<DataFlowView> nodesToDelete;
    private FunctionDefinitionStructure structure;
    private int structureIndex;

    public DeleteDataFlowNodes(List<DataFlowView> nodesToDelete, FunctionDefinitionStructure structure, int structureIndex) {
        this.nodesToDelete = nodesToDelete;
        this.structure = structure;
        this.structureIndex = structureIndex;
    }

    @Override
    public void undo() {
        Pane parent = structure.pane;
        // for each node
        for(DataFlowView each : nodesToDelete){

            // add node view and model
            structure.nodeViewList.add(each);
            parent.getChildren().add(each);
            structure.functionDefinition.getDataFlowNodes().add(each.getDataFlowNode());

            // add associated incoming edge's views and models also (by taking reference from the other side)
            for(DataFlowEdgeView edgeView: each.getIncomingEdges()){

                // add this edge from the view
                parent.getChildren().add(edgeView);
                edgeView.toBack();

                // attach this edge from the list (both view and model) from the other side
                edgeView.getFromView().getOutgoingEdges().add(edgeView);
                edgeView.getFromView().getDataFlowNode().getOutgoingEdges().add(edgeView.getEdge());
            }

            // add associated outgoing edge's views and models also
            for(DataFlowEdgeView edgeView: each.getOutgoingEdges()){

                // add this edge from the view
                parent.getChildren().add(edgeView);
                edgeView.toBack();

                // attach this edge from the list (both view and model) from the other side
                edgeView.getToView().getIncomingEdges().add(edgeView);
                edgeView.getToView().getDataFlowNode().getIncomingEdges().add(edgeView.getEdge());
            }
        }

    }

    @Override
    public void redo() {
        Pane parent = structure.pane;
        // for each node remove view, model and
        for(DataFlowView each : nodesToDelete){

            // remove node view and model
            parent.getChildren().remove(each);
            structure.functionDefinition.getDataFlowNodes().remove(each.getDataFlowNode());
            structure.nodeViewList.remove(each);

            // remove associated incoming edge's views and models also (but only from the other side)
            for(DataFlowEdgeView edgeView: each.getIncomingEdges()){

                // remove this edge from the view
                parent.getChildren().remove(edgeView);

                // detach this edge from the list (both view and model) from the other side
                edgeView.getFromView().getOutgoingEdges().remove(edgeView);
                edgeView.getFromView().getDataFlowNode().getOutgoingEdges().remove(edgeView.getEdge());
            }

            // remove associated outgoing edge's views and models also
            for(DataFlowEdgeView edgeView: each.getOutgoingEdges()){

                // remove this edge from the view
                parent.getChildren().remove(edgeView);

                // detach this edge from the list (both view and model) from the other side
                edgeView.getToView().getIncomingEdges().remove(edgeView);
                edgeView.getToView().getDataFlowNode().getIncomingEdges().remove(edgeView.getEdge());
            }
        }
    }

    @Override
    public int getFunctionDefinitionIndex(boolean undoOrRedo) {
        return structureIndex;
    }
}
