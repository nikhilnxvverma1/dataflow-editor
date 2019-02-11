package editor.container;

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
    public double cameraX;
    public double cameraY;
    public double cameraZ;

    public FunctionDefinitionStructure(FunctionDefinition functionDefinition) {
        this.functionDefinition = functionDefinition;
        this.group = new Group();
    }
}