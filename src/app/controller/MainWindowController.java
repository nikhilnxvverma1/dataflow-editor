package app.controller;

import app.controller.util.NodeTool;
import app.delegate.SidebarListener;
import app.delegate.WorkspaceListener;
import editor.command.Command;
import editor.container.ComponentTemplate;
import editor.container.FunctionDefinitionStructure;
import editor.data.DummyData;
import editor.util.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import model.Root;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Main controller that receives all outer static UI events. This controller does not hold and state
 * and is not responsible for any business logic.
 */
public class MainWindowController implements SidebarListener, WorkspaceListener {

    public static final double SIDEBAR_WIDTH = 300;

    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    //==================================================================================================================
    //  UI references
    //==================================================================================================================

    @FXML
    private AnchorPane rootContainer;
    @FXML
    private VBox sidebarContainer;
//    @FXML
//    private SubScene canvas;
    @FXML
    private ListView<FunctionDefinitionStructure> functionListView;

    //==================================================================================================================
    //  Descendant controllers and their initialization
    //==================================================================================================================

    private SidebarController sidebarController;
    private WorkspaceController workspaceController;

    private Root root;

    public void initialize(){
        this.sidebarContainer.setPrefWidth(SIDEBAR_WIDTH);
        this.sidebarController = new SidebarController(this,functionListView);
        this.workspaceController = new WorkspaceController(this,rootContainer);

        ArrayList<ComponentTemplate> componentTemplates = ComponentTemplate.loadDefaultLibrary();
        System.out.println(componentTemplates);

//        DummyData.filledFunctionDefinitions(5,1,5)
        root = getEmptyModel();
        this.sidebarController.initialize(root.getFunctionDefinitionList());
        this.workspaceController.initialize();
    }

    private void undo(){
        try{
            Command lastCommand = undoStack.pop();
            lastCommand.undo();
            functionListView.getSelectionModel().select(lastCommand.getFunctionDefinitionIndex(true));
            redoStack.push(lastCommand);
        }catch(EmptyStackException e){
            Logger.info("Undo stack is empty");
        }
    }

    private void redo(){
        try{
            Command revertedCommand = redoStack.pop();
            revertedCommand.redo();
            functionListView.getSelectionModel().select(revertedCommand.getFunctionDefinitionIndex(false));
            undoStack.push(revertedCommand);
        }catch(EmptyStackException e){
            Logger.info("Redo stack is empty");
        }
    }

    private Root getEmptyModel(){
        Root emptyModel = new Root();
        emptyModel.setFunctionDefinitionList(DummyData.emptyFunctionDefinitions(1));
        return emptyModel;
    }

    //==================================================================================================================
    //  Common children controller callbacks
    //==================================================================================================================

    @Override
    public void registerCommand(Command command, boolean executeBeforeRegistering){
        redoStack.removeAllElements();
        if(executeBeforeRegistering){
            command.redo();
        }
        undoStack.push(command);
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


    // Anchor pane event(s)

    @FXML
    private void keyPressedOnAnchorPane(KeyEvent keyEvent){

        if(keyEvent.isMetaDown() && keyEvent.getCode() == KeyCode.Z){
            if(keyEvent.isShiftDown()){
                this.redo();
            }else{
                this.undo();
            }
        } else if (keyEvent.getCode() == KeyCode.ESCAPE) {
            workspaceController.getTool().clear();
        } else if (keyEvent.getCode() == KeyCode.DELETE || keyEvent.getCode() == KeyCode.BACK_SPACE){
            workspaceController.deleteSelectedViews();
        }
    }

    // Function List View events

    @FXML
    private void functionListEditStarted(ListView.EditEvent<FunctionDefinitionStructure> e){
        Logger.debug("Function list edit started");
        sidebarController.functionListEditStarted(e);
    }

    @FXML
    private void functionListEditCommit(ListView.EditEvent<FunctionDefinitionStructure> e){
        Logger.debug("Function list edit commit");
        sidebarController.functionListEditCommit(e);
    }

    @FXML
    private void functionListEditCancel(ListView.EditEvent<FunctionDefinitionStructure> e){
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

    // Toolset events

    @FXML
    void plusClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.PLUS, (ToggleButton) event.getSource());
    }

    @FXML
    void minusClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.MINUS, (ToggleButton) event.getSource());
    }

    @FXML
    void multiplyClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.MULTIPLY, (ToggleButton) event.getSource());
    }

    @FXML
    void divideClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.DIVIDE, (ToggleButton) event.getSource());
    }

    @FXML
    void moduloClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.MODULO, (ToggleButton) event.getSource());
    }

    @FXML
    void numberInputClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.NUMBER_INPUT, (ToggleButton) event.getSource());
    }

    @FXML
    void integerInputClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.INTEGER_INPUT, (ToggleButton) event.getSource());
    }

    @FXML
    void lessThanClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.LESS_THAN, (ToggleButton) event.getSource());
    }

    @FXML
    void lessThanEqualToClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.LESS_THAN_EQUAL_TO, (ToggleButton) event.getSource());
    }

    @FXML
    void greaterThanClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.GREATER_THAN, (ToggleButton) event.getSource());
    }

    @FXML
    void greaterThanEqualToClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.GREATER_THAN_EQUAL_TO, (ToggleButton) event.getSource());
    }

    @FXML
    void equalToClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.EQUAL_TO, (ToggleButton) event.getSource());
    }

    @FXML
    void notEqualToClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.NOT_EQUAL_TO, (ToggleButton) event.getSource());
    }

    @FXML
    void andClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.AND, (ToggleButton) event.getSource());
    }

    @FXML
    void orClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.OR, (ToggleButton) event.getSource());
    }

    @FXML
    void notClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.NOT, (ToggleButton) event.getSource());
    }

    @FXML
    void booleanInputClicked(ActionEvent event){
        workspaceController.getTool().toggleNodeCreationFor(NodeTool.Type.BOOLEAN_INPUT, (ToggleButton) event.getSource());
    }
}
