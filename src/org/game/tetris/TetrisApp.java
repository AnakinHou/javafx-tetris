package org.game.tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TetrisApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);

        GameBoard gameBoard = new GameBoard();

        int width = GameConfig.brickSideLength * GameConfig.padColumns + 10;
        int height = GameConfig.brickSideLength * GameConfig.padRows + 10;
        Canvas gameCanvas = new Canvas(width, height);
        gameBoard.getChildren().add(gameCanvas);

        Scene scene = new Scene(gameBoard, 460d, 630d);
        // primaryStage.centerOnScreen();
        // primaryStage.setY(primaryStage.getY() * 3f / 2f);
        primaryStage.setTitle("My Tetris Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        GameField gf = new GameField(gameCanvas.getGraphicsContext2D(),gameBoard);
        gf.playGame();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
               // Platform.runLater(() -> {
                    gf.gamepad(keyEvent.getCode());
               // });
            }
        });
    }


}
