package model;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/** The root model holding all the functions that contain the data flow program */
@XmlRootElement(namespace = "app.model")
public class Root {

    List<FunctionDefinition> functionDefinitionList;

    public Root() {
    }

    public List<FunctionDefinition> getFunctionDefinitionList() {
        return functionDefinitionList;
    }

    public void setFunctionDefinitionList(List<FunctionDefinition> functionDefinitionList) {
        this.functionDefinitionList = functionDefinitionList;
    }
}
