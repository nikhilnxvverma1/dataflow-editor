package app.delegate;

import editor.container.FunctionDefinitionStructure;

/** All communication from the workspace app.controller TO its parent app.controller */
public interface WorkspaceListener {
    FunctionDefinitionStructure getCurrentFunctionDefinitionStructure();
}
