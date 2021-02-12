package org.game.tetris;

import java.awt.Point;
import java.util.Random;
import org.game.tetris.bricks.IBrick;
import org.game.tetris.bricks.LineBricks;
import org.game.tetris.bricks.PyramidBricks;
import javafx.concurrent.Task;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameField {

    // private Task<Void> gameTask;
    private IBrick brick;
    public Color[] colors;
    public int score;
    private int speed = 500;
    private GraphicsContext brickGC;
    private boolean[][] board;
    private Color[][] pieceColor;

    private GameBoard gameBoard;

    // private Thread gameThread = null;
    // 无意义
    private final Object lock = new Object();

    // 标志线程阻塞情况
    private boolean pause = false;

    public GameField(GraphicsContext gc, GameBoard gameBoard) {
        this.score = 0;
        this.colors = new Color[] {Color.rgb(248, 121, 41), Color.rgb(11, 165, 223),
                Color.rgb(192, 58, 180), Color.rgb(135, 212, 47), Color.rgb(215, 23, 53),
                Color.rgb(44, 87, 220), Color.rgb(251, 187, 49)};
        this.brick = generateBrick();
        this.brickGC = gc;
        this.board = new boolean[GameConfig.padColumns][GameConfig.padRows];
        this.pieceColor = new Color[GameConfig.padColumns][GameConfig.padRows];
        this.gameBoard = gameBoard;
    }



    /**
     * 设置线程是否阻塞
     */
    private void pauseGame() {
        this.pause = true;
    }

    /**
     * 调用该方法实现恢复线程的运行
     */
    private void resumeGame() {
        this.pause = false;
        synchronized (lock) {
            // 唤醒线程
            lock.notify();
        }
    }

    private void onPause() {
        synchronized (lock) {
            try {
                // 线程 等待/阻塞
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public IBrick generateBrick() {
        Random rand = new Random();
        int randomColorIndex = rand.nextInt(colors.length);
        Color randColor = colors[randomColorIndex];

        int randomBrickIndex = rand.nextInt(7);
        if (randomBrickIndex == 1) {
            return new LineBricks(randColor);
            // } else if (randomBrickIndex == 2) {
            //
            // } else if (randomBrickIndex == 3) {
            //
            // } else if (randomBrickIndex == 4) {
            //
            // } else if (randomBrickIndex == 5) {
            //
            // } else if (randomBrickIndex == 6) {
            //
        } else {
            return new PyramidBricks(randColor);
        }

    }

    public void gamepad(KeyCode code) {

        if (code.equals(KeyCode.LEFT)) {
            // System.out.println("-----<<<<< Left");
            if (brick.goLeft(board)) {
                updateBrick();
            }
            speed = 500;
        } else if (code.equals(KeyCode.RIGHT)) {
            // System.out.println("----->>>>>> right");
            if (brick.goRight(board)) {
                // erasePreviousBrick();
                updateBrick();
            }
            speed = 500;
        } else if (code.equals(KeyCode.UP)) {
            // System.out.println("----- ^^^^^^^ up");
            if (brick.rotate(board)) {
                // erasePreviousBrick();
                updateBrick();
            }
            speed = 500;
        } else if (code.equals(KeyCode.DOWN)) {
            // System.out.println("-----vvvvvvvvv down");
            speed = 200;
        } else if (code.equals(KeyCode.SPACE)) {
            if (pause) {
                // gameThread.re
                this.resumeGame();
            } else {
                this.pauseGame();
            }

        } else {

        }
    }

    private void updateBrick() {

        erasePreviousBrick();

        brickGC.setFill(brick.getColor());
        brickGC.setStroke(Color.BLACK);
        // brickGC.sets.setStrokeWidth(2d);
        brickGC.setLineWidth(1);
        for (Point p : brick.getCoordinates()) {
            brickGC.fillRect(p.x * 30 + 10, p.y * 30 + 10, 30, 30);
            brickGC.setStroke(Color.BLACK);
            brickGC.strokeRect(p.x * 30 + 10, p.y * 30 + 10, 30, 30);
        }
    }

    public void erasePreviousBrick() {
        for (Point p : brick.getPreviousCoordinates()) {
            brickGC.clearRect(p.x * 30 + 10, p.y * 30 + 10, 31, 30);
        }
    }

    public void playGame() {
        Task<Void> gameTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                IBrick nextBrick = generateBrick();
                while (!isGameOver()) {
                    System.out.println("=========== new round");
                    if (pause) {
                        // 线程 阻塞/等待
                        onPause();
                    }
                    gameBoard.updateNextBrickFigure(nextBrick);
                    // brick is null or brick is stopped
                    boolean collision = checkCollision(brick);
                    // System.out.println("*********isStop: " + collision);
                    if (collision) {
                        recordBoardCoordinates(brick);
                        int score = calculateScore();
                        if (score > 0) {
                            gameBoard.addScore(score);
                        }
                        brick = nextBrick;
                        nextBrick = generateBrick();
                        speed = 500;
                    }
                    // Platform.runLater(() -> {
                    updateBrick();
                    // });
                    try {
                        java.util.concurrent.TimeUnit.MILLISECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    brick.goDown(board);
                }
                return null;
            }
        };
        Thread gameThread = new Thread(gameTask);
        gameThread.start();
    }

    private boolean checkCollision(IBrick brick) {
        for (Point p : brick.getCoordinates()) {
            if (p.y >= 20) {
                brick = null;
                return true;
            }
            if (board[p.x][p.y]) {
                brick = null;
                return true;
            }
        }
        return false;
    }


    private void recordBoardCoordinates(IBrick brick) {
        brickGC.setFill(Color.BLACK);
        for (Point p : brick.getPreviousCoordinates()) {
            // brickGC.fillText("1", p.x * GameConfig.brickSideLength + 16,
            // p.y * GameConfig.brickSideLength + 24);
            board[p.x][p.y] = true;
            pieceColor[p.x][p.y] = brick.getColor();
            // System.out.println("======== recordBoardCoordinates *********** x:" + p.x + " y:" +
            // p.y
            // + " board[p.x][p.y] " + board[p.x][p.y]);
        }
    }

    private boolean isGameOver() {
        if (board[3][2] || board[4][2] || board[5][2]) {
            System.out.println("=############### Game Over");
            return true;
        }
        return false;
    }


    private int calculateScore() {
        int totalScore = 0;
        for (int i = GameConfig.padRows - 1; i > 1; i--) {
            boolean rowFull = true;
            for (int j = 0; j < GameConfig.padColumns; j++) {
                if (board[j][i] == false) {
                    rowFull = false;
                    break;
                }
            }
            if (rowFull) {
                brickGC.clearRect(10, i * 30 + 10, 30 * GameConfig.padColumns + 1, 30);

                brickGC.setStroke(Color.BLACK);
                brickGC.setLineWidth(1);

                for (int k = i - 1; k > 1; k--) {
                    for (int l = 0; l < GameConfig.padColumns; l++) {
                        board[l][k + 1] = board[l][k];
                        pieceColor[l][k + 1] = pieceColor[l][k];

                        if (board[l][k + 1]) {
                            brickGC.setFill(pieceColor[l][k + 1]);
                            brickGC.fillRect(l * 30 + 10, (k + 1) * 30 + 10, 30, 30);
                            brickGC.strokeRect(l * 30 + 10, (k + 1) * 30 + 10, 30, 30);
                            brickGC.clearRect(l * 30 + 10, k * 30 + 10, 31, 30);
                        }
                    }
                }
                totalScore += 100;
                i++;
            }
        }
        System.out.println("************score =" + totalScore);
        return totalScore;
    }
}
