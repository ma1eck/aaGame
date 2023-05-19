package Controller;

import Model.Game;
import Model.SmallBall;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import view.main;

import java.util.ArrayList;

public class GameController {

    private Game game = main.controller().currentGame();
    public int bigBallRadius() {
        return Game.bigBallRadius;
    }

    public int bigBallCenterX() {
        return Game.bigBallCenterX;
    }

    public int bigBallCenterY() {
        return Game.bigBallCenterY;
    }

    public Circle getBigBall() {
        return game.getBigBall();
    }

    public ArrayList<SmallBall> getSmallBalls() {
        return game.smallBalls();
    }

    public int rotatingRate() {
        return game.rotatingRate();
    }
}
