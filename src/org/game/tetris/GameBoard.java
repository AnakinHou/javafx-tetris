package org.game.tetris;

import java.awt.Point;
import org.game.tetris.bricks.IBrick;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GameBoard extends Pane {

    private Text score;
    private GraphicsContext gc;

    public GameBoard() {
        int brickSideLength = GameConfig.brickSideLength;
        int padRows = GameConfig.padRows;
        int padColumns = GameConfig.padColumns;

        Rectangle rect = new Rectangle(brickSideLength * padColumns, brickSideLength * padRows);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(2d);
        rect.setLayoutX(10);
        rect.setLayoutY(10);
        rect.setFill(Color.LIGHTGREEN);

        Canvas canvas =
                new Canvas(brickSideLength * padColumns + 210, brickSideLength * padRows + 30);
        gc = canvas.getGraphicsContext2D();

        // gc.setStroke(Color.RED);
        gc.setStroke(Color.BLUE);
        // gc.setLineWidth(1.5);
        for (int i = 1; i < padColumns; i++) {
            gc.strokeLine(i * brickSideLength + 10, 10, i * brickSideLength + 10,
                    brickSideLength * padRows + 10);

            gc.fillText(i + "", i * brickSideLength + 5, padRows * brickSideLength + 22);
        }

        // gc.setStroke(Color.BLUE);
        for (int i = 1; i < padRows; i++) {
            gc.strokeLine(10, i * brickSideLength + 10, brickSideLength * padColumns + 10,
                    i * brickSideLength + 10);
        }

        // gc.closePath();

        Label scoreLabel = new Label("Score");
        scoreLabel.setLayoutX(330);
        scoreLabel.setLayoutY(100);

        score = new Text("0");
        score.setLayoutX(330);
        score.setLayoutY(130);
        // view.getChildren().add(score);

        // Text nextBrick = new Text("Next Brick: ");
        // nextBrick.setLayoutX(330);
        // nextBrick.setLayoutY(300);
        Label nextBrick = new Label("Next Brick");
        nextBrick.setLayoutX(330);
        nextBrick.setLayoutY(280);
        // view.getChildren().add(nextFigure);

        // Rectangle rectNext = new Rectangle(brickSideLength * 4, brickSideLength * 4);
        // rectNext.setStroke(Color.BLACK);
        // rectNext.setStrokeWidth(2d);
        // rectNext.setLayoutX(320);
        // rectNext.setLayoutY(300);
        // rectNext.setFill(Color.LIGHTGREEN);


        gc.setStroke(Color.RED);
        for (int i = 1; i < 4; i++) {
            gc.strokeLine(320, i * brickSideLength + 300, 4 * brickSideLength + 320,
                    i * brickSideLength + 300);
        }

        for (int i = 1; i < 4; i++) {
            gc.strokeLine(320 + i * brickSideLength, 300, i * brickSideLength + 320,
                    4 * brickSideLength + 300);
        }


        this.getChildren().addAll(rect, canvas, scoreLabel, score, nextBrick);
    }

    public void addScore(int score) {
        if (this.score.getText() == null || "".equals(this.score.getText())) {
            this.score.setText(score + "");
        } else {
            int currentScore = Integer.valueOf(this.score.getText()).intValue() + score;
            this.score.setText(currentScore + "");
        }
    }


    public void updateNextBrickFigure(IBrick brick) {
        gc.clearRect(320, 300, GameConfig.brickSideLength * 4, GameConfig.brickSideLength * 4);
        gc.setFill(brick.getColor());
        for (Point p : brick.getCoordinates()) {
            gc.fillRect((p.x - 3) * GameConfig.brickSideLength + 320,
                    p.y * GameConfig.brickSideLength + 300, GameConfig.brickSideLength,
                    GameConfig.brickSideLength);
            gc.setStroke(Color.BLACK);
            gc.strokeRect((p.x - 3) * GameConfig.brickSideLength + 320,
                    p.y * GameConfig.brickSideLength + 300, GameConfig.brickSideLength,
                    GameConfig.brickSideLength);
        }
    }


}
