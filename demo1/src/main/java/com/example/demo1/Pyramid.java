package com.example.demo1;

import javafx.scene.Group;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Pyramid extends Group {

    private static final float PYRAMID_WIDTH = 14;
    private static final float PYRAMID_HEIGHT = 14;
    private static final float PYRAMID_DEPTH = 14;

    private MeshView meshView;
    public Pyramid(Position position) {
        TriangleMesh mesh = new TriangleMesh();

        mesh.getPoints().addAll(
                0,      -PYRAMID_HEIGHT / 2,   0,        // 0
                PYRAMID_WIDTH / 2,  PYRAMID_HEIGHT / 2,    PYRAMID_DEPTH / 2,    // 1
                PYRAMID_WIDTH / 2,  PYRAMID_HEIGHT / 2,    -PYRAMID_DEPTH / 2,   // 2
                -PYRAMID_WIDTH / 2, PYRAMID_HEIGHT / 2,    -PYRAMID_DEPTH / 2,   // 3
                -PYRAMID_WIDTH / 2, PYRAMID_HEIGHT / 2,    PYRAMID_DEPTH / 2     // 4
        );

        mesh.getTexCoords().addAll(
                0.504f, 0.524f,     // 0
                0.701f, 0,          // 1
                0.126f, 0,          // 2
                0,      0.364f,     // 3
                0,      0.608f,     // 4
                0.165f, 1,          // 5
                0.606f, 1,          // 6
                0.575f, 0.420f,     // 7
                0.575f, 0.643f,     // 8
                0.740f, 0.643f,     // 9
                0.740f, 0.420f      // 10
        );

        mesh.getFaces().addAll(
                0, 0, 3, 5, 2, 6, // Front face
                0, 0, 2, 2, 1, 3, // Right face
                0, 0, 1, 1, 4, 2, // Back face
                0, 0, 4, 4, 3, 5, // Left right face
                2, 9, 3, 8, 4, 7, // Bottom face
                2, 9, 4, 7, 1, 10 // Bottom face
        );


        meshView = new MeshView(mesh);


        super.getChildren().addAll(meshView);





        /*Polygon front = new Polygon(0, 0, size / 2, -size / 2, size, 0, size / 2, size / 2);
        front.setFill(Color.RED);

        Polygon back = new Polygon(0, 0, size / 2, -size / 2, size, 0, size / 2, size / 2);
        back.setFill(Color.GREEN);
        back.setTranslateZ(-size / 2);

        Rectangle left = new Rectangle(size / 2, size, Color.BLUE);
        left.setTranslateX(-size / 4);
        left.setRotationAxis(Rotate.Y_AXIS);
        left.setRotate(90);
        left.setTranslateZ(-size / 4);

        Rectangle right = new Rectangle(size / 2, size, Color.BLUE);
        right.setTranslateX(size / 4);
        right.setRotationAxis(Rotate.Y_AXIS);
        right.setRotate(90);
        right.setTranslateZ(-size / 4);

        Rectangle bottom = new Rectangle(size, size, Color.YELLOW);
        bottom.setTranslateY(size / 2);
        bottom.setRotationAxis(Rotate.X_AXIS);
        bottom.setRotate(90);

        this.setTranslateX(position.getX());
        this.setTranslateY(position.getY() - size / 2);
        this.setTranslateZ(position.getZ());



        getChildren().addAll(front, back, left, right, bottom);*/
    }



    public static double getHeight(){
        return sqrt(pow(PYRAMID_HEIGHT*sqrt(3)/2, 2) - pow(PYRAMID_WIDTH/2, 2));
    }

    public void setColors(Color defuse, Color specular){
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(defuse);
        phongMaterial.setSpecularColor(specular);

        meshView.setMaterial(phongMaterial);
    }


}
