package app.controller;

import app.delegate.WorkspaceListener;
import app.view.ArithmeticNodeView;
import app.view.DataFlowView;
import app.view.DataFlowViewListener;
import editor.container.FunctionDefinitionStructure;
import editor.util.Logger;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import model.*;

import java.util.List;

/**
 * Responds to all (delegated) canvas related events and managing the business workflow
 * for the selected function definition
 */
public class WorkspaceController implements DataFlowViewListener {

    private static final double CLOSEST_CAMERA_Z = 10;
    private static final double FARTHEST_CAMERA_Z = 100;
    private static final double DEFAULT_CAMERA_Z = 40;

    /** Usually the main window controller will be the listener. This is a decoupled back reference */
    private WorkspaceListener workspaceListener;
    private SubScene canvas;


    public WorkspaceController(WorkspaceListener workspaceListener, SubScene canvas) {
        this.workspaceListener = workspaceListener;
        this.canvas = canvas;

        // create a Camera to view the 3D Shapes
        // this needs to be done before any callbacks fire off that may have a dependency on camera
        PerspectiveCamera camera = new PerspectiveCamera();
        camera.setNearClip(1);
        camera.setFarClip(1000);
        camera.setFieldOfView(35);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(DEFAULT_CAMERA_Z);
        canvas.setCamera(camera);
    }

    void initialize(){

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
        canvas.getCamera().setTranslateX(newSelection.cameraX);
        canvas.getCamera().setTranslateY(newSelection.cameraY);
        canvas.getCamera().setTranslateZ(newSelection.cameraZ);
    }

    void panCanvas(ScrollEvent scrollEvent){
        Logger.debug("Canvas scrolled(dx,dy): ("+scrollEvent.getDeltaX()+","+(scrollEvent.getDeltaY())+")");

        //compute the new position of the camera based on the delta amount scrolled
        double newX = canvas.getCamera().getTranslateX() - scrollEvent.getDeltaX();
        double newY = canvas.getCamera().getTranslateY() - scrollEvent.getDeltaY();
        canvas.getCamera().setTranslateX(newX);
        canvas.getCamera().setTranslateY(newY);

        // save the position in the current structure
        FunctionDefinitionStructure current = workspaceListener.getCurrentFunctionDefinitionStructure();
        current.cameraX = newX;
        current.cameraY = newY;
    }

    void zoomCanvas(ZoomEvent zoomEvent){
        Logger.debug("Zooming on canvas dz:"+zoomEvent.getZoomFactor());

        // get the current camera z
        double cameraZ = canvas.getCamera().getTranslateZ();

        // compute new camera position
        double newCameraZ = cameraZ;
        if(zoomEvent.getZoomFactor()>=1){ // zoom in
            if(cameraZ > CLOSEST_CAMERA_Z){  // don't go beyond threshold
                newCameraZ = cameraZ - 1;
            }
        }else{ // zoom out
            if(cameraZ < FARTHEST_CAMERA_Z){ // don't go beyond threshold
                newCameraZ = cameraZ + 1;
            }
        }

        // set the new camera position (z axis only)
        canvas.getCamera().setTranslateZ(newCameraZ);

        // save the Z axis in the current structure
        FunctionDefinitionStructure current = workspaceListener.getCurrentFunctionDefinitionStructure();
        current.cameraZ = newCameraZ;
    }

    //==================================================================================================================
    //  Events received from the data flow views
    //==================================================================================================================

}
