package app.controller;

import app.delegate.WorkspaceListener;
import app.view.ArithmeticNodeView;
import app.view.DataFlowView;
import app.view.DataFlowViewListener;
import editor.container.FunctionDefinitionStructure;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import model.*;

import java.util.List;

/**
 * Responds to all (delegated) canvas related events and managing the business workflow
 * for the selected function definition
 */
public class WorkspaceController implements DataFlowViewListener {

    /** Usually the main window controller will be the listener. This is a decoupled back reference */
    private WorkspaceListener workspaceListener;
    private SubScene canvas;


    public WorkspaceController(WorkspaceListener workspaceListener, SubScene canvas) {
        this.workspaceListener = workspaceListener;
        this.canvas = canvas;
    }

    void initialize(){

        // create a Camera to view the 3D Shapes
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.setFieldOfView(35);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(0);
        canvas.setCamera(camera);

        // get a list of definitions structure
        List<FunctionDefinitionStructure> structureList = workspaceListener.getAllFunctionDefinitionStructure();

        // for each structure
        for(FunctionDefinitionStructure structure : structureList){

            // create a view for each data flow node model in this definition
            createAndAddViewsForDataFlowNodes(structure);

            // TODO create a view for each data flow edge model in this definition
        }


    }

    private void createAndAddViewsForDataFlowNodes(FunctionDefinitionStructure structure){

        // loop through each data flow node
        for(DataFlowNode node : structure.functionDefinition.getDataFlowNodes()){

            //create a view for the node based on the specific data model
            DataFlowView nodeView = null;

            if(node.getClass()== ArithmeticNode.class){
                nodeView = new ArithmeticNodeView((ArithmeticNode)node,this);
            }else if(node.getClass()== ConditionalNode.class){

            }else if(node.getClass()== BooleanInputNode.class){

            }else if(node.getClass()== NumberInputNode.class){

            }else if(node.getClass()== FunctionNode.class){

            }

            // add this node view to the structure's group
            structure.group.getChildren().add(nodeView);
        }
    }

    //==================================================================================================================
    //  Events received from the parent controller
    //==================================================================================================================

    void functionDefinitionChanged(FunctionDefinitionStructure newSelection,FunctionDefinitionStructure oldSelection){

        // remove the group of the old selection, and add this selection
        if(oldSelection!=null){
            // do any transient de-allocation if needed
        }

        // set the root of the canvas to be that of new selection
        canvas.setRoot(newSelection.group);
    }

    //==================================================================================================================
    //  Events received from the data flow views
    //==================================================================================================================

}
