package editor.command;

/**
 * Interface for holding all undoable and re-doable objects
 */
public interface Command {

    /** Reverted execution of the command. */
    void undo();

    /** Forward execution of the command. */
    void redo();

    /**
     * Get the index of the function definition in the function list view in which this event occurred.
     * This will be used to select the right function definition after each undo or redo operation.
     * @param undoOrRedo if true, this call is made after an undo, otherwise it's made after a redo.
     */
    int getFunctionDefinitionIndex(boolean undoOrRedo);
}
