package editor.command;

import editor.container.FunctionDefinitionStructure;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class AddFunctionDefinition implements Command {

    private FunctionDefinitionStructure functionDefinitionStructure;
    private ListView<FunctionDefinitionStructure> listView;
    private ObservableList<FunctionDefinitionStructure> functionStructureList;

    public AddFunctionDefinition(FunctionDefinitionStructure functionDefinitionStructure,
                                 ListView<FunctionDefinitionStructure> listView,
                                 ObservableList<FunctionDefinitionStructure> functionStructureList) {
        this.functionDefinitionStructure = functionDefinitionStructure;
        this.listView = listView;
        this.functionStructureList = functionStructureList;
    }

    @Override
    public void undo() {
        functionStructureList.remove(functionDefinitionStructure);
    }

    @Override
    public void redo() {
        functionStructureList.add(functionDefinitionStructure);
        listView.getSelectionModel().selectLast();
    }
}
