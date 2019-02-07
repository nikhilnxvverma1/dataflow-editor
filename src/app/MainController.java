package app;

import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController {

    @FXML
    private AnchorPane rootContainer;
    @FXML
    private SubScene canvas;

    @FXML
    private ListView functionListView;

    public void initialize(){
        canvas.widthProperty().bind(rootContainer.widthProperty());
        canvas.heightProperty().bind(rootContainer.heightProperty());
    }

    @FXML
    private void mouseClickOnCanvas(MouseEvent mouseEvent){
        System.out.println("Mouse click registered");
    }

    @FXML
    private void mousePressedOnCanvas(MouseEvent mouseEvent){
        System.out.println("Mouse press registered");
    }

    @FXML
    private void mouseDraggedOnCanvas(MouseEvent mouseEvent){
        System.out.println("Mouse drag registered");
    }

    @FXML
    private void mouseReleasedOnCanvas(MouseEvent mouseEvent){
        System.out.println("Mouse release registered");
    }
}
