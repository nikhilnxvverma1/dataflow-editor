package app.controller;

import app.delegate.SidebarListener;
import editor.container.FunctionDefinitionStructure;
import editor.util.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import model.FunctionDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for handling all delegated events in the sidebar view,
 * and managing function list definitions
 */
public class SidebarController {

    /** Usually the main window controller will be the listener. This is a decoupled back reference */
    private SidebarListener sidebarListener;
    private ListView functionListView;

    // List of functions structures
    // TODO change to an observable FXCollections list
    private ArrayList<FunctionDefinitionStructure> functionStructureList = new ArrayList<>();
    private FunctionDefinitionStructure selectedDefinitionStructure;

    SidebarController(SidebarListener sidebarListener, ListView functionListView) {
        this.sidebarListener = sidebarListener;
        this.functionListView = functionListView;
    }

    public void initialize(List <FunctionDefinition> functionDefinitionList){

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
        functionListView.setCellFactory(TextFieldListCell.forListView());
        for(FunctionDefinitionStructure functionDefinitionStructure : functionStructureList){
            functionListView.getItems().add(functionDefinitionStructure.functionDefinition.getName());
        }

        // track selection every time it changes in list view
        functionListView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int oldSelectionIndex = oldValue.intValue();
                FunctionDefinitionStructure oldSelection;
                if(oldSelectionIndex==-1){
                    oldSelection = null;
                }else{
                    oldSelection = functionStructureList.get(oldSelectionIndex);
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
            functionListView.getItems().set(index,target.getName());
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
        functionListView.getItems().add(newDefinition.getName());
        functionListView.getSelectionModel().selectLast();
        // TODO create a new function definition command and use that instead
    }

    private String getValidNewName(){
        return "function" + functionStructureList.size();
    }

    void deleteSelectedFunctionDefinition(){
        int selectedIndex = functionListView.getSelectionModel().getSelectedIndex();
        //remove selected index if it is not main
        if(!functionStructureList.get(selectedIndex).functionDefinition.isMain()){

            // remove from list view first so that the callback doesn't crash (because of a small dependency)
            functionListView.getItems().remove(selectedIndex);
            functionStructureList.remove(selectedIndex);

        }else{
            Logger.info("Deletion of main function definition disallowed");
        }

        // TODO create a new delete command and commit it
    }

    List<FunctionDefinitionStructure> getFunctionStructureList(){
        return this.functionStructureList;
    }
}
