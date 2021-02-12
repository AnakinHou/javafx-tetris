package org.game.tetris.demo;

import java.awt.Point;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ShapeDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane view = new Pane();

        Rectangle box = new Rectangle(300d, 580d);
        box.setStroke(Color.BLACK);
        box.setLayoutX(10);
        box.setLayoutY(10);
        box.setFill(Color.GREENYELLOW);
        view.getChildren().add(box);


        ArrayList<Point> coordinates = new ArrayList<Point>(4);
        coordinates.add(new Point(4, 3));
        coordinates.add(new Point(4, 2));
        coordinates.add(new Point(4, 1));
        coordinates.add(new Point(4, 0));

        Canvas canvas = new Canvas(120, 120);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // gc.fillRect(0, 0, 120, 120);
        gc.setFill(Color.RED);
        // for (Point p : coordinates) {
        // gc.fillRect((int) (p.x - 1) * 30, (int) (p.y) * 30, 30, 30);
        // }

        gc.fillRect((4 - 1) * 30, 3 * 30, 30, 30);

        gc.setFill(Color.BLUE);
        Point p = new Point(20, 50);
        gc.fillRect(p.getX(), p.getY(), 30, 30);

        // gc.setStroke(Color.BLACK);
        // gc.setLineWidth(1.5);


        //
        // for (int i = 0; i < 4; i++) {
        // gc.strokeLine(i * 30, 0, i * 30, 120);
        // }
        //
        // for (int i = 0; i < 4; i++) {
        // gc.strokeLine(0, i * 30, 120, i * 30);
        // }


        view.getChildren().add(canvas);

        Scene scene = new Scene(view, 420d, 600d);
        primaryStage.setTitle("Tetris Shape demo");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                Platform.runLater(() -> {
                    // gameController.handleKeyCode(keyEvent.getCode());
                    KeyCode code = keyEvent.getCode();
                    if (code.equals(KeyCode.LEFT)) {
                        System.out.println("LEFT");
                        gc.clearRect(p.getX(), p.getY(), 30, 30);
                        p.x = p.x - 2;
                        gc.fillRect(p.getX(), p.getY(), 30, 30);
                        // gc.setFill(Color.BLUE);
//                        gc.moveTo(p.getX(), p.getY());
                        // gc.translate(p.getX(), p.getY());
                        // gc.transform(xform);
//                        canvas.setTranslateX(p.getX());
//                         gc.save();
//                         gc.setGlobalAlpha(canvas.getAlpha());
//                         Rotate r = new Rotate(canvas.getRotate(), getX() + getWidth() / 2, getY() + getHeight() / 2);
//                         gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
//                         draw(gc);
//                         gc.restore();
                         
                    } else if (code.equals(KeyCode.RIGHT)) {
                        gc.clearRect(p.getX(), p.getY(), 30, 30);
                        p.x = p.x + 2;
                        gc.fillRect(p.getX(), p.getY(), 30, 30);
                        System.out.println("RIGHT");
                    } else if (code.equals(KeyCode.UP)) {
                        System.out.println("UP");
                    } else if (code.equals(KeyCode.DOWN)) {
                        System.out.println("DOWN");
                    } else {
                        System.out.println(".......");
                    }
                });
            }
        });
    }

}
