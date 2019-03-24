package app.controller.util;

import editor.container.FunctionDefinitionStructure;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * A Zoomable and pannable area for holding content pertaining to a {@link FunctionDefinitionStructure}.
 * Keep in mind, this container does not handle any mouse events. So those will have to be setup separately.
 */
public class EditSpace extends ScrollPane {
    private static final double ZOOM_INTENSITY = 0.02;
    public static final double CONTENT_PANE_WIDTH = 1200;
    public static final double CONTENT_PANE_HEIGHT = 900;

    private double scaleValue;
    private Pane contentPane;
    private FunctionDefinitionStructure structure;
    private Node zoomNode;

    public EditSpace() {
        super();
        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setFitToHeight(true); //center
        setFitToWidth(true); //center

        // setup the content pane
        this.contentPane = new Pane();
        this.contentPane.setPrefSize(CONTENT_PANE_WIDTH,CONTENT_PANE_HEIGHT);
        this.setContent(contentPane);
        contentPane.setOnZoom(e -> {
            e.consume();
            onZoom(e.getZoomFactor(), new Point2D(e.getX(), e.getY()));
        });
    }

    public void setFunctionDefinitionStructure(FunctionDefinitionStructure structure){
        this.structure = structure;
        this.zoomNode = new Group(structure.group);
//        setContent(outerNode(zoomNode));
        contentPane.getChildren().clear();
        contentPane.getChildren().add(zoomNode);
        this.scaleValue = structure.cameraZ;
        updateScale();
        this.setHvalue(structure.cameraX);
        this.setVvalue(structure.cameraY);
    }

    public Pane getContentPane() {
        return contentPane;
    }

    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnZoom(e -> {
            e.consume();
            onZoom(e.getZoomFactor(), new Point2D(e.getX(), e.getY()));
        });
        return outerNode;
    }

    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    private void updateScale() {
        structure.group.setScaleX(scaleValue);
        structure.group.setScaleY(scaleValue);
    }

    private void onZoom(double zoomFactor, Point2D mousePoint) {
//        double zoomFactor = Math.exp(wheelDelta * ZOOM_INTENSITY);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = structure.group.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = structure.group.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));

        // save the scroll position in the structure
        structure.cameraX = this.getHvalue();
        structure.cameraY = this.getVvalue();
    }
}