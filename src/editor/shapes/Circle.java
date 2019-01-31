package editor.shapes;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class Circle extends MeshView {
    private double radius;
    public Circle(double radius) {
        this.radius = radius;
        recomputeMesh();
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        recomputeMesh();
    }

    private void recomputeMesh(){

        // allocate the arrays (extra point for center)
        float[] points = new float[361*3];
        float[] texCoordinates = new float[361*2];
        int[] faces = new int[361*3*2];

        // create the point for center
        points[0]=0; // center x
        points[1]=0; // center y
        points[2]=0; // center z

        texCoordinates[0] = 0.5f;
        texCoordinates[1] = 0.5f;

        // circumference points
        int facesIndex = 0;
        int elementIndex = 1;
        int pointIndex = 3;
        int texIndex = 2;
        for (int deg = 0; deg < 360; deg++,elementIndex++,pointIndex+=3,texIndex+=2) {

            // vertices
            points[pointIndex] = (float)(Math.cos(Math.toRadians(deg))*radius);
            points[pointIndex + 1] = (float)(Math.sin(Math.toRadians(deg))*radius);
            points[pointIndex + 2] = 0; // z axis is irrelevant in 2d

            // texture coordinates
            texCoordinates[texIndex] = 0.5f + (float)Math.cos(Math.toRadians(deg));
            texCoordinates[texIndex + 1] = 0.5f + (float)Math.sin(Math.toRadians(deg));

            // faces
            if(deg%2==0){//every even number of times

                // for the previous triangle
                if(deg!=0){
                    faces[facesIndex++] = elementIndex; // vertex
                    faces[facesIndex++] = elementIndex; // texture coordinate
                }

                // for this triangle
                faces[facesIndex++] = elementIndex; // vertex
                faces[facesIndex++] = elementIndex; // texture coordinate

                faces[facesIndex++] = 0; // center's vertex
                faces[facesIndex++] = 0; // center's texture coordinate
            }else{
                // for the previous triangle
                faces[facesIndex++] = elementIndex; // vertex
                faces[facesIndex++] = elementIndex; // texture coordinate

                // for this triangle
                faces[facesIndex++] = elementIndex; // vertex
                faces[facesIndex++] = elementIndex; // texture coordinate

                faces[facesIndex++] = 0; // center's vertex
                faces[facesIndex++] = 0; // center's texture coordinate
            }


        }

        // for the last triangle we add the final vertex as the first vertex
        // for the previous triangle
        faces[facesIndex++] = 1; // vertex
        faces[facesIndex] = 1; // texture coordinate

        TriangleMesh triangleMesh = new TriangleMesh();
        triangleMesh.getPoints().setAll(points);
        triangleMesh.getTexCoords().setAll(texCoordinates);
        triangleMesh.getFaces().setAll(faces);
        this.setMesh(triangleMesh);
    }
}
