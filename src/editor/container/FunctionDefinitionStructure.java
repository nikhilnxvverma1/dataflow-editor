package editor.container;

import app.controller.WorkspaceController;
import app.view.DataFlowView;
import javafx.scene.Group;
import model.FunctionDefinition;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple data container that holds an aggregate of function definition model, view(i.e. group),
 * and current camera position of the scene
 */
public class FunctionDefinitionStructure{

    public FunctionDefinition functionDefinition;
    public Group group;
    public List<DataFlowView> nodeViewList = new LinkedList<>();
//    public double cameraX = WorkspaceController.SCROLL_PANE_WIDTH/2;
//    public double cameraY = WorkspaceController.SCROLL_PANE_HEIGHT/2;
    public double cameraX = 0;
    public double cameraY = 0;
    public double cameraZ = 1;

    public FunctionDefinitionStructure(FunctionDefinition functionDefinition) {
        this.functionDefinition = functionDefinition;
        this.group = new Group();
//        this.group.setPrefSize(PANE_WIDTH,PANE_HEIGHT);
//        group.setStyle("-fx-background-color: red;"); //debugging purposes only
//        group.setStyle("-fx-pref-width: 999px;"); //debugging purposes only
//        group.setStyle("-fx-pref-height: 999px;"); //debugging purposes only
//        this.cameraX = 0;
//        this.cameraY = WorkspaceController.TITLE_BAR_HEIGHT;
//        this.cameraZ = WorkspaceController.DEFAULT_CAMERA_Z;
    }
}