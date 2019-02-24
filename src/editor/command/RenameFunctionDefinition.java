package editor.command;

import editor.container.FunctionDefinitionStructure;
import javafx.scene.control.ListView;

public class RenameFunctionDefinition implements Command{

    private FunctionDefinitionStructure objectToRename;
    private String newName;
    private String oldName;
    private int index;
    private ListView<FunctionDefinitionStructure> listView;

    public RenameFunctionDefinition(FunctionDefinitionStructure objectToRename, String newName, int index, ListView<FunctionDefinitionStructure> listView) {

        this.objectToRename = objectToRename;
        this.newName = newName;
        this.index = index;
        this.listView = listView;

        this.oldName = this.objectToRename.functionDefinition.getName();
    }

    @Override
    public void undo() {
        objectToRename.functionDefinition.setName(oldName);
        listView.getSelectionModel().select(index);
        listView.refresh();
    }

    @Override
    public void redo() {
        objectToRename.functionDefinition.setName(newName);
        listView.getSelectionModel().select(index);
        listView.refresh();
    }
}
