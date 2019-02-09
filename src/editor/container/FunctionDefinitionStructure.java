package editor.container;

import javafx.scene.Group;
import model.FunctionDefinition;

/**
 * Simple data container that holds an aggregate of function definition model, view(i.e. group),
 * and current camera position of the scene
 */
public class FunctionDefinitionStructure{
    FunctionDefinition functionDefinition;
    Group group;
    double cameraX;
    double cameraY;
    double cameraZ;

    public FunctionDefinitionStructure(FunctionDefinition functionDefinition) {
        this.functionDefinition = functionDefinition;
        this.group = new Group();
    }
}