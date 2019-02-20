package app.controller;

import app.delegate.WorkspaceListener;
import app.view.*;
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

    private static final double CLOSEST_CAMERA_Z = -1;
    public static final double DEFAULT_CAMERA_Z = -100;
    private static final double FARTHEST_CAMERA_Z = -10000;
    public static final double ZOOM_DELTA_Z = 50;

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
                nodeView = new ConditionalNodeView((ConditionalNode)node,this);
            }else if(node.getClass()== BooleanInputNode.class){
                nodeView = new BooleanNodeView((BooleanInputNode)node,this);
            }else if(node.getClass()== NumberInputNode.class){
                nodeView = new NumberInputView((NumberInputNode)node,this);
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

    /**
     * Responsible for panning the canvas by a scroll event and subsequently saving that information in the current
     * function structure
     * @param scrollEvent the event that occurred on canvas that initiated this event
     */
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

    /**
     * Responsible for zooming the canvas (withing limits) by a zoom event and subsequently saving that
     * information in the current function structure
     * @param zoomEvent the event that occurred on canvas that initiated this event
     */
    void zoomCanvas(ZoomEvent zoomEvent){


        // get the current camera z
        double cameraZ = canvas.getCamera().getTranslateZ();

        // compute new camera position
        double newCameraZ = cameraZ;
        if(zoomEvent.getZoomFactor()>=1){ // zoom in
            Logger.debug("Zooming IN (factor):"+zoomEvent.getZoomFactor());
            if(cameraZ + ZOOM_DELTA_Z < CLOSEST_CAMERA_Z){  // don't go beyond threshold
                newCameraZ = cameraZ + ZOOM_DELTA_Z;
            }
        }else{ // zoom out
            Logger.debug("Zooming OUT (factor):"+zoomEvent.getZoomFactor());
            if(cameraZ - ZOOM_DELTA_Z > FARTHEST_CAMERA_Z){ // don't go beyond threshold
                newCameraZ = cameraZ - ZOOM_DELTA_Z;
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
