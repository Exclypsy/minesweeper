package com.example.minesweeper;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Random;

public class GameLogic {

    private final HelloController controller;
    private int width, height, mineCount;
    private int moveCount = 0;
    private boolean gameOver = false;
    private GameCell[][] cells;

    public GameLogic(HelloController controller) {
        this.controller = controller;
    }

    public void startNewGame(int width, int height, int mineCount) {
        this.width = width;
        this.height = height;
        this.mineCount = mineCount;
        this.gameOver = false;
        this.moveCount = 0;

        GridPane board = controller.getBoard();
        board.getChildren().clear();
        board.getColumnConstraints().clear();
        board.getRowConstraints().clear();
        controller.getStatusLabel().setText("Hra beží...");

        cells = new GameCell[width][height];

        for (int i = 0; i < width; i++)
            board.getColumnConstraints().add(new ColumnConstraints(30));
        for (int i = 0; i < height; i++)
            board.getRowConstraints().add(new RowConstraints(30));

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell(x, y, this);
                cells[x][y] = cell;
                board.add(cell, x, y);
            }
        }

        placeMines();
        calculateNeighborMines();
    }

    private void placeMines() {
        Random rand = new Random();
        int placed = 0;
        while (placed < mineCount) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            Cell cell = (Cell) cells[x][y];
            if (!cell.isMine) {
                cell.isMine = true;
                placed++;
            }
        }
    }

    private void calculateNeighborMines() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell cell = (Cell) cells[x][y];
                if (cell.isMine) continue;

                int count = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx, ny = y + dy;
                        if (nx >= 0 && ny >= 0 && nx < width && ny < height && ((Cell) cells[nx][ny]).isMine)
                            count++;
                    }
                }
                cell.neighborMines = count;
            }
        }
    }

    public void incrementMoveCount() {
        moveCount++;
        controller.getMovesLabel().setText("Ťahy: " + moveCount);
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void revealNeighbors(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
                    ((Cell) cells[nx][ny]).reveal();
                }
            }
        }
    }

    public void checkWin() {
        int revealed = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (((Cell) cells[x][y]).revealed)
                    revealed++;

        if (revealed == width * height - mineCount)
            gameOver(true);
    }

    public void gameOver(boolean won) {
        controller.stopTimer();
        gameOver = true;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                GameCell c = cells[x][y];
                if (c instanceof Cell cell && cell.isMine) {
                    var url = getClass().getResource("/com/example/minesweeper/assets/bomb.png");
                    if (url != null) {
                        Image img = new Image(url.toString());
                        ImageView view = new ImageView(img);
                        view.setFitWidth(20);
                        view.setFitHeight(20);
                        c.setGraphic(view);
                    } else {
                        c.setText("💣");
                    }
                }
                c.setDisable(true);
            }
        }

        controller.getStatusLabel().setText(won ? "Vyhral si! 🎉" : "Prehral si! 💥");
    }
}