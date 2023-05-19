package view;

import Controller.GameController;
import Model.RotateAnimation;
import Model.ShootingBall;
import Model.ShootingBallAnimation;
import Model.SmallBall;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.TimerTask;

public class GameMenu extends Application {
    public static int stageWidth = 400;
    public static int stageHeight = 700;
    private static Stage stage;
    private static GameController controller;
    private static int rotateRate;

    private static ArrayList<Transition> transitions = new ArrayList<>();
    private static Pane pane;
    private static AnimationTimer animationTimer = null;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new GameController();
        GameMenu.stage = stage;
        pane = new Pane();
        pane.setPrefSize(stageWidth, stageHeight);
        Circle bigBall = controller.getBigBall();
        pane.getChildren().add(bigBall);
        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        pane.getChildren().addAll(smallBalls);
        Scene scene = new Scene(pane);
        setKeyEvents(scene);
        rotateRate = controller.rotatingRate();
        updateSmallBallsInTransition();
        stage.setScene(scene);
        stage.show();
    }

    private void setKeyEvents(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.TAB))
                    freeze();
                else if (keyEvent.getCode().equals(KeyCode.SPACE))
                    shootBall();

            }
        });
    }

    private void shootBall() {
        double angle = 0;
        int startingX = 200; // todo
        ShootingBall ball = controller.makeAShootingBall(angle, startingX);
        pane.getChildren().add(ball);
        new ShootingBallAnimation(ball, pane, main.controller().currentGame()).play();
    }

    public static void updateSmallBallsInTransition() {
        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        if (transitions.size() > 0) {
            transitions.get(0).stop();
            transitions.remove(0);
        }
        transitions.add(0,
                new RotateAnimation(main.controller().currentGame(), smallBalls, rotateRate));
        transitions.get(0).play();
    }

    public static Pane pane() {
        return pane;
    }

    public void freeze() {
        if (false || transitions.size() == 0) return; // todo : check something
        rotateRate = 2;
        updateSmallBallsInTransition();
        returnToDefaultAfterFrozenDuration();
    }

    private static void returnToDefaultAfterFrozenDuration() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                rotateRate = controller.rotatingRate();
                updateSmallBallsInTransition();
            }
        };
        final long startTime = System.nanoTime();
        if (animationTimer != null) animationTimer.stop();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startTime) / 1000000000;
                if (t > controller.freezeTime()) {
                    task.run();
                    this.stop();
                }
            }
        };
        animationTimer.start();
    }

}
