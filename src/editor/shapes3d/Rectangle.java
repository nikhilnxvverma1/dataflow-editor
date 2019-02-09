package editor.shapes3d;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Rectangle extends MeshView{

    public Rectangle(int length,int height) {
        float[] points = {
                -length/2, height/2, 0,
                -length/2, -height/2, 0,
                length/2, height/2, 0,
                length/2, -height/2, 0
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
}
