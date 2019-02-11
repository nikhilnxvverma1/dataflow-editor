package editor.data;

import model.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Developer convenience class useful for making dummy data and supplying it over to controller for operations.
 * Most(if not all) methods here are static.
 */
public class DummyData {

    private static String [] nthString = {
            "first",
            "second",
            "third",
            "fourth",
            "fifth",
            "sixth",
            "seventh",
            "eighth",
            "ninth",
            "tenth"};


    public static List<FunctionDefinition> emptyFunctionDefinitions(int count){
        List<FunctionDefinition> emptyDefinitions = new LinkedList<FunctionDefinition>();
        for(int i=0;i<count;i++){
            if(i==0){
                emptyDefinitions.add(new FunctionDefinition(true, "main"));
            }else if(i<11){
                emptyDefinitions.add(new FunctionDefinition(false, nthString[i-1]));
            }else{
                emptyDefinitions.add(new FunctionDefinition(false, "function-"+i));
            }
        }
        return emptyDefinitions;
    }

    public enum DataFlowNodeType{
        ARITHMETIC,
        CONDITIONAL,
        BOOLEAN_INPUT,
        NUMBER_INPUT,
    }

    public static DataFlowNode nodeAt(double x, double y,DataFlowNodeType type){
        DataFlowNode node = null;
        switch (type){
            case ARITHMETIC:
                node = new ArithmeticNode(x,y);
                break;
            case CONDITIONAL:
                node = new ConditionalNode(x,y);
                break;
            case BOOLEAN_INPUT:
                node = new BooleanInputNode(x,y);
                break;
            case NUMBER_INPUT:
                node = new NumberInputNode(x,y);
                break;
        }
        return node;
    }
}
