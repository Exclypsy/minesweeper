package com.example.minesweeper;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell extends GameCell {
    public boolean isMine = false;
    public boolean revealed = false;
    public boolean flagged = false;
    public int neighborMines = 0;
    public int x, y;
    private final GameLogic logic;

    public Cell(int x, int y, GameLogic logic) {
        this.x = x;
        this.y = y;
        this.logic = logic;

        setMinSize(30, 30);
        setMaxSize(30, 30);
        setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        setOnMouseClicked(e -> {
            if (logic.isGameOver()) return;

            MouseButton button = e.getButton();
            if (button == MouseButton.PRIMARY) {
                if (!flagged) {
                    logic.incrementMoveCount();
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
            logic.gameOver(false);
        } else {
            if (neighborMines > 0) {
                setText(String.valueOf(neighborMines));
                setTextFill(getColorForNumber(neighborMines));
            } else {
                setText("");
                logic.revealNeighbors(x, y);
            }
            logic.checkWin();
        }
    }

    @Override
    public void toggleFlag() {
        if (revealed) return;

        flagged = !flagged;

        if (flagged) {
            var url = getClass().getResource("/com/example/minesweeper/assets/flag.png");
            if (url != null) {
                Image flagImage = new Image(url.toString());
                ImageView view = new ImageView(flagImage);
                view.setFitWidth(20);
                view.setFitHeight(20);
                setGraphic(view);
            } else {
                setText("🚩");
            }
        } else {
            setGraphic(null);
            setText("");
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
}