package editor.util;

import editor.container.FunctionDefinitionStructure;
import javafx.util.StringConverter;
import model.FunctionDefinition;

public class FunctionDefinitionConverter extends StringConverter<FunctionDefinitionStructure> {

    @Override
    public String toString(FunctionDefinitionStructure object) {
        return object.functionDefinition.getName();
    }

    @Override
    public FunctionDefinitionStructure fromString(String string) {
        return new FunctionDefinitionStructure(new FunctionDefinition(false,string));
    }
}
