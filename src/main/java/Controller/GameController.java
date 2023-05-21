package Controller;

import Model.ChangingBallsSizeAnimation;
import Model.Game;
import Model.ShootingBall;
import Model.SmallBall;
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
        SmallBall smallBall = game.addABallWithAngle(angle, Game.player1Color);
        pane.getChildren().add(smallBall);
        GameMenu.updateChangingBallsSizeAnimation();
        GameMenu.updateMainRotation();
    }

    public int freezeTime() {
        return game.freezeTime();
    }

    public ShootingBall makeAShootingBall(double angle, int startingX) {
        return new ShootingBall(angle, Game.smallBallsRadius, startingX, Game.shootingY, Game.player1Color);
    }

    public void hitShootingBall(ShootingBall ball) {
        if (doesThisBallHitAnyBall(ball)) loseGame();
        double angle = game.getAngleFromCoordinates((int) ball.getCenterX(), (int) ball.getCenterY());
        double absoluteAngle = angle - game.currentAngle;
        Pane pane = GameMenu.pane();
        pane.getChildren().remove(ball);
        setABallOnBigBall((int) absoluteAngle);
        game.decreaseBallsToShoot();
        game.increaseScore();
        GameMenu.updateBallsToShootText(getBallsToShootNumber().toString());
        GameMenu.updateScoreText(getScore().toString());
        if (getBallsToShootNumber() == 0) winGame();
    }

    private void loseGame() {
        GameMenu.loseGame();
    }

    private void winGame() {
        GameMenu.winGame();
    }

    public boolean areSmallBallsHitting(Circle ball1, Circle ball2) {
        double radiusSum = ball1.getRadius() + ball2.getRadius();
        double x1 = ball1.getCenterX(),
                x2 = ball2.getCenterX(),
                y1 = ball1.getCenterY(),
                y2 = ball2.getCenterY();
        double xDistance = x1 - x2;
        double yDistance = y1 - y2;
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
        return distance <= radiusSum;

    }

    public boolean doesThisBallHitAnyBall(Circle ball) {
        for (Circle circle : game.smallBalls()) {
            if (!circle.equals(ball) && areSmallBallsHitting(ball, circle)) return true;
        }
        return false;
    }

    public Integer getBallsToShootNumber() {
        return game.ballsToShoot();
    }

    public Integer getScore() {
        return game.score();
    }

    public int numberOfStartingPlayableBalls() {
        return game.numberOfPlayableBalls();
    }

    public boolean areBallsHitting() {
        for (Circle ball : game.smallBalls()) {
            if (doesThisBallHitAnyBall(ball)) return true;
        }
        return false;
    }

    public ChangingBallsSizeAnimation changingBallsSizeAnimation() {
        ChangingBallsSizeAnimation changingBallsSizeAnimation =
                new ChangingBallsSizeAnimation(game.smallBalls());
        return changingBallsSizeAnimation;
    }
}