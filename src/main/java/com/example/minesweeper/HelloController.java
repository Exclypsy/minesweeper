package com.example.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

import java.util.Random;
// something
public class HelloController {

    @FXML private TextField widthField;
    @FXML private TextField heightField;
    @FXML private TextField minesField;
    @FXML private GridPane board;
    @FXML private Label statusLabel;

    private int width, height, mineCount;
    private Cell[][] cells;
    private boolean gameOver;

    private class Cell extends Button {
        boolean isMine = false;
        boolean revealed = false;
        boolean flagged = false;
        int neighborMines = 0;
        int x, y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
            setMinSize(30, 30);
            setMaxSize(30, 30);
            setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

            setOnMouseClicked(e -> {
                if (gameOver) return;

                MouseButton button = e.getButton();
                if (button == MouseButton.PRIMARY) {
                    if (!flagged) {
                        System.out.println("Reveal clicked at " + x + "," + y);
                        reveal();
                    }
                } else if (button == MouseButton.SECONDARY) {
                    System.out.println("Flag toggled at " + x + "," + y);
                    toggleFlag();
                }
            });
        }

        void reveal() {
            if (revealed || flagged) return;

            revealed = true;
            setDisable(true);

            if (isMine) {
                setText("💣");
                setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 14;");
                gameOver(false);
            } else {
                if (neighborMines > 0) {
                    setText(String.valueOf(neighborMines));
                    setTextFill(getColorForNumber(neighborMines));
                } else {
                    setText("");
                    // Odhal okolie (rekurzívne)
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            int nx = x + dx;
                            int ny = y + dy;
                            if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
                                cells[nx][ny].reveal();
                            }
                        }
                    }
                }
                checkWin();
            }
        }

        void toggleFlag() {
            if (revealed) return;

            flagged = !flagged;
            setText(flagged ? "🚩" : "");
        }
    }

    private Color getColorForNumber(int n) {
        return switch (n) {
            case 1 -> Color.BLUE;
            case 2 -> Color.GREEN;
            case 3 -> Color.RED;
            case 4 -> Color.DARKBLUE;
            case 5 -> Color.DARKRED;
            case 6 -> Color.TURQUOISE;
            case 7 -> Color.BLACK;
            case 8 -> Color.GRAY;
            default -> Color.BLACK;
        };
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

        board.getChildren().clear();
        board.getColumnConstraints().clear();
        board.getRowConstraints().clear();

        cells = new Cell[width][height];

        for (int i = 0; i < width; i++) {
            board.getColumnConstraints().add(new ColumnConstraints(30));
        }
        for (int i = 0; i < height; i++) {
            board.getRowConstraints().add(new RowConstraints(30));
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell(x, y);
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
            if (!cells[x][y].isMine) {
                cells[x][y].isMine = true;
                placed++;
            }
        }
    }

    private void calculateNeighborMines() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cells[x][y].isMine) {
                    cells[x][y].neighborMines = -1;
                    continue;
                }
                int count = 0;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (nx >= 0 && ny >= 0 && nx < width && ny < height) {
                            if (cells[nx][ny].isMine) count++;
                        }
                    }
                }
                cells[x][y].neighborMines = count;
            }
        }
    }

    private void gameOver(boolean won) {
        gameOver = true;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Cell c = cells[x][y];
                if (c.isMine) {
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

    private void checkWin() {
        int revealedCount = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (cells[x][y].revealed) revealedCount++;
            }
        }
        if (revealedCount == width * height - mineCount) {
            gameOver(true);
        }
    }
}