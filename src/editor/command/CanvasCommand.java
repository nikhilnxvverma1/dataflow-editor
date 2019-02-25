package editor.command;

public abstract class CanvasCommand implements Command{
    int functionDefinitionIndex;

    public void setFunctionDefinitionIndex(int functionDefinitionIndex) {
        this.functionDefinitionIndex = functionDefinitionIndex;
    }

    @Override
    public int getFunctionDefinitionIndex(boolean undoOrRedo) {
        return functionDefinitionIndex;
    }

}
