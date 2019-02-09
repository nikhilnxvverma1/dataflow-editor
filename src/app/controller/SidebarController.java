package app.controller;

import app.delegate.SidebarListener;
import editor.container.FunctionDefinitionStructure;
import javafx.scene.control.ListView;
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
    private ArrayList<FunctionDefinitionStructure> functionStructureList = new ArrayList<>();
    private FunctionDefinitionStructure selectedFunctionDefinition;

    SidebarController(SidebarListener sidebarListener, ListView functionListView) {
        this.sidebarListener = sidebarListener;
        this.functionListView = functionListView;
    }

    public void initialize(List <FunctionDefinition> functionDefinitionList){

        // if the list is empty or null, make sure to not construct the function definition from scratch
        if(functionDefinitionList==null || functionDefinitionList.size()==0){
            FunctionDefinition main = new FunctionDefinition(true);
            FunctionDefinitionStructure aggregate = new FunctionDefinitionStructure(main);
            this.functionStructureList.add(aggregate);
            this.selectedFunctionDefinition = aggregate;
        }else{

            // wrap each function definition model, in a function definition container
            for(FunctionDefinition functionDefinition : functionDefinitionList){

                FunctionDefinitionStructure aggregate = new FunctionDefinitionStructure(functionDefinition);

                //by default, the main function definition will be the selected function definition
                if(functionDefinition.isMain()){
                    this.selectedFunctionDefinition = aggregate;
                }

                this.functionStructureList.add(aggregate);
            }
        }


    }

    //==================================================================================================================
    //  Event received from the parent controller
    //==================================================================================================================
}
