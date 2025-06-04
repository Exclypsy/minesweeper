Dokumentácia k projektu Minesweeper
1. Úvod
Tento projekt predstavuje klasickú logickú hru Minesweeper vytvorenú v prostredí JavaFX.
Hráč si môže nastaviť rozmery hracieho poľa a počet mín. Po spustení hry odhaľuje políčka,
pričom sa vyhýba poliam s mínami. Hra sleduje čas a počet ťahov.
2. Architektúra
Projekt je rozdelený do niekoľkých tried podľa zodpovednosti:
• HelloApplication.java – spúšťacia trieda JavaFX aplikácie.
• HelloController.java – zodpovedá za spracovanie GUI a vstupov používateľa.
• GameLogic.java – obsahuje hlavnú hernú logiku, ako napr. generovanie mín, kontrolu
výhry a prehry.
• GameCell.java – abstraktný základ pre herné políčko.
• Cell.java – implementuje konkrétne správanie bunky na hernej ploche.
3. Grafické rozhranie a UML
4. Komentované zdrojové kódy vlastných tried
HelloController.java
Spracováva všetky prvky GUI, komunikuje s GameLogic.
• @FXML prepojenia s komponentmi: TextField, GridPane, Label, …
• startGame() – číta vstupy, inicializuje logiku, štartuje časovač.
• Obsahuje metódy pre aktualizáciu stavu hry (čas, počet ťahov, výhra/prehra).
GameLogic.java
Oddelená logika herného poľa.
• startNewGame(...) – resetuje pole a umiestni míny.
• placeMines() – náhodne generuje míny.
• calculateNeighborMines() – počíta okolité míny pre každú bunku.
• revealNeighbors(x, y) – rekurzívne odhaľovanie políčok.
• checkWin() – zisťuje, či boli všetky bezpečné polia odhalené.
• gameOver(boolean) – končí hru a odhalí všetky míny pomocou obrázka.
Cell.java
Reprezentuje jedno políčko na hernej ploche.
• Zdedené z GameCell (ktorá dedí Button).
• Uchováva:
o isMine – či je bunka mínou
o flagged – či je označená
o revealed – či bola odhalená
o neighborMines – počet susedných mín
• Kliknutie ľavým tlačidlom myši: reveal()
• Pravým tlačidlom: toggleFlag(), vkladá obrázok vlajky (flag.png)
• Zobrazovanie obrázku bomby (bomb.png) pri prehre
GameCell.java
Základná abstraktná trieda pre políčko:
public abstract class GameCell extends Button {
public abstract void reveal();
public abstract void toggleFlag();
}
Zabezpečuje rozhranie pre všetky typy buniek v hre.
HelloApplication.java
Spúšťacia trieda pre JavaFX:
@Override
public void start(Stage stage) throws Exception {
FXMLLoader loader = new FXMLLoader(...);
Scene scene = new Scene(loader.load(), ...);
stage.setScene(scene);
stage.show();
}
5. Používateľský návod
1. Spusti aplikáciu.
2. 4. 5. 6. Zadaj šírku, výšku a počet mín.
3. Klikni na Nová hra.
Klikni ľavým tlačidlom myši na políčko – odhalíš ho.
Pravým tlačidlom môžeš umiestniť alebo odstrániť vlajku.
Hru vyhráš, ak odhalíš všetky polia bez mín.
6. Záver
Projekt Minesweeper ukazuje správne použitie JavaFX a OOP princípov ako dedičnosť a
polymorfizmus. Triedy sú dobre oddelené a modulárne, čo uľahčuje rozšíriteľnosť. Použitý
dizajn umožňuje jednoduché testovanie a budúci vývoj ďalších funkcií.
