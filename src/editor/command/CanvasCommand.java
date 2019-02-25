package editor.command;

public abstract class CanvasCommand implements Command{
    int functionDefinitionIndex;

    public void setFunctionDefinitionIndex(int functionDefinitionIndex) {
        this.functionDefinitionIndex = functionDefinitionIndex;
    }
}
