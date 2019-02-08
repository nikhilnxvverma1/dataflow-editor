package app.controller;

import app.delegate.SidebarListener;
import app.delegate.WorkspaceListener;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Main controller that receives all outer static UI events. This controller does not hold and state
 * and is not responsible for any business logic.
 */
public class MainWindowController implements SidebarListener, WorkspaceListener {

    //==================================================================================================================
    //  UI references
    //==================================================================================================================

    @FXML
    private AnchorPane rootContainer;
    @FXML
    private SubScene canvas;
    @FXML
    private ListView functionListView;

    //==================================================================================================================
    //  Descendant controllers and their initialization
    //==================================================================================================================

    private SidebarController sidebarController;
    private WorkspaceController workspaceController;

    public void initialize(){
        canvas.widthProperty().bind(rootContainer.widthProperty());
        canvas.heightProperty().bind(rootContainer.heightProperty());
        this.sidebarController = new SidebarController(this,functionListView);
        this.workspaceController = new WorkspaceController(this,canvas);
    }

    //==================================================================================================================
    //  Event handlers (for static UI)
    //==================================================================================================================

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
