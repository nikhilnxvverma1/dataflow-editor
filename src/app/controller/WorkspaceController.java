package app.controller;

import app.delegate.WorkspaceListener;
import editor.container.FunctionDefinitionStructure;
import javafx.scene.SubScene;
import model.FunctionDefinition;

/**
 * Responds to all (delegated) canvas related events and managing the business workflow
 * for the selected function definition
 */
public class WorkspaceController {

    /** Usually the main window controller will be the listener. This is a decoupled back reference */
    private WorkspaceListener workspaceListener;
    private SubScene canvas;

    public WorkspaceController(WorkspaceListener workspaceListener, SubScene canvas) {
        this.workspaceListener = workspaceListener;
        this.canvas = canvas;
    }

    void initialize(){
        FunctionDefinitionStructure currentStructure = workspaceListener.getCurrentFunctionDefinitionStructure();

    }

    //==================================================================================================================
    //  Event received from the parent controller
    //==================================================================================================================
}
