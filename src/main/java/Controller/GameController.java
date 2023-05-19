package Controller;

import Model.Game;
import Model.SmallBall;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import view.GameMenu;
import view.main;

import java.util.ArrayList;

public class GameController {

    private Game game = main.controller().currentGame();

    public Circle getBigBall() {
        return game.getBigBall();
    }

    public ArrayList<SmallBall> getSmallBalls() {
        return game.smallBalls();
    }

    public int rotatingRate() {
        return game.rotatingRate();
    }

    public void setABallOnBigBall(int angle) {
        Pane pane = GameMenu.pane();
        if (pane == null) return;
        SmallBall smallBall = game.addABallWithAngle(angle);
        pane.getChildren().add(smallBall);
        GameMenu.updateSmallBallsInTransition();
    }

    public int freezeTime() {
        return game.freezeTime();
    }
}
