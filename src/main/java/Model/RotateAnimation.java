package Model;

import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;

public class RotateAnimation extends Transition {
    private Game game;
    private ArrayList<SmallBall> smallBalls;
    private double rate;

    public RotateAnimation(Game game, ArrayList<SmallBall> smallBalls, double rate) {
        this.game = game;
        this.smallBalls = smallBalls;
        this.rate = rate;
        setCycleDuration(Duration.millis(1000));
        setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        game.increaseAngle(rate / 5);
        for (SmallBall smallBall : smallBalls) {
            int[] coordinates = game.getPositionOfAAngle(smallBall.relativeAngle());
            smallBall.updateCoordinates(coordinates);
        }
    }
}
