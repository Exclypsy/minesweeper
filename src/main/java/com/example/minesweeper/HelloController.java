package com.example.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Random;
// something


public class HelloController {

    @FXML private TextField widthField;
    @FXML private TextField heightField;
    @FXML private TextField minesField;
    @FXML private GridPane board;
    @FXML private Label statusLabel;
    private long startTime;
    private int moveCount = 0;
    private Timeline timer;
    @FXML private Label timerLabel;
    @FXML private Label movesLabel;

    private int width, height, mineCount;
    private GameCell[][] cells;
    private boolean gameOver;


    // --- Helper methods for Cell interaction ---
    public boolean isGameOver() {
        return gameOver;
    }

    public void incrementMoveCount() {
        moveCount++;
        movesLabel.setText("Ťahy: " + moveCount);
    }

    public void revealNeighbors(int x, int y) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
                    ((Cell) cells[nx][ny]).reveal();
                }
            }
        }
    }

    @FXML
    protected void startGame() {
        try {
            width = Integer.parseInt(widthField.getText());
            height = Integer.parseInt(heightField.getText());
            mineCount = Integer.parseInt(minesField.getText());

            if (width <= 0 || height <= 0) {
                statusLabel.setText("Šírka a výška musia byť väčšie ako 0.");
                return;
            }
            if (mineCount <= 0 || mineCount >= width * height) {
                statusLabel.setText("Počet mín musí byť v rozmedzí 1 až " + (width * height - 1));
                return;
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Zadaj platné čísla.");
            return;
        }

        gameOver = false;
        statusLabel.setText("Hra beží...");

        moveCount = 0;
        startTime = System.currentTimeMillis();
        timerLabel.setText("Čas: 00:00");
        movesLabel.setText("Ťahy: 0");
        if (timer != null) timer.stop();
        timer = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            long totalSeconds = (System.currentTimeMillis() - startTime) / 1000;
            long minutes = totalSeconds / 60;
            long seconds = totalSeconds % 60;
            String timeStr = String.format("%02d:%02d", minutes, seconds);
            timerLabel.setText("Čas: " + timeStr);
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        board.getChildren().clear();
        board.getColumnConstraints().clear();
        board.getRowConstraints().clear();

        cells = new GameCell[width][height];

        for (int i = 0; i < width; i++) {
            board.getColumnConstraints().add(new ColumnConstraints(30));
        }
        for (int i = 0; i < height; i++) {
            board.getRowConstraints().add(new RowConstraints(30));
        }

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
                if (cell.isMine) {
                    cell.neighborMines = -1;
                    continue;
                }
                int count = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
                            if (((Cell) cells[nx][ny]).isMine) count++;
                        }
                    }
                }
                cell.neighborMines = count;
            }
        }
    }

    public void gameOver(boolean won) {
        if (timer != null) timer.stop();
        gameOver = true;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                GameCell c = cells[x][y];
                if (c instanceof Cell cell && cell.isMine) {
                    c.setText("💣");
                    c.setDisable(true);
                    c.setStyle("-fx-background-color: red;");
                } else {
                    c.setDisable(true);
                }
            }
        }

        if (won) {
            statusLabel.setText("Vyhral si! 🎉");
        } else {
            statusLabel.setText("Prehral si! 💥");
        }
    }

    public void checkWin() {
        int revealedCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cells[x][y] instanceof Cell cell && cell.revealed) revealedCount++;
            }
        }
        if (revealedCount == width * height - mineCount) {
            gameOver(true);
        }
    }
}