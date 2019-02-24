package editor.command;

import editor.container.FunctionDefinitionStructure;
import editor.util.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class DeleteFunctionDefinition implements Command {
    private FunctionDefinitionStructure objectToDelete;
    private ListView<FunctionDefinitionStructure> listView;
    private ObservableList<FunctionDefinitionStructure> functionStructureList;
    private int index;

    public DeleteFunctionDefinition(FunctionDefinitionStructure objectToDelete,
                                    ListView<FunctionDefinitionStructure> listView,
                                    ObservableList<FunctionDefinitionStructure> functionStructureList,
                                    int index) throws RuntimeException{
        if(objectToDelete.functionDefinition.isMain()){
            throw new RuntimeException("Deleting main function is not allowed");
        }
        this.objectToDelete = objectToDelete;
        this.listView = listView;
        this.functionStructureList = functionStructureList;
        this.index = index;

    }

    @Override
    public void undo() {
        functionStructureList.add(index,objectToDelete);
        listView.getSelectionModel().select(index);
    }

    @Override
    public void redo() {

        // only deletes items that are not main (checked in the constructor above)
        functionStructureList.remove(objectToDelete);

    }
}
