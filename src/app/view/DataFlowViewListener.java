package app.view;

import editor.command.Command;

/**
 * Delegate that handles business callbacks as they occur on dataflow views
 */
public interface DataFlowViewListener {

    /**
     * Register a command to the command stack for proper undo redo functionality
     * @param command the command to push to the undo stack
     * @param executeBeforeRegistering if true the command will be executed before registering.
     */
    void registerCommand(Command command, boolean executeBeforeRegistering);
}
