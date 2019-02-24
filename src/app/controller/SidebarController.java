package app.controller;

import app.delegate.SidebarListener;
import editor.command.DeleteFunctionDefinition;
import editor.container.FunctionDefinitionStructure;
import editor.util.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import model.FunctionDefinition;
import java.util.List;

/**
 * Responsible for handling all delegated events in the sidebar view,
 * and managing function list definitions
 */
class SidebarController {

    /** Usually the main window controller will be the listener. This is a decoupled back reference */
    private SidebarListener sidebarListener;
    private ListView<FunctionDefinitionStructure> functionListView;

    // List of functions structures
    private ObservableList<FunctionDefinitionStructure> functionStructureList = FXCollections.observableArrayList();
    private FunctionDefinitionStructure selectedDefinitionStructure;

    SidebarController(SidebarListener sidebarListener, ListView<FunctionDefinitionStructure> functionListView) {
        this.sidebarListener = sidebarListener;
        this.functionListView = functionListView;
    }

    void initialize(List<FunctionDefinition> functionDefinitionList){

        int selectionIndex = 0;

        // if the list is empty or null,
        if(functionDefinitionList==null || functionDefinitionList.size()==0){

            // make sure to construct only the main function definition from scratch
            FunctionDefinition main = new FunctionDefinition(true,"main");
            FunctionDefinitionStructure aggregate = new FunctionDefinitionStructure(main);
            this.functionStructureList.add(aggregate);
            this.selectedDefinitionStructure = aggregate;

        }else{
            int index = 0;
            // wrap each function definition model, in a function definition container
            for(FunctionDefinition functionDefinition : functionDefinitionList){

                FunctionDefinitionStructure aggregate = new FunctionDefinitionStructure(functionDefinition);

                //by default, the main function definition will be the selected function definition
                if(functionDefinition.isMain()){
                    this.selectedDefinitionStructure = aggregate;
                    selectionIndex = index;
                }

                this.functionStructureList.add(aggregate);
            }
        }

        // add the names of the function definitions in the list view
        functionListView.setCellFactory(param-> new ListCell<FunctionDefinitionStructure>(){
            @Override
            protected void updateItem(FunctionDefinitionStructure item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.functionDefinition == null) {
                    setText(null);
                } else {
                    setText(item.functionDefinition.getName());
                }
            }
        });

        functionListView.setItems(functionStructureList);

        // track selection every time it changes in list view
        functionListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                FunctionDefinitionStructure oldSelection;
                if(oldValue.intValue() ==-1){
                    // this happens when during application startup when old selection does not exist
                    oldSelection = null;
                }else if(oldValue.intValue() >= functionStructureList.size()) {
                    // this happens when the last function is deleted
                    oldSelection = functionStructureList.get(functionStructureList.size()-1);
                }else{
                    oldSelection = functionStructureList.get(oldValue.intValue());
                }
                selectedDefinitionStructure = functionStructureList.get(newValue.intValue());
                Logger.debug("Selection will change");
                sidebarListener.selectionChangedTo(selectedDefinitionStructure,oldSelection);
            }
        });

        // select the main function
        // IMPORTANT: this should only be done AFTER the selection change callback has been registered
        functionListView.getSelectionModel().select(selectionIndex);
    }

    FunctionDefinitionStructure getCurrentFunctionDefinitionStructure(){
        int selectedIndex = functionListView.getSelectionModel().getSelectedIndex();
        return functionStructureList.get(selectedIndex);
    }

    //==================================================================================================================
    //  Events received from the parent controller
    //==================================================================================================================

    void functionListEditStarted(ListView.EditEvent<String> e){
        // TODO initialize the function name command
    }

    void functionListEditCommit(ListView.EditEvent<String> e){
        int index = e.getIndex();
        FunctionDefinition target = functionStructureList.get(index).functionDefinition;
        if(!target.isMain()){
            target.setName(e.getNewValue());
//            functionListView.getItems().set(index,target.getName());
        }//else show a notice saying you can't rename main method
        // TODO commit the edit function name command
    }

    void functionListEditCancel(ListView.EditEvent<String> e){
        // TODO cancel the edit function name command
    }

    void newFunctionDefinition(ActionEvent actionEvent){

        FunctionDefinition newDefinition = new FunctionDefinition(false,getValidNewName());
        FunctionDefinitionStructure contained = new FunctionDefinitionStructure(newDefinition);
        functionStructureList.add(contained);
//        functionListView.getItems().add(newDefinition.getName());
        functionListView.getSelectionModel().selectLast();
        // TODO create a new function definition command and use that instead
    }

    private String getValidNewName(){
        return "function" + functionStructureList.size();
    }

    void deleteSelectedFunctionDefinition(){

        //remove selected object if it is not main
        int selectedIndex = functionListView.getSelectionModel().getSelectedIndex();
        FunctionDefinitionStructure objectToDelete = functionStructureList.get(selectedIndex);

        // don't delete main
        if(!objectToDelete.functionDefinition.isMain()){

            // create a new delete command and commit it
            sidebarListener.registerCommand(
                    new DeleteFunctionDefinition(objectToDelete,
                            functionListView,
                            functionStructureList,
                            selectedIndex),
                    true);
        }else{
            Logger.info("Deletion of main function definition disallowed");
        }


    }

    List<FunctionDefinitionStructure> getFunctionStructureList(){
        return this.functionStructureList;
    }
}
