package editor.shapes3d;

import javafx.geometry.Point2D;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

/**
 * Creates a Line between two 2d points.
 * The translation of the Line MeshView is advised to be kept at 0,0,z
 */
public class Line extends MeshView {

    private Point2D from;
    private Point2D to;
    private float width=1;

    public Line(Point2D from,Point2D to) {
        this.from = from;
        this.to = to;
        recomputeMesh();
    }

    public Point2D getFrom() {
        return from;
    }

    public void setFrom(Point2D from) {
        this.from = from;
        recomputeMesh();
    }

    public Point2D getTo() {
        return to;
    }

    public void setTo(Point2D to) {
        this.to = to;
        recomputeMesh();
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
        recomputeMesh();
    }

    private void recomputeMesh(){

        Point2D p0 = pointPerpendicularTo(from,true);
        Point2D p1 = pointPerpendicularTo(from,false);
        Point2D p2 = pointPerpendicularTo(to,true);
        Point2D p3 = pointPerpendicularTo(to,false);

        float[] points = {
                (float)p0.getX(), (float)p0.getY(), 0,
                (float)p1.getX(), (float)p1.getY(), 0,
                (float)p2.getX(), (float)p2.getY(), 0,
                (float)p3.getX(), (float)p3.getY(), 0
        };

        float[] texCoordinates = {
                0, 0,
                0, 1,
                1, 0,
                1, 1
        };

        int[] faces = {
                2,2, 1,1, 0,0,
                2,2, 3,3, 1,1
        };

        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoordinates);
        triangleMesh.getFaces().setAll(faces);
        this.setMesh(triangleMesh);
    }

    private Point2D pointPerpendicularTo(Point2D point,boolean above){
        Point2D vector = from.subtract(to);
        Point2D perpendicular = new Point2D(vector.getY(),-vector.getX());
        Point2D unitVector = perpendicular.normalize();
        if(above){
            return point.add(unitVector.multiply(this.width));
        }else{
            return point.subtract(unitVector.multiply(this.width));
        }
    }
}
