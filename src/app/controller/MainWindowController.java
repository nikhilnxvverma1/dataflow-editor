package app.controller;

import app.delegate.SidebarListener;
import app.delegate.WorkspaceListener;
import editor.container.FunctionDefinitionStructure;
import editor.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.FunctionDefinition;

import java.util.ArrayList;
import java.util.LinkedList;
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


        List<FunctionDefinition> test = new LinkedList<FunctionDefinition>();
        test.add(new FunctionDefinition(true, "main"));
        test.add(new FunctionDefinition(false, "first"));
        test.add(new FunctionDefinition(false, "second"));
        test.add(new FunctionDefinition(false, "third"));
        test.add(new FunctionDefinition(false, "nth method"));
        this.sidebarController.initialize(test);
    }

    //==================================================================================================================
    //  Sidebar callbacks
    //==================================================================================================================

    @Override
    public void selectionChangedTo(FunctionDefinitionStructure newSelection, FunctionDefinitionStructure oldSelection) {
        // TODO pass the message down to workspace controller
        Logger.debug("Function definition Selection changed");
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
