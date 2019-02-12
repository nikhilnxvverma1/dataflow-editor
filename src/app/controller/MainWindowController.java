package app.controller;

import app.delegate.SidebarListener;
import app.delegate.WorkspaceListener;
import editor.container.FunctionDefinitionStructure;
import editor.data.DummyData;
import editor.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;

import java.util.List;

/**
 * Main controller that receives all outer static UI events. This controller does not hold and state
 * and is not responsible for any business logic.
 */
public class MainWindowController implements SidebarListener, WorkspaceListener {

    //==================================================================================================================
    //  UI references
    //==================================================================================================================

    @FXML
    private AnchorPane rootContainer;
    @FXML
    private SubScene canvas;
    @FXML
    private ListView functionListView;

    //==================================================================================================================
    //  Descendant controllers and their initialization
    //==================================================================================================================

    private SidebarController sidebarController;
    private WorkspaceController workspaceController;

    public void initialize(){
        canvas.widthProperty().bind(rootContainer.widthProperty());
        canvas.heightProperty().bind(rootContainer.heightProperty());
        this.sidebarController = new SidebarController(this,functionListView);
        this.workspaceController = new WorkspaceController(this,canvas);

        //TODO file handling, create models

        this.sidebarController.initialize(DummyData.filledFunctionDefinitions(5,1,5));
        this.workspaceController.initialize();
    }

    //==================================================================================================================
    //  Sidebar callbacks
    //==================================================================================================================

    @Override
    public void selectionChangedTo(FunctionDefinitionStructure newSelection, FunctionDefinitionStructure oldSelection) {
        // pass the message down to workspace controller
        workspaceController.functionDefinitionChanged(newSelection,oldSelection);
        Logger.debug("Function definition Selection changed");
    }

    //==================================================================================================================
    //  Workspace callbacks
    //==================================================================================================================

    @Override
    public FunctionDefinitionStructure getCurrentFunctionDefinitionStructure(){
        return sidebarController.getCurrentFunctionDefinitionStructure();
    }

    @Override
    public List<FunctionDefinitionStructure> getAllFunctionDefinitionStructure() {
        return sidebarController.getFunctionStructureList();
    }

    //==================================================================================================================
    //  Event handlers (for static UI)
    //==================================================================================================================

    @FXML
    private void mouseClickOnCanvas(MouseEvent mouseEvent){
        System.out.println("Mouse click registered");
    }

    @FXML
    private void mousePressedOnCanvas(MouseEvent mouseEvent){
        Logger.debug("Mouse press registered");
        canvas.requestFocus();
    }

    @FXML
    private void mouseDraggedOnCanvas(MouseEvent mouseEvent){
        Logger.debug("Mouse drag registered");
    }

    @FXML
    private void mouseReleasedOnCanvas(MouseEvent mouseEvent){
        Logger.debug("Mouse release registered");
    }

    @FXML
    private void scrollCanvas(ScrollEvent scrollEvent){
        workspaceController.panCanvas(scrollEvent);
    }

    @FXML
    private void zoomCanvas(ZoomEvent zoomEvent){
        workspaceController.zoomCanvas(zoomEvent);
    }

    @FXML
    private void functionListEditStarted(ListView.EditEvent<String> e){
        Logger.debug("Function list edit started");
        sidebarController.functionListEditStarted(e);
    }

    @FXML
    private void functionListEditCommit(ListView.EditEvent<String> e){
        Logger.debug("Function list edit commit");
        sidebarController.functionListEditCommit(e);
    }

    @FXML
    private void functionListEditCancel(ListView.EditEvent<String> e){
        Logger.debug("Function list edit cancel");
        sidebarController.functionListEditCancel(e);
    }

    @FXML
    private void newFunctionDefinition(ActionEvent actionEvent){
        Logger.debug("new function definition");
        sidebarController.newFunctionDefinition(actionEvent);
    }

    @FXML
    private void deleteSelectedFunctionDefinition(KeyEvent keyEvent){
        if((keyEvent.getCode() == KeyCode.DELETE) ||
                (keyEvent.getCode() == KeyCode.BACK_SPACE)){
            Logger.debug("delete function definition");
            sidebarController.deleteSelectedFunctionDefinition();

        }

    }

}
