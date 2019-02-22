package app.delegate;

import editor.command.Command;

/** Base interface for delegates of child controllers of {@link app.controller.MainWindowController} */
public interface ChildControllerListener {

    /**
     * Register a command to the command stack for proper undo redo functionality
     * @param command the command to push to the undo stack
     * @param executeBeforeRegistering if true the command will be executed before registering.
     */
    void registerCommand(Command command, boolean executeBeforeRegistering);
}
