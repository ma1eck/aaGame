package Controller;

import Model.Game;
import Model.TwoPlayerGame;
import view.main;

public class ControllerMainMenu {
    public void newGame() {
        main.controller().setCurrentGame(new Game(main.controller().currentUser().gameSetting()));
    }

    public void newTwoPlayerGame() {
        main.controller().setCurrentTwoPlayerGame(new TwoPlayerGame(main.controller().currentUser().gameSetting()));
    }
}
