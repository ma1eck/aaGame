package Model;

import Controller.GameController;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.GameMenu;

public class ShootingBallAnimation extends Transition {
    private ShootingBall ball;
    private Pane pane;
    private Game game;
    private GameController controller = new GameController();

    public ShootingBallAnimation(ShootingBall ball, Pane pane, Game game) {
        this.ball = ball;
        this.pane = pane;
        this.game = game;
        setCycleDuration(Duration.millis(1000));
        setCycleCount(-1);
    }

    @Override
    protected void interpolate(double v) {
        double x = ball.getCenterX(),
        y =  ball.getCenterY();

        if (x < 0 || x > GameMenu.stageWidth ||
                y < 0 || y > GameMenu.stageHeight){
            this.stop();
            pane.getChildren().remove(ball);
        }
        else if (game.getDistanceToCenter(x,y) <= Game.smallBallsDistanceFromCenter){ // todo can be in controller
            this.stop();
            controller.hitShootingBall(ball);
        }
        else {
            ball.setCenterY(y - ball.yVelocity());
            ball.setCenterX(x + ball.xVelocity());
        }
    }
}
