package Controller;

import Model.Game;
import view.main;

public class ControllerMainMenu {
    public void newGame(){
        main.controller().setCurrentGame(new Game(main.controller().currentUser().gameSetting()));
    }
}
