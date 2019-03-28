package app.view;

import javafx.geometry.Pos;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import model.DataFlowNode;
import model.NumberInputNode;

/**
 * Data flow View with a text field that allows for integer outputs only.
 * Note that it uses a Number input model to store its content,
 * but value of its output channel is always Integer
 */
public class IntegerInputView extends NumberInputView{

    public IntegerInputView(NumberInputNode numberInputNode, DataFlowViewListener dataFlowViewListener) {
        super(numberInputNode,dataFlowViewListener);
    }

    boolean validInput(String text){
        try{
            Integer.parseInt(text);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    @Override
    public Class getTypeForOutput(int index) {
        return int.class;
    }
}
