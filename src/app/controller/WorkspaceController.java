package app.controller;

import app.controller.util.NodeTool;
import app.controller.util.SelectionManager;
import app.controller.util.EditSpace;
import app.delegate.WorkspaceListener;
import app.view.*;
import editor.command.CanvasCommand;
import editor.command.DeleteDataFlowNodes;
import editor.container.ConnectionPoint;
import editor.container.FunctionDefinitionStructure;
import editor.util.Logger;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Responds to all (delegated) canvas related events and managing the business workflow
 * for the selected function definition
 */
public class WorkspaceController implements DataFlowViewListener {

    /** Usually the main window controller will be the listener. This is a decoupled back reference */
    private WorkspaceListener workspaceListener;
    private EditSpace editSpace;
    private NodeTool tool;
    private SelectionManager selectionManager;

    WorkspaceController(WorkspaceListener workspaceListener, AnchorPane parent) {
        this.workspaceListener = workspaceListener;
        this.tool = new NodeTool(this);
        this.selectionManager = new SelectionManager(this);

        setupEditSpace(parent);
    }

    private void setupEditSpace(AnchorPane parent){

        // create an edit space (which would hold the contents of the current structure)
        this.editSpace =  new EditSpace();

        // add to parent with constraints to top left side being 0
        parent.getChildren().add(editSpace);
        editSpace.toBack();
        AnchorPane.setTopAnchor(this.editSpace,0.0);
        AnchorPane.setLeftAnchor(this.editSpace,MainWindowController.SIDEBAR_WIDTH);
        AnchorPane.setRightAnchor(this.editSpace,0.0);
        AnchorPane.setBottomAnchor(this.editSpace,0.0);

        // setup event handling for all mouse events
        editSpace.getContentPane().setOnMouseMoved(this::mouseMovedOnCanvas);
        editSpace.getContentPane().setOnMouseEntered(this::mouseEnteredOnCanvas);
        editSpace.getContentPane().setOnMouseExited(this::mouseExitedOnCanvas);
        editSpace.getContentPane().setOnMousePressed(this::mousePressedOnCanvas);
        editSpace.getContentPane().setOnMouseDragged(this::mouseDraggedOnCanvas);
        editSpace.getContentPane().setOnMouseReleased(this::mouseReleasedOnCanvas);
        editSpace.getContentPane().setOnMouseClicked(this::mouseClickOnCanvas);
    }

    /**
     * Gets the location from the mouse event after cancelling any camera pan.
     * This is just a middle man method invoked for getting transformed points.
     * It doesn't do anything now because scroll pane natively takes care of it.
     * @param mouseEvent mouse event that occurred on the sub scene
     * @return transformed point that can be used in structure's group space
     */
    public Point2D transformedAfterPan(MouseEvent mouseEvent){
        FunctionDefinitionStructure structure = getCurrentStructure();
//        double x = mouseEvent.getX() + structure.cameraX;
//        double y = mouseEvent.getY() + structure.cameraY;
        double x = mouseEvent.getX();
        double y = mouseEvent.getY();
//        Logger.debug("(x,y)"+x+","+y);
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

    EditSpace getEditSpace() {
        return editSpace;
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

        editSpace.setFunctionDefinitionStructure(newSelection);

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
        if(!tool.inCreationMode()){
            selectionManager.mousePressedOnCanvas(mouseEvent);
            mouseEvent.consume();
        }
    }

    void mouseDraggedOnCanvas(MouseEvent mouseEvent){
        if(!tool.inCreationMode()){
            selectionManager.mouseDraggedOnCanvas(mouseEvent);
            mouseEvent.consume();
        }
    }

    void mouseReleasedOnCanvas(MouseEvent mouseEvent){
        if(!tool.inCreationMode()){
            selectionManager.mouseReleasedOnCanvas(mouseEvent);
            mouseEvent.consume();
        }
    }

    void mouseClickOnCanvas(MouseEvent mouseEvent){
        editSpace.getContentPane().requestFocus();
        DataFlowView newNodeCreated = tool.createNode(mouseEvent);
        if(newNodeCreated!=null){
            newNodeCreated.postViewCreation();
            selectionManager.getSelectionSet().clear();
            selectionManager.getSelectionSet().add(newNodeCreated);
        }
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

    @Override
    public List<DataFlowView> currentSelection() {
        return selectionManager.cloneSelection();
    }
}
