package Controller;

import Model.Game;
import Model.ShootingBall;
import Model.TwoPlayerGame;
import javafx.scene.shape.Circle;
import view.main;

public class TwoPlayerGameController extends GameController {
    private TwoPlayerGame game;
    public TwoPlayerGameController() {
        super();
        game = main.controller().currentTwoPlayerGame();
        super.game = game;
    }
    public double shootingAnglePlayer2() {
        return  180 - game.shootingAngle();
    }

    public int shootingXPlayer2() {
        return game.shootingXPlayer2();
    }

    public int shootingYPlayer2() {
        return game.shootingYPlayer2();
    }

    public Circle makeABallPlayer2(int x, int y) {
        Circle ball = new Circle(x, y, TwoPlayerGame.smallBallsRadius, TwoPlayerGame.player2Color);
        return ball;
    }

    public ShootingBall makeAShootingBallPlayer2(double angle, int x) {
        return new ShootingBall(angle, Game.smallBallsRadius, x, TwoPlayerGame.shootingYPlayer2,
                TwoPlayerGame.player2Color);
    }

    public void changeShootingXPlayer2(int amount) {
        game.changeShootingXPlayer2(amount);
    }
}
