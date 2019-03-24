package editor.container;

import app.controller.WorkspaceController;
import app.controller.util.EditSpace;
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
    public double cameraX = 0.5;
    public double cameraY = 0.5;
    public double cameraZ = 1;

    public FunctionDefinitionStructure(FunctionDefinition functionDefinition) {
        this.functionDefinition = functionDefinition;
        this.group = new Group();
    }
}