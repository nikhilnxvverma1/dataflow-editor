package app.controller;

import app.delegate.WorkspaceListener;
import javafx.scene.SubScene;

/**
 * Responds to all (delegated) canvas related events and managing the business workflow
 * for the selected function definition
 */
public class WorkspaceController {

    private WorkspaceListener workspaceListener;
    private SubScene canvas;

    public WorkspaceController(WorkspaceListener workspaceListener, SubScene canvas) {
        this.workspaceListener = workspaceListener;
        this.canvas = canvas;
    }
}
