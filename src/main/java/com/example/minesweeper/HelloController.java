package com.example.minesweeper;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class HelloController {

    @FXML private TextField widthField;
    @FXML private TextField heightField;
    @FXML private TextField minesField;
    @FXML private GridPane board;
    @FXML private Label statusLabel;
    @FXML private Label timerLabel;
    @FXML private Label movesLabel;

    private Timeline timer;
    private long startTime;

    private final GameLogic gameLogic = new GameLogic(this);

    @FXML
    protected void startGame() {
        try {
            int width = Integer.parseInt(widthField.getText());
            int height = Integer.parseInt(heightField.getText());
            int mines = Integer.parseInt(minesField.getText());

            if (width <= 0 || height <= 0 || mines <= 0 || mines >= width * height) {
                statusLabel.setText("Zlé parametre!");
                return;
            }

            resetTimer();
            movesLabel.setText("Ťahy: 0");
            timerLabel.setText("Čas: 00:00");

            if (timer != null) timer.stop();
            timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                long total = (System.currentTimeMillis() - startTime) / 1000;
                timerLabel.setText("Čas: %02d:%02d".formatted(total / 60, total % 60));
            }));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

            gameLogic.startNewGame(width, height, mines);
        } catch (NumberFormatException e) {
            statusLabel.setText("Zadaj čísla!");
        }
    }

    public void stopTimer() {
        if (timer != null) timer.stop();
    }

    public void resetTimer() {
        startTime = System.currentTimeMillis();
    }

    public GridPane getBoard() {
        return board;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Label getMovesLabel() {
        return movesLabel;
    }
}