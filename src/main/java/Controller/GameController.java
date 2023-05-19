package Controller;

import Model.Game;
import Model.ShootingBall;
import Model.SmallBall;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Cylinder;
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

    public ShootingBall makeAShootingBall(double angle, int startingX) {
        return new ShootingBall(angle, Game.smallBallsRadius, startingX, Game.shootingY);
    }

    public void hitShootingBall(ShootingBall ball) {
        if (doesThisBallHitAnyBall(ball)) return;
        double angle = game.getAngleFromCoordinates((int) ball.getCenterX(), (int) ball.getCenterY());
        double absoluteAngle = angle - game.currentAngle;
        Pane pane = GameMenu.pane();
        pane.getChildren().remove(ball);
        setABallOnBigBall((int) absoluteAngle);
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

    public boolean doesThisBallHitAnyBall(Circle ball){
        for (Circle circle : game.smallBalls()){
            if (!circle.equals(ball) && areSmallBallsHitting(ball,circle)) return true;
        }
        return false;
    }
}
