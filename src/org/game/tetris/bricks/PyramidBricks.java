package org.game.tetris.bricks;

import java.awt.Point;
import javafx.scene.paint.Color;

public class PyramidBricks implements IBrick {

    private Point[] preCoordinates;

    private Point[] coordinates;
    public Color brickColor;
    /// 1--2--3--4
    private int rotateState = 1;

    public PyramidBricks(Color color) {
        this.brickColor = color;
        coordinates =
                new Point[] {new Point(3, 1), new Point(4, 1), new Point(4, 0), new Point(5, 1)};
        preCoordinates =
                new Point[] {new Point(3, 1), new Point(4, 1), new Point(4, 0), new Point(5, 1)};
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
        for (int i = 0; i < 4; i++) {
            if (coordinates[i].y >= board[0].length) {
                return false;
            }
            if (board[coordinates[i].x][coordinates[i].y]) {
                return false;
            }
        }
        for (int i = 0; i < 4; i++) {
            preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            coordinates[i].move(coordinates[i].x, coordinates[i].y + 1);
        }
        return true;
    }

    @Override
    public boolean rotate(boolean[][] board) {
        for (int i = 0; i < 4; i++) {
            if (coordinates[i].y < 2 || coordinates[i].y >= 19 || coordinates[i].x >= 9
                    || coordinates[i].x <= 0) {
                return false;
            }
        }
        // 边界检测
        // 。。。。。
        if (rotateState == 1) {
            for (int i = 0; i < 4; i++) {
                preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            }
            coordinates[0].move(preCoordinates[1].x, preCoordinates[1].y - 1);
            coordinates[1].move(preCoordinates[1].x, preCoordinates[1].y);
            coordinates[2].move(preCoordinates[1].x + 1, preCoordinates[1].y);
            coordinates[3].move(preCoordinates[1].x, preCoordinates[1].y + 1);
            rotateState = 2;

            return true;
        } else if (rotateState == 2) {
            for (int i = 0; i < 4; i++) {
                preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            }
            coordinates[0].move(preCoordinates[1].x + 1, preCoordinates[1].y);
            coordinates[1].move(preCoordinates[1].x, preCoordinates[1].y);
            coordinates[2].move(preCoordinates[1].x, preCoordinates[1].y + 1);
            coordinates[3].move(preCoordinates[1].x - 1, preCoordinates[1].y);

            rotateState = 3;
            return true;
        } else if (rotateState == 3) {
            for (int i = 0; i < 4; i++) {
                preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            }

            coordinates[0].move(preCoordinates[1].x, preCoordinates[1].y + 1);
            coordinates[1].move(preCoordinates[1].x, preCoordinates[1].y);
            coordinates[2].move(preCoordinates[1].x - 1, preCoordinates[1].y);
            coordinates[3].move(preCoordinates[1].x, preCoordinates[1].y - 1);

            rotateState = 4;
            return true;
        } else {
            for (int i = 0; i < 4; i++) {
                preCoordinates[i].move(coordinates[i].x, coordinates[i].y);
            }
            coordinates[0].move(preCoordinates[1].x - 1, preCoordinates[1].y);
            coordinates[1].move(preCoordinates[1].x, preCoordinates[1].y);
            coordinates[2].move(preCoordinates[1].x, preCoordinates[1].y - 1);
            coordinates[3].move(preCoordinates[1].x + 1, preCoordinates[1].y);

            rotateState = 1;
            return true;
        }
    }

    @Override
    public Point[] getPreviousCoordinates() {
        return preCoordinates;
    }
}
