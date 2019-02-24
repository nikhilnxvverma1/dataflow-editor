package app.controller.util;

import app.controller.WorkspaceController;
import app.view.*;
import javafx.scene.control.ToggleButton;
import model.ArithmeticNode;
import model.BooleanInputNode;
import model.ConditionalNode;
import model.NumberInputNode;

/**
 * Utility used by workspace controller to drive the toolset in creating nodes on the canvas under the current
 * function definition. This tool mostly extracts features that would otherwise have to go in workspace controller.
 */
public class NodeTool {

    private WorkspaceController backReference;
    private ToggleButton source = null;
    private DataFlowView nodePreview = null;

    public NodeTool(WorkspaceController backReference) {
        this.backReference = backReference;

    }

    public boolean toggleNodeCreationFor(Type type,ToggleButton source){
        boolean creationMode;

        // go back to normal mode
        if (source == this.source) {
            this.source = null;
            source.setSelected(false);
            creationMode = false;
        }else{

            // if existing source exists, set it to false
            if(this.source!=null){
                this.source.setSelected(false);
            }

            // set a new data flow view and enable its creation
            nodePreview = buildPreviewDataFlowView(type);
            this.source = source;
            this.source.setSelected(true);
            creationMode = true;
        }
        return creationMode;
    }

    private DataFlowView buildPreviewDataFlowView(Type type){
        switch (type){

            case PLUS:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.ADD);
                return new ArithmeticNodeView(model,backReference);
            }
            case MINUS:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.SUBTRACT);
                return new ArithmeticNodeView(model,backReference);
            }
            case MULTIPLY:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.MULTIPLY);
                return new ArithmeticNodeView(model,backReference);
            }
            case DIVIDE:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.DIVIDE);
                return new ArithmeticNodeView(model,backReference);
            }
            case MODULO:
            {
                ArithmeticNode model = new ArithmeticNode(0, 0);
                model.setType(ArithmeticNode.Type.MODULO);
                return new ArithmeticNodeView(model,backReference);
            }
            case NUMBER_INPUT:
            {
                NumberInputNode model = new NumberInputNode(0,0);
                return new NumberInputView(model,backReference);
            }
            case BOOLEAN_INPUT:
            {
                BooleanInputNode model = new BooleanInputNode(0,0);
                return new BooleanNodeView(model,backReference);
            }
            case LESS_THAN:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.LESS_THAN);
                return new ConditionalNodeView(model,backReference);
            }
            case LESS_THAN_EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.LESS_THAN_EQUAL_TO);
                return new ConditionalNodeView(model,backReference);
            }
            case GREATER_THAN:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.GREATER_THAN);
                return new ConditionalNodeView(model,backReference);
            }
            case GREATER_THAN_EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.GREATER_THAN_EQUAL_TO);
                return new ConditionalNodeView(model,backReference);
            }
            case EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.EQUALS);
                return new ConditionalNodeView(model,backReference);
            }
            case NOT_EQUAL_TO:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.NOT_EQUALS);
                return new ConditionalNodeView(model,backReference);
            }
            case AND:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.AND);
                return new ConditionalNodeView(model,backReference);
            }
            case OR:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.OR);
                return new ConditionalNodeView(model,backReference);
            }
            case NOT:
            {
                ConditionalNode model = new ConditionalNode(0, 0);
                model.setType(ConditionalNode.Type.NOT);
                return new ConditionalNodeView(model,backReference);
            }
            case FUNCTION:
                break;
            case MODULE:
                break;
        }

        return null;
    }

    public enum Type{
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        MODULO,
        NUMBER_INPUT,
        BOOLEAN_INPUT,
        LESS_THAN,
        LESS_THAN_EQUAL_TO,
        GREATER_THAN,
        GREATER_THAN_EQUAL_TO,
        EQUAL_TO,
        NOT_EQUAL_TO,
        AND,
        OR,
        NOT,
        FUNCTION,
        MODULE
    }

}
