package editor.command;

/**
 * Interface for holding all undoable and re-doable objects
 */
public interface Command {
    void undo();
    void redo();
}
