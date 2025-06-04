# 🧩 Minesweeper (JavaFX)

A classic **Minesweeper game** implemented in **JavaFX**, supporting customizable board size, mine count, timer, move tracking, and a clean UI.

---

## 🖥️ Features

- Adjustable **board width**, **height**, and **number of mines**
- Mouse interactions:
  - **Left click** to reveal a cell
  - **Right click** to place/remove a flag
- Game timer (MM:SS format)
- Move counter
- Graphical representation of flags and mines (images)
- Object-oriented design with separation of UI and game logic

---

## 📂 Project Structure

src/
├── com/example/minesweeper/
│   ├── HelloApplication.java     # JavaFX entry point
│   ├── HelloController.java      # Handles GUI components and user events
│   ├── GameLogic.java            # Core game logic (mines, victory, defeat, etc.)
│   ├── GameCell.java             # Abstract class for board cells
│   ├── Cell.java                 # Implementation of a game cell (button)
├── resources/
│   ├── hello-view.fxml           # GUI layout file
│   ├── assets/
│   │   ├── bomb.png              # Image shown for mines
│   │   └── flag.png              # Image shown for flags

---

## 🧠 Class Overview

### `HelloApplication`
- Main application class
- Loads the FXML layout and launches the stage

### `HelloController`
- Manages UI components (TextFields, Labels, GridPane)
- Handles user input and resets game state
- Starts/stops the timer

### `GameLogic`
- Separates all core game logic from UI
- Responsible for mine placement, neighbor counting, win/loss checking

### `Cell`
- Represents a single clickable cell
- Handles clicks, reveals, and flags
- Displays numbers, images, or empty state

### `GameCell`
- Abstract base class extending `Button`
- Defines required `reveal()` and `toggleFlag()` methods

---

## 🎮 How to Play

1. Enter board width, height, and number of mines.
2. Click `New Game`.
3. Left-click cells to reveal.
4. Right-click cells to toggle flag.
5. Avoid mines. Win by revealing all non-mine cells.

---

## 🧪 Possible Enhancements

- Difficulty presets (Easy / Medium / Hard)
- Sound effects and animations
- Highscore saving
- Multiplayer version via LAN or online
- Saving/loading games

---

## 🧾 Screenshots

<img width="499" alt="3SB1_MBartkoOKomkaADobrovicSMihalikovaTHanak_GUIMiny_v1" src="https://github.com/user-attachments/assets/43136980-c574-47ae-b00c-47ba6f14b040" />


---

## 📐 UML Diagram

<img width="575" alt="3SB1_MBartkoOKomkaADobrovicSMihalikovaTHanak_UMLMiny_v1" src="https://github.com/user-attachments/assets/f949dbd0-bbeb-4eb9-9ec5-7ebc5506b166" />

---

## 🛠️ Requirements

- Java 17+
- JavaFX SDK (configured in project)
- IDE like IntelliJ IDEA or VS Code

---

## 📦 Build & Run

```bash
# Compile and run (if JavaFX is configured)
mvn clean javafx:run

Or open the project in your IDE and run HelloApplication.

⸻

📄 License

This project is open-source and free to use. Add a LICENSE file if needed.

⸻
