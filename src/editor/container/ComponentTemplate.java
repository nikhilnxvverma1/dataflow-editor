package editor.container;

import editor.util.Logger;
import model.component.*;
import model.component.library.MyComponent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponentTemplate {

    private static final Object [] LIBRARY = new Object [] {new MyComponent()};
    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = createPrimitiveMap();

    private ComponentNode componentNode;
    private ArrayList<Class> inputTypes;
    private ArrayList<Class> outputTypes;

    private ComponentTemplate(ComponentNode componentNode) {
        this.componentNode = componentNode;
        this.inputTypes = ComponentTemplate.buildTypeList(componentNode.getInputChannels());
        this.outputTypes = ComponentTemplate.buildTypeList(componentNode.getOutputChannels());
    }

    /**
     * Builds a new component based on the deep copy of the component that it is holding.
     * @return a cloned component
     */
    public ComponentNode buildComponent(){
        ComponentNode clone = new ComponentNode();
        clone.setX(componentNode.getX());
        clone.setY(componentNode.getY());
        clone.setName(componentNode.getName());
        clone.setDescription(componentNode.getDescription());
        clone.setRedBg(componentNode.getRedBg());
        clone.setGreenBg(componentNode.getGreenBg());
        clone.setBlueBg(componentNode.getBlueBg());
        clone.setRedFg(componentNode.getRedFg());
        clone.setGreenFg(componentNode.getGreenFg());
        clone.setBlueFg(componentNode.getBlueFg());
        clone.setInputChannelNames(new ArrayList<String>(componentNode.getInputChannelNames()));
        clone.setInputChannels(new ArrayList<String>(componentNode.getInputChannels()));
        clone.setOutputChannelNames(new ArrayList<String>(componentNode.getOutputChannelNames()));
        clone.setOutputChannels(new ArrayList<String>(componentNode.getOutputChannels()));
        return clone;
    }

    public ArrayList<Class> getInputTypes() {
        return inputTypes;
    }

    public ArrayList<Class> getOutputTypes() {
        return outputTypes;
    }

    public String getName(){
        return componentNode.getName();
    }

    public ComponentNode getComponentNode() {
        return componentNode;
    }

    private static HashMap<Class<?>, Class<?>> createPrimitiveMap(){
        HashMap<Class<?>, Class<?>> map = new HashMap<>();

        map.put(boolean.class, Boolean.class);
        map.put(byte.class, Byte.class);
        map.put(char.class, Character.class);
        map.put(double.class, Double.class);
        map.put(float.class, Float.class);
        map.put(int.class, Integer.class);
        map.put(long.class, Long.class);
        map.put(short.class, Short.class);
        map.put(void.class, Void.class);

        return map;
    }

    /**
     * Converts a list of types (in string format) to actual class types
     * @param typeNames a list of string each of which is a fully qualified class name in the classpath
     * @return an array of class types
     */
    private static ArrayList<Class> buildTypeList(List<String> typeNames){
        ArrayList<Class> types = new ArrayList<>(typeNames.size());
        for(String fullyQualifiedName:typeNames){
            try {
                Class<?> aClass = Class.forName(fullyQualifiedName);
                types.add(aClass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                Logger.error("Not a valid type "+fullyQualifiedName);
            }
        }
        return types;
    }

    /**
     * Reads all classes within a array
     * @param library array of objects representing components
     * @return list of component templates corresponding to each class in that array
     */
    public static ArrayList<ComponentTemplate> loadComponentsFrom(Object[] library){
        ArrayList<ComponentTemplate> componentList = new ArrayList<>(library.length);
        int index = 0;
        for(Object o : library){

            ComponentNode node = new ComponentNode();
            node.setName(node.getClass().getSimpleName());

            Class aClass = o.getClass();

            // look for Name and Color in Class type
            Annotation[] classAnnotations = aClass.getAnnotations();
            for(Annotation annotation : classAnnotations){

                if(annotation instanceof Name){

                    Name name = (Name) annotation;
                    node.setName(name.value());
                    node.setDescription(name.description());

                } else if (annotation instanceof Color){
                    Color color = (Color) annotation;
                    if(color.background().length>=3){
                        node.setRedBg(color.background()[0]);
                        node.setGreenBg(color.background()[1]);
                        node.setBlueBg(color.background()[2]);
                    }

                    if(color.foreground().length>=3){
                        node.setRedFg(color.foreground()[0]);
                        node.setGreenFg(color.foreground()[1]);
                        node.setBlueFg(color.foreground()[2]);
                    }
                }
            }

            // look for input and output channels in field
            Field[] fields = aClass.getDeclaredFields();
            for(Field field : fields){
                Annotation[] fieldAnnotations = field.getAnnotations();
                for(Annotation fieldAnnotation : fieldAnnotations){

                    if(fieldAnnotation instanceof Input){

                        // add to input channels by registering its canonical name
                        node.getInputChannels().add(wrapIfNeeded(field.getType()).getCanonicalName());

                        // set the name of the output channel by noting its name inside the annotation (if exists)
                        Input input = (Input) fieldAnnotation;
                        String name = input.value();
                        if(name.trim().equalsIgnoreCase("")){
                            node.getInputChannelNames().add(field.getName());
                        }else{
                            node.getInputChannelNames().add(name);
                        }

                    }else if(fieldAnnotation instanceof Output){

                        // add to output channels by registering its canonical name
                        node.getOutputChannels().add(wrapIfNeeded(field.getType()).getCanonicalName());

                        // set the name of the output channel by noting its name inside the annotation (if exists)
                        Output output = (Output) fieldAnnotation;
                        String name = output.value();
                        if(name.trim().equalsIgnoreCase("")){
                            node.getOutputChannelNames().add(field.getName());
                        }else{
                            node.getOutputChannelNames().add(name);
                        }
                    }
                }
            }
            componentList.add(new ComponentTemplate(node));
        }

        return componentList;
    }

    public static ArrayList<ComponentTemplate> loadDefaultLibrary(){
        return loadComponentsFrom(LIBRARY);
    }

    private static Class<?> wrapIfNeeded(Class<?> aClass){
        if(aClass.isPrimitive()){
            return PRIMITIVES_TO_WRAPPERS.get(aClass);
        }else{
            return aClass;
        }
    }
}
