package editor.container;

import app.controller.WorkspaceController;
import app.view.DataFlowView;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import model.FunctionDefinition;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple data container that holds an aggregate of function definition model, view(i.e. pane),
 * and current camera position of the scene
 */
public class FunctionDefinitionStructure{

    public FunctionDefinition functionDefinition;
    public Pane pane;
    public List<DataFlowView> nodeViewList = new LinkedList<>();
    public double cameraX;
    public double cameraY;
    public double cameraZ;

    public FunctionDefinitionStructure(FunctionDefinition functionDefinition) {
        this.functionDefinition = functionDefinition;
        this.pane = new Pane();
//        this.pane.setPrefSize(PANE_WIDTH,PANE_HEIGHT);
//        pane.setStyle("-fx-background-color: red;"); //debugging purposes only
//        pane.setStyle("-fx-pref-width: 999px;"); //debugging purposes only
//        pane.setStyle("-fx-pref-height: 999px;"); //debugging purposes only
        this.cameraX = 0;
        this.cameraY = WorkspaceController.TITLE_BAR_HEIGHT;
        this.cameraZ = WorkspaceController.DEFAULT_CAMERA_Z;
    }
}