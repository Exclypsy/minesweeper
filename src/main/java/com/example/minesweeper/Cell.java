package com.example.minesweeper;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Cell extends GameCell {
    public boolean isMine = false;
    public boolean revealed = false;
    public boolean flagged = false;
    public int neighborMines = 0;
    public int x, y;
    private final HelloController controller;

    public Cell(int x, int y, HelloController controller) {
        this.x = x;
        this.y = y;
        this.controller = controller;

        setMinSize(30, 30);
        setMaxSize(30, 30);
        setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        setOnMouseClicked(e -> {
            if (controller.isGameOver()) return;

            MouseButton button = e.getButton();
            if (button == MouseButton.PRIMARY) {
                if (!flagged) {
                    controller.incrementMoveCount();
                    reveal();
                }
            } else if (button == MouseButton.SECONDARY) {
                toggleFlag();
            }
        });
    }

    @Override
    public void reveal() {
        if (revealed || flagged) return;

        revealed = true;
        setDisable(true);

        if (isMine) {
            setText("💣");
            setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-font-size: 14;");
            controller.gameOver(false);
        } else {
            if (neighborMines > 0) {
                setText(String.valueOf(neighborMines));
                setTextFill(getColorForNumber(neighborMines));
            } else {
                setText("");
                controller.revealNeighbors(x, y);
            }
            controller.checkWin();
        }
    }

    @Override
    public void toggleFlag() {
        if (revealed) return;

        flagged = !flagged;
        setText(flagged ? "🚩" : "");
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
}
