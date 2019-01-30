package app;

import editor.shapes.*;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Cylinder;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        // Create a Cylinder
        Cylinder cylinder = new Cylinder(40, 120);
        cylinder.setTranslateX(500);
        cylinder.setTranslateY(200);
        cylinder.setTranslateZ(400);

        Rectangle rectangle = new Rectangle(100,40);
        rectangle.setTranslateX(300);
        rectangle.setTranslateY(200);
        rectangle.setTranslateZ(400);

        Triangle triangle = new Triangle(80,60);
        triangle.setTranslateX(500);
        triangle.setTranslateY(500);
        triangle.setTranslateZ(400);

        DirectedRectangle directedRectangle = new DirectedRectangle(80,60);
        directedRectangle.setTranslateX(200);
        directedRectangle.setTranslateY(400);
        directedRectangle.setTranslateZ(400);

        Line line = new Line(new Point2D(222,522),new Point2D(432,562));
        line.setTranslateX(0);
        line.setTranslateY(0);
        line.setTranslateZ(400);

        Circle circle = new Circle(50);
        circle.setTranslateX(350);
        circle.setTranslateY(350);
        circle.setTranslateZ(400);

        // Add the Shapes and the Light to the Group
        Group root = new Group(cylinder,rectangle,triangle,directedRectangle,line,circle);

//        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));


        Scene scene = new Scene(root, 800, 600);

        // Create a Camera to view the 3D Shapes
        ParallelCamera camera = new ParallelCamera();
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(100);
        scene.setCamera(camera);
        scene.setFill(Color.rgb(10, 10, 40));

        primaryStage.setTitle("Visual Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
