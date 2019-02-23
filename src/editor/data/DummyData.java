package editor.data;

import model.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Developer convenience class useful for making dummy data and supplying it over to controller for operations.
 * Most(if not all) methods here are static.
 */
public class DummyData {

    private static final Random random = new Random();
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


    /**
     * Builds an list of empty function definitions,first function will always be main, next 10 will be named in order
     * and remaining will be named in counter increments
     * @param count number of definitions
     * @return list of definitions
     */
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

    /**
     * Builds an list of filled function definitions,first function will always be main, next 10 will be named in order
     * and remaining will be named in counter increments
     * @param count number of definitions
     * @param lowerLimitOfNodeCount random number of nodes per definition will be above this number(inclusive)
     * @param upperLimitOfNodeCount random number of nodes per definition will be below this number(exclusive)
     * @return list of definitions
     */
    public static List<FunctionDefinition> filledFunctionDefinitions(int count,
                                                                     int lowerLimitOfNodeCount,
                                                                     int upperLimitOfNodeCount){
        List<FunctionDefinition> definitionList = new LinkedList<>();
        for(int i=0;i<count;i++){

            // random function definition with random number of node
            int randomNodeCount = lowerLimitOfNodeCount + random.nextInt(upperLimitOfNodeCount);
            FunctionDefinition randomDefinition = randomNodesInADefinition(randomNodeCount,DataFlowNodeType.CONDITIONAL);

            // naming
            if(i==0){
                randomDefinition.setMain(true);
            }else if(i<11){
                randomDefinition.setName(nthString[i-1]);
            }else{
                randomDefinition.setName("function-"+i);
            }
            definitionList.add(randomDefinition);
        }
        return definitionList;
    }

    /**
     * Used within the context of dummy data to indicate the type of dataflow node
     */
    public enum DataFlowNodeType{
        ARITHMETIC,
        CONDITIONAL,
        BOOLEAN_INPUT,
        NUMBER_INPUT;

        public static DataFlowNodeType getRandomType(){
            return values()[random.nextInt(values().length)];
        }
    }

    /**
     * Builds a single node of a given type
     * @param x x position
     * @param y y position
     * @param type type of node
     * @return a generic DataFlowNode
     */
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

    /**
     * Builds a function definition with random nodes  of the same type
     * @param count number of nodes
     * @param type type of nodes
     * @return function definition encapsulation all the random nodes
     */
    public static FunctionDefinition randomNodesInADefinition(int count,DataFlowNodeType type){
        FunctionDefinition functionDefinition = new FunctionDefinition(false,"random"+count);
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * 1000;
            double y = random.nextDouble() * 800;
            functionDefinition.getDataFlowNodes().add(nodeAt(x,y,type));
        }
        return functionDefinition;
    }

    /**
     * Builds a function definition with random nodes  of different(random) type
     * @param count number of nodes
     * @return function definition encapsulation all the random nodes
     */
    public static FunctionDefinition randomNodesInADefinition(int count){
        FunctionDefinition functionDefinition = new FunctionDefinition(false,"random"+count);
        for (int i = 0; i < count; i++) {
            double x = random.nextDouble() * 1000;
            double y = random.nextDouble() * 800;
            DataFlowNodeType type = DataFlowNodeType.getRandomType();
            functionDefinition.getDataFlowNodes().add(nodeAt(x,y,type));
        }
        return functionDefinition;
    }
}
