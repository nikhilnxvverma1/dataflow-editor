package editor.shapes;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Triangle extends MeshView {

    public Triangle(int base,int height) {
        float[] points = {
                0, -height/2, 0,
                -base/2, height/2, 0,
                base/2, height/2, 0
        };

        float[] texCoordinates = {
                0.5f, 0,
                0, 1,
                1, 1
        };

        int[] faces = {
                0,0, 1,1, 2,2
        };

        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoordinates);
        triangleMesh.getFaces().setAll(faces);
        this.setMesh(triangleMesh);
    }
}
