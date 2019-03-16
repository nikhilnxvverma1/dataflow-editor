package editor.data;

import model.Root;

/** Responsible for loading and saving entire data model to an XML file.*/
public class XmlData {

    /**
     * Loads the entire data model from the specified XML file
     * @param filename the absolute filepath to the xml file from which model needs to be created
     * @return root model holding all functions containing the data flow program
     */
    public Root loadModel(String filename){
        return null;
    }

    /**
     * Saves the entire data model to a specified file
     * @param root the root model containing all the function definitions holding data flow programs
     * @param filename the absolute filepath to an xml file. If the file doesn't exist, it will be created
     */
    public void saveModel(Root root, String filename){

    }

}
