package app.controller.util;

import app.controller.WorkspaceController;
import app.view.DataFlowView;
import editor.container.FunctionDefinitionStructure;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

/**
 * Manages selection/deselection of nodes by listening to change events on the selectionSet and adding observers
 * to the layout bound property of the highlight rect. These views are internal to the class.
 */
public class SelectionManager implements ListChangeListener<DataFlowView>, ChangeListener<Bounds> {
    private static final Paint HIGHLIGHT_FILL = new Color(1,1,1,0.1);
    private static final Paint HIGHLIGHT_OUTLINE = Color.WHITE;
    private static final Paint SELECTION_OUTLINE = Color.WHITE;
    private static final Paint SELECTION_FILL = new Color(1,1,1,0.0);
    private WorkspaceController backReference;
    private Rectangle highlightRect = new Rectangle();
    private ObservableList<DataFlowView> selectionSet = FXCollections.observableList(new LinkedList<>());
    private Rectangle selectionRect = new Rectangle();

    private double initialX = 0, initialY = 0;

    public SelectionManager(WorkspaceController backReference) {
        this.backReference = backReference;
        highlightRect.setFill(HIGHLIGHT_FILL);
        highlightRect.setStroke(HIGHLIGHT_OUTLINE);
        highlightRect.layoutBoundsProperty().addListener(this);
        selectionSet.addListener(this);
        selectionRect.setStroke(SELECTION_OUTLINE);
        selectionRect.setFill(SELECTION_FILL);
    }

    @Override
    public void onChanged(Change<? extends DataFlowView> c) {

        if(selectionSet.size()==0){
            backReference.getCurrentStructure().group.getChildren().remove(selectionRect);
        }else{
            if(!backReference.getCurrentStructure().group.getChildren().contains(selectionRect)){
                backReference.getCurrentStructure().group.getChildren().add(selectionRect);
                selectionRect.toBack();
            }
            //recompute the dimensions for the selection rect
            BoundingBox bounds = computeBoundsForSelection();
            selectionRect.setX(bounds.getMinX());
            selectionRect.setY(bounds.getMinY());
            selectionRect.setWidth(bounds.getWidth());
            selectionRect.setHeight(bounds.getHeight());
        }
    }

    /**
     * Compute the dimensions for the selected items
     * @return Bounding box describing the bounds in the current structure's group coordinate system
     */
    private BoundingBox computeBoundsForSelection() {
        double lowX = 0;
        double lowY = 0;
        double highX = 0;
        double highY = 0;
        boolean unset = true;

        for(DataFlowView each : selectionSet){
            Bounds bounds = each.getBoundsInParent();
            if(unset || bounds.getMinX() < lowX){
                lowX = bounds.getMinX();
            }
            if(unset || bounds.getMinY() < lowY){
                lowY = bounds.getMinY();
            }
            if(unset || bounds.getMaxX() > highX){
                highX = bounds.getMaxX();
            }
            if(unset || bounds.getMaxY() > highY){
                highY = bounds.getMaxY();
            }
            unset = false;
        }

        return new BoundingBox(lowX,lowY,highX-lowX,highY-lowY);
    }

    @Override
    public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {

        FunctionDefinitionStructure structure = backReference.getCurrentStructure();
        for(DataFlowView each : structure.nodeViewList){
            Bounds viewBounds = each.getBoundsInParent();
            Bounds highlightBounds = highlightRect.getBoundsInParent();
            if(highlightBounds.intersects(viewBounds) || highlightBounds.contains(viewBounds)){
                if(!selectionSet.contains(each)){
                    selectionSet.add(each);
                }
            }else{
                selectionSet.remove(each);
            }
        }
    }

    public ObservableList<DataFlowView> getSelectionSet() {
        return selectionSet;
    }

    public void moveSelectionOutlineBy(double dx,double dy){
        selectionRect.setX(selectionRect.getX() + dx);
        selectionRect.setY(selectionRect.getY() + dy);
    }

    public void mousePressedOnCanvas(MouseEvent mouseEvent){
        selectionSet.clear();
        Point2D local = backReference.transformedAfterPan(mouseEvent);
        initialX = local.getX();
        initialY = local.getY();
        backReference.getCurrentStructure().group.getChildren().add(highlightRect);
        highlightRect.setX(initialX);
        highlightRect.setY(initialY);
        highlightRect.setWidth(0);
        highlightRect.setHeight(0);
    }

    public void mouseDraggedOnCanvas(MouseEvent mouseEvent){
        Point2D local = backReference.transformedAfterPan(mouseEvent);
        double width = Math.abs(local.getX() - initialX);
        double height = Math.abs(local.getY() - initialY);
        if( local.getX() > initialX ){
            highlightRect.setX(initialX);
        }else{
            highlightRect.setX(local.getX());
        }

        if( local.getY() > initialY ){
            highlightRect.setY(initialY);
        }else{
            highlightRect.setY(local.getY());
        }

        highlightRect.setWidth(width);
        highlightRect.setHeight(height);

    }

    public void mouseReleasedOnCanvas(MouseEvent mouseEvent){
        backReference.getCurrentStructure().group.getChildren().remove(highlightRect);
    }

    /**
     * @return returns a copy of the current selection
     */
    public List<DataFlowView> cloneSelection(){
        return new LinkedList<>(selectionSet);
    }
}
