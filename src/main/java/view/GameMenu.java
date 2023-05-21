package view;

import Controller.GameController;
import Model.*;
import Utils.GetRandom;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.ArrayList;

import static javafx.animation.Animation.Status.STOPPED;

public class GameMenu extends Application {
    public static GameMenu gameMenu;
    public static int stageWidth = 400;
    public static int stageHeight = 700;
    private static Stage stage;
    private static Scene scene;
    private static boolean isGameOn = true;
    private static int currentPhase = 1;
    private static GameController controller;
    private static int rotateRate;
    private static HBox informationBar;
    private static Text ballsToShootText;
    private static Text scoreText;
    private static Text timeText;
    private static ArrayList<Animation> animations = new ArrayList<>();
    private static RotateAnimation rotateAnimation = null;
    private static Timeline timerTimeLine;
    private static Pane pane;
    private static AnimationTimer stopFreezeAnimationTimer = null; // to reset freeze
    private static long startingGameTime;

    @Override
    public void start(Stage stage) throws Exception {
        gameMenu = this;
        startANewGame();
        controller = new GameController();
        GameMenu.stage = stage;
        pane = new Pane();
        startingGameTime = System.currentTimeMillis();
        setTimeLine();
        pane.setPrefSize(stageWidth, stageHeight);
        setInformationBarOnPane();
        setBallsOnPane();
        scene = new Scene(pane);
        setKeyEvents(scene);
        rotateRate = controller.rotatingRate();
        updateMainRotation();
        stage.setScene(scene);
        stage.show();
    }

    private void setTimeLine() {
        timerTimeLine = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> {
            Long timePassed = (System.currentTimeMillis() - startingGameTime) / 1000;
            if (phase() != currentPhase) {
                updatePhase();
                System.out.println(currentPhase = phase()); // todo
            }
            showTimeOnScreen(timePassed);
        }));
        timerTimeLine.setCycleCount(-1);
        timerTimeLine.play();
        animations.add(timerTimeLine);
    }

    private static void showTimeOnScreen(Long timePassed) {
        timeText.setText(timePassed.toString());
    }

    public static void loseGame() {

        isGameOn = false;
        for (Animation animation : animations) {
            if (!animation.getStatus().equals(STOPPED))
                animation.stop();
        }
        animations.clear();

        pane.getChildren().add(new Text(stageWidth / 2, stageHeight / 2, "You lost!"));
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    main.controller().mainMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    public static void winGame() {
        isGameOn = false;
        for (Animation animation : animations) {
            if (!animation.getStatus().equals(STOPPED)) {
                animation.stop();
            }
        }
        animations.clear();

        pane.getChildren().add(new Text(stageWidth / 2, stageHeight / 2, "You won!"));
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    main.controller().mainMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


    public static void updateBallsToShootText(String ballsToShootNumber) {
        ballsToShootText.setText(ballsToShootNumber);
    }

    public static void updateScoreText(String score) {
        scoreText.setText(score);
    }


    private static void setInformationBarOnPane() {
        informationBar = new HBox();
        informationBar.getChildren().add(new Text("Balls to shoot : "));
        ballsToShootText = new Text(controller.getBallsToShootNumber().toString());
        informationBar.getChildren().add(ballsToShootText);
        informationBar.getChildren().add(new Text("      score : "));
        scoreText = new Text(controller.getScore().toString());
        informationBar.getChildren().add(scoreText);
        informationBar.getChildren().add(new Text("      time passed : "));
        timeText = new Text();
        showTimeOnScreen(0L);
        informationBar.getChildren().add(timeText);

        pane.getChildren().add(informationBar);
    }

    private static void setBallsOnPane() {
        Circle bigBall = controller.getBigBall();
        pane.getChildren().add(bigBall);
        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        pane.getChildren().addAll(smallBalls);
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
        if (!isGameOn) return;

        double angle = 0;
        int startingX = 200; // todo
        ShootingBall ball = controller.makeAShootingBall(angle, startingX);
        pane.getChildren().add(ball);
        animations.add(new ShootingBallAnimation(ball, pane, main.controller().currentGame()));
        animations.get(animations.size() - 1).play();
    }

    public static void updateMainRotation() {
        if (!isGameOn) return;

        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        if (rotateAnimation != null) {
            rotateAnimation.stop();
            animations.remove(rotateAnimation);
        }
        rotateAnimation = new RotateAnimation(main.controller().currentGame(), smallBalls, rotateRate);
        animations.add(rotateAnimation);
        rotateAnimation.play();
    }

    public static Pane pane() {
        return pane;
    }

    public void freeze() {
        if (false || animations.size() == 0) return; // todo : check something
        rotateRate = 2;
        updateMainRotation();
        returnToDefaultAfterFrozenDuration();
    }

    private static void returnToDefaultAfterFrozenDuration() {
        Timeline returnToDefaultRotation = new Timeline(
                new KeyFrame(Duration.millis(controller.freezeTime() * 1000),
                        actionEvent -> {
                            rotateRate = controller.rotatingRate();
                            updateMainRotation();
                        }));
        returnToDefaultRotation.setCycleCount(1);
        returnToDefaultRotation.play();
        animations.add(returnToDefaultRotation);

    }

    public static void startANewGame() {
        isGameOn = true;
        if (animations == null) animations = new ArrayList<>();
        else animations.clear();
        rotateAnimation = null;
        currentPhase = 1;

    }


    public static int phase() {
        return 1 + ((controller.numberOfStartingPlayableBalls() - controller.getBallsToShootNumber()) * 4) /
                controller.numberOfStartingPlayableBalls();
    }

    private static void updatePhase() {
        if (currentPhase == phase()) return;

        currentPhase = phase();
        switch (currentPhase) {
            case 2:
                startPhase2();
                break;
            case 3:
                break; // todo
            case 4:
                break; // todo
        }
    }


    private static void startPhase2() {
        reversingRotateTimeLine();
        // todo change in balls size
        changingBallsSize();
    }

    private static ChangingBallsSizeAnimation changingBallsSizeAnimation;

    private static void changingBallsSize() {
        changingBallsSizeAnimation = controller.changingBallsSizeAnimation();
        changingBallsSizeAnimation.play();
        animations.add(changingBallsSizeAnimation);
    }

    public static void updateChangingBallsSizeAnimation() {
        if (phase() < 2) return;
        if (changingBallsSizeAnimation != null) if (!changingBallsSizeAnimation.getStatus().equals(STOPPED)) {
            changingBallsSizeAnimation.stop();
            animations.remove(changingBallsSizeAnimation);
        }
        changingBallsSizeAnimation = controller.changingBallsSizeAnimation();
        changingBallsSizeAnimation.play();
        animations.add(changingBallsSizeAnimation);
    }

    private static long lastRevers;
    private static int nextReversTime;

    private static void reversingRotateTimeLine() {
        int minTimeSpending = 3;
        int maxTimeSpending = 7;
        lastRevers = System.currentTimeMillis();
        nextReversTime = GetRandom.getInt(minTimeSpending, maxTimeSpending);
        System.out.println(nextReversTime);
        Timeline reversingRotate = new Timeline(new KeyFrame(Duration.millis(300), actionEvent -> {
            Long timePassed = (System.currentTimeMillis() - lastRevers) / 1000;
            if (timePassed > nextReversTime) {
                rotateRate = -rotateRate;
                updateMainRotation();
                lastRevers = System.currentTimeMillis();
                nextReversTime = GetRandom.getInt(minTimeSpending, maxTimeSpending);
                System.out.println(nextReversTime);
            }
        }));
        reversingRotate.setCycleCount(-1);
        reversingRotate.play();
        animations.add(reversingRotate);
    }
}
