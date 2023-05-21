package Model;

import javafx.animation.Transition;
import javafx.util.Duration;

import java.util.ArrayList;

public class ChangingBallsSizeAnimation extends Transition {
    private ArrayList<SmallBall> smallBalls;
    public ChangingBallsSizeAnimation(ArrayList<SmallBall> smallBalls) {
        this.smallBalls = smallBalls;
        setCycleDuration(Duration.millis(2000));
        setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double currentSize = (1 + Math.sin(v*2*Math.PI) * 0.30) * Game.smallBallsRadius;
        for (SmallBall smallBall : smallBalls) {
            smallBall.setRadius(currentSize);
        }
    }
}
