package com.example.minesweeper;

import javafx.scene.control.Button;

public abstract class GameCell extends Button {
    public abstract void reveal();
    public abstract void toggleFlag();
}
