package editor.shapes;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class DirectedRectangle extends MeshView {
    public DirectedRectangle(float length,float height) {
        float[] points = {
                -length/2, height/2, 0,
                -length/2, -height/2, 0,
                length/2, height/2, 0,
                length/2, -height/2, 0,
                (length/2)+(length/4), 0, 0
        };

        float[] texCoordinates = {
                0, 0,
                0, 1,
                0.8f, 0,
                0.8f, 1,
                1, 0.5f

        };

        int[] faces = {
                2,2, 1,1, 0,0,
                2,2, 3,3, 1,1,
                4,4, 3,3, 2,2
        };

        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoordinates);
        triangleMesh.getFaces().setAll(faces);
        this.setMesh(triangleMesh);
    }
}
