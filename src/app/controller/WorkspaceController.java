package app.controller;

import app.controller.util.NodeTool;
import app.controller.util.SelectionManager;
import app.delegate.WorkspaceListener;
import app.view.*;
import editor.command.CanvasCommand;
import editor.command.DeleteDataFlowNodes;
import editor.container.ConnectionPoint;
import editor.container.FunctionDefinitionStructure;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Responds to all (delegated) canvas related events and managing the business workflow
 * for the selected function definition
 */
public class WorkspaceController implements DataFlowViewListener {

    private static final double CLOSEST_CAMERA_Z = -1;
    public static final double DEFAULT_CAMERA_Z = -100;
    private static final double FARTHEST_CAMERA_Z = -10000;
    private static final double ZOOM_DELTA_Z = 50;
    private static final Paint HIGHLIGHT_FILL = new Color(1,1,1,0.1);
    private static final Paint HIGHLIGHT_OUTLINE = Color.WHITE;
    private static final Paint SELECTION_OUTLINE = Color.WHITE;
    private static final Paint SELECTION_FILL = new Color(1,1,1,0.0);

    /** Usually the main window controller will be the listener. This is a decoupled back reference */
    private WorkspaceListener workspaceListener;
    private SubScene canvas;
    private NodeTool tool;
    private SelectionManager selectionManager;

    WorkspaceController(WorkspaceListener workspaceListener, SubScene canvas) {
        this.workspaceListener = workspaceListener;
        this.canvas = canvas;
        this.tool = new NodeTool(this);
        this.selectionManager = new SelectionManager(this);

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

    /**
     * Gets the location from the mouse event after cancelling any camera pan
     * @param mouseEvent mouse event that occurred on the sub scene
     * @return transformed point that can be used in structure's group space
     */
    public Point2D transformedAfterPan(MouseEvent mouseEvent){
        FunctionDefinitionStructure structure = getCurrentStructure();
        double x = mouseEvent.getX() + structure.cameraX;
        double y = mouseEvent.getY() + structure.cameraY;
        return new Point2D(x,y);
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

            // also add this node view list to the structure for tracking later
            structure.nodeViewList.add(nodeView);
        }
    }

    /**
     * @return finds the index of the current function definition structure
     */
    private int getCurrentStructureIndex(){
        FunctionDefinitionStructure currentStructure = workspaceListener.getCurrentFunctionDefinitionStructure();
        List<FunctionDefinitionStructure> structureList = workspaceListener.getAllFunctionDefinitionStructure();
        return structureList.indexOf(currentStructure);
    }

    NodeTool getTool() {
        return tool;
    }

    //==================================================================================================================
    //  Events received from the parent controller
    //==================================================================================================================

    void functionDefinitionChanged(FunctionDefinitionStructure newSelection,FunctionDefinitionStructure oldSelection){

        // remove the group of the old selection, and add this selection
        if(oldSelection!=null){
            // do any transient de-allocation if needed
        }

        // clear the selection set for this new entry
        selectionManager.getSelectionSet().clear();

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
//        Logger.debug("Canvas scrolled(dx,dy): ("+scrollEvent.getDeltaX()+","+(scrollEvent.getDeltaY())+")");

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
            if(cameraZ + ZOOM_DELTA_Z < CLOSEST_CAMERA_Z){  // don't go beyond threshold
                newCameraZ = cameraZ + ZOOM_DELTA_Z;
            }
        }else{ // zoom out
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

    void deleteSelectedViews(){
        List<DataFlowView> currentSelection = selectionManager.cloneSelection();
        if(currentSelection.size()>0){
            DeleteDataFlowNodes deleteNodes = new DeleteDataFlowNodes(
                    currentSelection,
                    getCurrentStructure(),
                    getCurrentStructureIndex());
            workspaceListener.registerCommand(deleteNodes,true);
            selectionManager.getSelectionSet().clear();
        }
    }

    // Mouse Events

    void mouseMovedOnCanvas(MouseEvent mouseEvent){
        tool.mouseMoved(mouseEvent);
    }

    void mouseEnteredOnCanvas(MouseEvent mouseEvent){
        tool.togglePreview(true);
    }

    void mouseExitedOnCanvas(MouseEvent mouseEvent){
        tool.togglePreview(false);
    }

    void mousePressedOnCanvas(MouseEvent mouseEvent){
        selectionManager.mousePressedOnCanvas(mouseEvent);
    }

    void mouseDraggedOnCanvas(MouseEvent mouseEvent){
        selectionManager.mouseDraggedOnCanvas(mouseEvent);
    }

    void mouseReleasedOnCanvas(MouseEvent mouseEvent){
        selectionManager.mouseReleasedOnCanvas(mouseEvent);
    }

    void mouseClickOnCanvas(MouseEvent mouseEvent){
        tool.createNode(mouseEvent);
    }

    //==================================================================================================================
    //  Events received from the data flow views
    //==================================================================================================================

    @Override
    public void registerCommand(CanvasCommand command, boolean executeBeforeRegistering) {

        //set the current structure's index
        command.setFunctionDefinitionIndex(getCurrentStructureIndex(getCurrentStructureIndex()));
        workspaceListener.registerCommand(command,executeBeforeRegistering);
    }

    private int getCurrentStructureIndex(int index) {
        return index;
    }

    @Override
    public List<ConnectionPoint> getAvailableInputConnectionPoints(DataFlowView exclude){
        LinkedList<ConnectionPoint> connectionList = new LinkedList<>();

        // collect all input connection points in the current structure excluding the parameter
        for(DataFlowView node : getCurrentStructure().nodeViewList){
            if(node!=exclude){
                int totalInputChannels = node.totalInputChannels();
                for (int i = 0; i < totalInputChannels; i++) {

                    // check if this connection point is already consumed/occupied
                    boolean occupied = false;
                    for(DataFlowEdgeView edgeView : node.getIncomingEdges()){
                        if(edgeView.getEdge().getToInputIndex() == i){
                            occupied = true;
                            break;
                        }
                    }

                    // only add a connection point if it is not occupied by an edge
                    if(!occupied){
                        connectionList.add(new ConnectionPoint(node,i,node.getInputConnectorAt(i)));
                    }
                }
            }
        }
        return connectionList;
    }

    @Override
    public FunctionDefinitionStructure getCurrentStructure(){
        return workspaceListener.getCurrentFunctionDefinitionStructure();
    }

    @Override
    public boolean requestSoleSelection(DataFlowView sole) {
        ObservableList<DataFlowView> selectionSet = selectionManager.getSelectionSet();
        int size = selectionSet.size();
        if(size ==0 || !selectionSet.contains(sole)){
            selectionSet.clear();
            selectionSet.add(sole);
            return true;
        }
        return false;
    }

    @Override
    public void movedBy(double deltaX, double deltaY) {
        selectionManager.moveSelectionOutlineBy(deltaX,deltaY);
    }
}
