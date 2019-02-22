package app.delegate;

import editor.container.FunctionDefinitionStructure;

import java.util.List;

/** All communication from the workspace app.controller TO its parent app.controller */
public interface WorkspaceListener extends ChildControllerListener {
    FunctionDefinitionStructure getCurrentFunctionDefinitionStructure();
    List<FunctionDefinitionStructure> getAllFunctionDefinitionStructure();
}
