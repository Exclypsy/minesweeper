package com.example.minesweeper;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class GameCell extends Button {
    public abstract void reveal();
    public abstract void toggleFlag();
}
