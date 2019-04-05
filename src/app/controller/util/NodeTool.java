package app.controller.util;

import app.controller.WorkspaceController;
import app.view.*;
import editor.command.CreateDataFlowNode;
import editor.container.ComponentTemplate;
import javafx.geometry.Point2D;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import model.*;
import model.module.ArithmeticNode;
import model.component.ComponentNode;
import model.module.FormatTextNode;

/**
 * Utility used by workspace controller to drive the toolset in creating nodes on the canvas under the current
 * function definition. This tool mostly extracts features that would otherwise have to go in workspace controller.
 */
public class NodeTool {

    private static final double TRANSLUCENCY = 0.5;

    private WorkspaceController backReference;
    private ToggleButton source = null;
    private DataFlowView nodePreview = null;
    private Type type = null;
    private ComponentTemplate componentTemplate;

    public NodeTool(WorkspaceController backReference) {
        this.backReference = backReference;

    }

    /**
     * Creation mode is when the workspace is actually creating nodes on mouse click
     * @return true if the workspace is in creation mode
     */
    public boolean inCreationMode(){
        return nodePreview!=null;
    }

    public void togglePreview(boolean visible){
        if (nodePreview != null) {
            nodePreview.setVisible(visible);
        }
    }

    /** Switches off creation mode of a type of node and defaults back to a selection mode */
    public void clear(){
        if (source != null) {
            source.setSelected(false);
            source = null;
        }
        if (nodePreview != null) {
            backReference.getCurrentStructure().group.getChildren().remove(nodePreview);
            nodePreview = null;
        }
        type = null;
    }

    public void toggleComponentCreationFor(ComponentTemplate componentTemplate, ToggleButton source){
        this.componentTemplate = componentTemplate;
        boolean inCreation = toggleNodeCreationFor(Type.COMPONENT,source);
        if(!inCreation){
            this.componentTemplate = null;
        }
    }

    public boolean toggleNodeCreationFor(Type type, ToggleButton source){
        boolean creationMode;

        // go back to normal mode
        if (source == this.source) {
            clear();
            creationMode = false;
        }else{

            // if existing source exists, set it to false
            if(this.source!=null){
                this.source.setSelected(false);
            }

            // set a new data flow view and enable its creation
            this.type = type;
            nodePreview = buildPreviewDataFlowView();
            backReference.getCurrentStructure().group.getChildren().add(nodePreview);
            this.source = source;
            this.source.setSelected(true);
            creationMode = true;
        }
        return creationMode;
    }

    public DataFlowView createNode(MouseEvent mouseEvent){
        if (nodePreview != null) {

            Point2D p = backReference.transformedAfterPan(mouseEvent);
            nodePreview.setLocation(p.getX(),p.getY());

            // get rid of the translucency
            nodePreview.setOpacity(1);

            // create the command and execute it through the workspace
            DataFlowView newNodeCreated = nodePreview;
            CreateDataFlowNode createNode = new CreateDataFlowNode(newNodeCreated,backReference.getCurrentStructure());
            backReference.registerCommand(createNode,true);

            //set a new node preview at mouse position
            nodePreview = buildPreviewDataFlowView();
            backReference.getCurrentStructure().group.getChildren().add(nodePreview);
            nodePreview.setLocation(p.getX(),p.getY());

            return newNodeCreated;
        }
        return null;
    }

    public void mouseMoved(MouseEvent mouseEvent){
        if (nodePreview != null) {

            Point2D p = backReference.transformedAfterPan(mouseEvent);
            nodePreview.setLocation(p.getX(),p.getY());
        }
    }

    private DataFlowView buildPreviewDataFlowView(){
        DataFlowView dataFlowView = null;
        switch (type){

            case PLUS:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.ADD);
                dataFlowView =  new ArithmeticNodeView(model,backReference);
            }
            break;
            case MINUS:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.SUBTRACT);
                dataFlowView =  new ArithmeticNodeView(model,backReference);
            }
            break;
            case MULTIPLY:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.MULTIPLY);
                dataFlowView =  new ArithmeticNodeView(model,backReference);
            }
            break;
            case DIVIDE:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.DIVIDE);
                dataFlowView =  new ArithmeticNodeView(model,backReference);
            }
            break;
            case MODULO:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.MODULO);
                dataFlowView =  new ArithmeticNodeView(model,backReference);
            }
            break;
            case NUMBER_INPUT:
            {
                NumberInputNode model = new NumberInputNode(0,0);
                dataFlowView =  new NumberInputView(model,backReference);
            }
            break;
            case INTEGER_INPUT:
            {
                NumberInputNode model = new NumberInputNode(0,0); // number can store integer
                dataFlowView =  new IntegerInputView(model,backReference);
            }
            break;
            case BOOLEAN_INPUT:
            {
                BooleanInputNode model = new BooleanInputNode(0,0);
                dataFlowView =  new BooleanNodeView(model,backReference);
            }
            break;
            case STRING_INPUT:
            {
                StringInputNode model = new StringInputNode(0,0); // number can store integer
                dataFlowView =  new StringInputView(model,backReference);
            }
            break;
            case DATE_INPUT:
            {
                DateInputNode model = new DateInputNode(0,0); // number can store integer
                dataFlowView =  new DateInputView(model,backReference);
            }
            break;
            case COLOR_INPUT:
            {
                ColorInputNode model = new ColorInputNode(0,0); // number can store integer
                dataFlowView =  new ColorInputView(model,backReference);
            }
            break;
            case LESS_THAN:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.LESS_THAN);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case LESS_THAN_EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.LESS_THAN_EQUAL_TO);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case GREATER_THAN:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.GREATER_THAN);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case GREATER_THAN_EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.GREATER_THAN_EQUAL_TO);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.EQUALS);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case NOT_EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.NOT_EQUALS);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case AND:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.AND);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case OR:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.OR);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case NOT:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.NOT);
                dataFlowView =  new ConditionalNodeView(model,backReference);
            }
            break;
            case COMPONENT:
                dataFlowView = new ComponentNodeView(componentTemplate, backReference);
                break;
            case FORMAT_TEXT:
                FormatTextNode formatTextNode = new FormatTextNode(0,0);
                dataFlowView = new FormatTextView(formatTextNode,backReference);
                break;
        }

        dataFlowView.setOpacity(TRANSLUCENCY);

        return dataFlowView;
    }

    public enum Type{
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        MODULO,
        NUMBER_INPUT,
        INTEGER_INPUT,
        BOOLEAN_INPUT,
        STRING_INPUT,
        DATE_INPUT,
        COLOR_INPUT,
        LESS_THAN,
        LESS_THAN_EQUAL_TO,
        GREATER_THAN,
        GREATER_THAN_EQUAL_TO,
        EQUAL_TO,
        NOT_EQUAL_TO,
        AND,
        OR,
        NOT,
        FORMAT_TEXT,
        CLIPBOARD_HISTORY,
        DELIMITED_TOKENS,
        INPUT,
        ARGUMENT,
        OUTPUT,
        FILE_CHOOSER,
        DIRECTORY_CHOOSER,
        COMPONENT
    }

}
