package org.game.tetris.bricks;

import java.awt.Point;
import javafx.scene.paint.Color;

public interface IBrick {
    public Point[] getPreviousCoordinates();

    public Point[] getCoordinates();

    public Color getColor();

    public boolean goLeft(boolean[][] board);

    public boolean goRight(boolean[][] board);

    public boolean goDown(boolean[][] board);

    public boolean rotate(boolean[][] board);

}
