package app.delegate;


import editor.container.FunctionDefinitionStructure;

/** All communication from the sidebar controller TO its parent controller */
public interface SidebarListener extends ChildControllerListener {
    /** Every time a change in function definition happens, the method is invoked*/
    void selectionChangedTo(FunctionDefinitionStructure newSelection,FunctionDefinitionStructure oldSelection);
}
