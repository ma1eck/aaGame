package Controller;

import Model.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import view.GameMenu;
import view.main;

import java.util.ArrayList;

import static java.lang.Math.min;

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
        GameMenu.updateMainRotation();
        GameMenu.updateChangingBallsSizeAnimation();
    }

    public int freezeTime() {
        return game.freezeTime();
    }

    public ShootingBall makeAShootingBall(double angle, int startingX) {
        return new ShootingBall(angle, Game.smallBallsRadius, startingX, Game.shootingY, Game.player1Color);
    }
    public Circle makeABall(int x, int y) {
        Circle ball = new Circle(x, y, Game.smallBallsRadius, Game.player1Color);
        return ball;
    }

    public void hitShootingBall(ShootingBall ball) {
        if (doesThisBallHitAnyBall(ball)) {
            loseGame();
            return;
        }
        double angle = game.getAngleFromCoordinates((int) ball.getCenterX(), (int) ball.getCenterY());
        double absoluteAngle = angle - game.currentAngle;
        Pane pane = GameMenu.pane();
        pane.getChildren().remove(ball);
        setABallOnBigBall((int) absoluteAngle);
        game.decreaseBallsToShoot();
        game.increaseScore();
        GameMenu.updateBallsToShootText(getBallsToShootNumber().toString());
        GameMenu.updateScoreText(getScore().toString());
        game.increaseNumberOFBallsShotFromPreviousFreeze();
        GameMenu.updateFreezeProgressBar();
        if (getBallsToShootNumber() == 0) winGame();
    }

    public void loseGame() {
        GameMenu.loseGame();
    }

    public void winGame() {
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

    public boolean checkBallsHitting() {
        for (Circle ball : game.smallBalls()) {
            if (doesThisBallHitAnyBall(ball)) {
                loseGame();
                return false;
            }
        }
        return true;
    }

    public ChangingBallsSizeAnimation changingBallsSizeAnimation() {
        ChangingBallsSizeAnimation changingBallsSizeAnimation =
                new ChangingBallsSizeAnimation(game.smallBalls(), this);
        return changingBallsSizeAnimation;
    }

    public double shootingAngle() {
        return game.shootingAngle();
    }

    public void setAngle(int newAngle) {
        game.setShootingAngle(newAngle);
    }

    public int shootingX() {
        return game.shootingX();
    }
    public void changeShootingX(int amount) {
        game.changeShootingX(amount);
    }

    public int shootingY() {
        return Game.shootingY;
    }
    public int timeBetweenChangingShootingAngle() {
        return game.timeBetweenChangingShootingAngle();
    }
    public int invisibilityTime(){
        return game.invisibilityTime();
    }

    public double getFreezeBarProgress() {
        return  min((double) game.numberOFBallsShotFromPreviousFreeze() / Game.ballsToShootBeforeFreezing, 1);
    }
    public void setNumberOFBallsShotFromPreviousFreeze0(){
        game.setNumberOFBallsShotFromPreviousFreeze0();
    }

    public void addUserScore() {
        User currentUser = main.controller().currentUser();
        currentUser.increaseScore(game.difficulty(), game.score());
        User.writeUsers();
    }
}