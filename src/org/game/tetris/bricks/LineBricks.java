package org.game.tetris.bricks;

import java.awt.Point;
import javafx.scene.paint.Color;

public class LineBricks implements IBrick {

    private Point[] preCoordinates;

    private Point[] coordinates;
    public Color brickColor;

    private int rotateState = 1;

    public LineBricks(Color color) {
        this.brickColor = color;
        coordinates =
                new Point[] {new Point(3, 0), new Point(4, 0), new Point(5, 0), new Point(6, 0)};
        preCoordinates =
                new Point[] {new Point(3, 0), new Point(4, 0), new Point(5, 0), new Point(6, 0)};
    }

    @Override
    public Point[] getCoordinates() {
        return coordinates;
    }

    @Override
    public Color getColor() {
        return brickColor;
    }

    @Override
    public boolean goLeft(boolean[][] board) {
        for (int i = 0; i < 4; i++) {
            if (coordinates[i].x <= 0) {
                return false;
            }
            if (board[coordinates[i].x - 1][coordinates[i].y]) {
                return false;
            }
        }
        for (int i = 0; i < 4; i++) {
            preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            coordinates[i].move(coordinates[i].x - 1, coordinates[i].y);
        }
        return true;
    }

    @Override
    public boolean goRight(boolean[][] board) {
        for (int i = 0; i < 4; i++) {
            if (coordinates[i].x >= board.length - 1) {
                return false;
            }
            if (board[coordinates[i].x + 1][coordinates[i].y]) {
                return false;
            }
        }
        for (int i = 0; i < 4; i++) {
            preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            coordinates[i].move(coordinates[i].x + 1, coordinates[i].y);
        }
        return true;
    }

    @Override
    public boolean goDown(boolean[][] board) {
        // System.out.println(" ****** goDown 111");
        for (int i = 0; i < 4; i++) {
            if (coordinates[i].y >= 20) {
                return false;
            }
        }
        for (int i = 0; i < 4; i++) {
            preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            coordinates[i].move(coordinates[i].x, coordinates[i].y + 1);
        }
        return true;
        // System.out.println(" ****** goDown 333333333");
    }

    @Override
    public boolean rotate(boolean[][] board) {

        for (int i = 0; i < 4; i++) {
            if (coordinates[i].y < 2 || coordinates[i].y >= 19 || coordinates[i].x >= 9
                    || coordinates[i].x <= 0) {
                return false;
            }
        }
        if (rotateState == 1) {
            // row to column
            if (board[coordinates[3].x][coordinates[3].y + 1]
                    || board[coordinates[0].x][coordinates[0].y - 2]) {
                return false;
            }
            for (int i = 0; i < 4; i++) {
                preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            }
            coordinates[0].move(preCoordinates[2].x, preCoordinates[2].y - 2);
            coordinates[1].move(preCoordinates[2].x, preCoordinates[2].y - 1);
            coordinates[2].move(preCoordinates[2].x, preCoordinates[2].y);
            coordinates[3].move(preCoordinates[2].x, preCoordinates[2].y + 1);
            rotateState = 2;
            return true;
        } else {
            // column to row
            if (board[coordinates[3].x + 1][coordinates[3].y]
                    || board[coordinates[0].x - 2][coordinates[0].y]) {
                return false;
            }

            for (int i = 0; i < 4; i++) {
                preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            }
            coordinates[0].move(preCoordinates[2].x - 2, preCoordinates[2].y);
            coordinates[1].move(preCoordinates[2].x - 1, preCoordinates[2].y);
            coordinates[2].move(preCoordinates[2].x, preCoordinates[2].y);
            coordinates[3].move(preCoordinates[2].x + 1, preCoordinates[2].y);
            rotateState = 1;
            return true;
        }
    }

    @Override
    public Point[] getPreviousCoordinates() {
        return preCoordinates;
    }


}
