package app.view;

import editor.command.CanvasCommand;
import editor.container.ConnectionPoint;
import editor.container.FunctionDefinitionStructure;

import java.util.List;

/**
 * Delegate that handles business callbacks as they occur on dataflow views
 */
public interface DataFlowViewListener {

    /**
     * Register a command to the command stack for proper undo redo functionality
     * @param command the command to push to the undo stack
     * @param executeBeforeRegistering if true the command will be executed before registering.
     */
    void registerCommand(CanvasCommand command, boolean executeBeforeRegistering);

    /**
     * Gets all input connection points with ready input channels
     * @param exclude excludes input connection points from this node
     * @return list of connection points excluding the ones from specified dataflow view
     */
    List<ConnectionPoint> getAvailableInputConnectionPoints(DataFlowView exclude);

    /**
     * Only one function definition can be active at any time, this method retrieves the active structure.
     * @return The function definition structure that currently exists on canvas
     */
    FunctionDefinitionStructure getCurrentStructure();
}
