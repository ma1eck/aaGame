package view;

import Controller.GameController;
import Model.*;
import Utils.GetRandom;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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
    private static ProgressBar freezeProgressBar;
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
        main.controller().setGrayScaleBaseOnSetting(pane);
        setInformationBarOnPane();
        setBallsOnPane();
        showShootingBallAndPath();
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
            }
            showTimeOnScreen(timePassed);
        }));
        timerTimeLine.setCycleCount(-1);
        timerTimeLine.play();
        animations.add(timerTimeLine);
    }

    private static void showTimeOnScreen(Long timePassed) {
        Integer second = (int) (timePassed % 60);
        Integer minute = (int) (timePassed / 60);
        String minuteStr = minute < 10 ? ("0" + minute.toString()) : minute.toString();
        String secondStr = second < 10 ? ("0" + second.toString()) : second.toString();

        timeText.setText(minuteStr + ":" + secondStr);
    }

    private static void preEndGame() {
        isGameOn = false;
        for (Animation animation : animations) {
            if (!animation.getStatus().equals(STOPPED))
                animation.stop();
        }
        animations.clear();
    }

    public static void loseGame() {
        preEndGame();
        pane.setStyle("-fx-background-color: #ce0909");
        pane.getChildren().add(new Text(stageWidth / 2, stageHeight / 2, "You lost!"));
        postEndGame();

    }


    public static void winGame() {
        preEndGame();
        pane.setStyle("-fx-background-color: #75c516");
        pane.getChildren().add(new Text(stageWidth / 2, stageHeight / 2, "You won!"));
        postEndGame();
    }

    private static void postEndGame() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    controller.addUserScore();
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

    public static void updateFreezeProgressBar() {
        double progress = controller.getFreezeBarProgress();
        freezeProgressBar.setProgress(progress);
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
        freezeProgressBar = new ProgressBar(1);
        freezeProgressBar.setStyle("-fx-accent: #2f0254; ");
        updateFreezeProgressBar();
        informationBar.getChildren().add(freezeProgressBar);

        pane.getChildren().add(informationBar);
    }

    private static void setBallsOnPane() {
        Circle bigBall = controller.getBigBall();
        pane.getChildren().add(bigBall);
        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        pane.getChildren().addAll(smallBalls);
    }

    private static Line pathLine;
    private static Circle shootingBallInstance;

    private static void showShootingBallAndPath() {
        if (pathLine != null) pane.getChildren().remove(pathLine);
        if (shootingBallInstance != null) pane.getChildren().remove(shootingBallInstance);
        pathLine = makePathLine();
        shootingBallInstance = controller.makeABall(controller.shootingX(), controller.shootingY());
        pane.getChildren().add(pathLine);
        pane.getChildren().add(shootingBallInstance);

    }

    private static Line makePathLine() {
        return new Line(controller.shootingX(), controller.shootingY(),
                controller.shootingX() + 100 * Math.sin(Math.toRadians(controller.shootingAngle()))
                , controller.shootingY() - 100 * Math.cos(Math.toRadians(controller.shootingAngle())));
    }

    private void setKeyEvents(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.TAB))
                    freeze();
                else if (keyEvent.getCode().equals(KeyCode.SPACE))
                    shootBall();
                else if ((keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.A)) &&
                        phase() >= 4)
                    shootingPosToLeft();
                else if ((keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.D)) &&
                        phase() >= 4)
                    shootingPosToRight();
            }
        });
    }

    private void shootingPosToRight() {
        if (controller.shootingX() < stageWidth - 20 - 3) controller.changeShootingX(3);
        showShootingBallAndPath();
    }

    private void shootingPosToLeft() {
        if (controller.shootingX() > 20 + 3) controller.changeShootingX(-3);
        showShootingBallAndPath();
    }

    private void shootBall() {
        if (!isGameOn) return;
        int startingX = controller.shootingX(); // todo
        ShootingBall ball = controller.makeAShootingBall(controller.shootingAngle(), startingX);
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
        if (freezeProgressBar.getProgress() < 1 || animations.size() == 0) return;
        rotateRate = 2;
        updateMainRotation();
        returnToDefaultAfterFrozenDuration();
        controller.setNumberOFBallsShotFromPreviousFreeze0();
        updateFreezeProgressBar();
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
        pathLine = null;
        shootingBallInstance = null;
        currentVisibility = true;
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
                startPhase3();
                break;
            case 4:
                startPhase4();
                break;
        }
    }


    private static void startPhase2() {
        reversingRotateTimeLine();
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
        Timeline reversingRotate = new Timeline(new KeyFrame(Duration.millis(300), actionEvent -> {
            Long timePassed = (System.currentTimeMillis() - lastRevers) / 1000;
            if (timePassed > nextReversTime) {
                rotateRate = -rotateRate;
                updateMainRotation();
                lastRevers = System.currentTimeMillis();
                nextReversTime = GetRandom.getInt(minTimeSpending, maxTimeSpending);
            }
        }));
        reversingRotate.setCycleCount(-1);
        reversingRotate.play();
        animations.add(reversingRotate);
    }

    private static boolean currentVisibility = true;

    private static void startPhase3() {
        int invisibilityTime = controller.invisibilityTime();
        int visibilityTime = 3;
        Timeline makeBallsInvisibleAnimation = new Timeline(new KeyFrame(Duration.seconds
                (invisibilityTime + visibilityTime),
                actionEvent -> {
                    for (SmallBall ball : controller.getSmallBalls()) {
                        ball.setVisible(true);
                    }
                    Timeline makingInvisible = new Timeline(new KeyFrame(Duration.seconds
                            (visibilityTime),
                            actionEvent2 -> {
                                for (SmallBall ball : controller.getSmallBalls()) {
                                    ball.setVisible(false);
                                }
                            }));
                    makingInvisible.setCycleCount(1);
                    makingInvisible.play();
                    animations.add(makingInvisible);
                })
        );
        makeBallsInvisibleAnimation.setCycleCount(-1);
        makeBallsInvisibleAnimation.playFromStart();
        animations.add(makeBallsInvisibleAnimation);
    }

    private static void startPhase4() {
        Timeline changingAngleAnimation = new Timeline(
                new KeyFrame(Duration.millis(controller.timeBetweenChangingShootingAngle() * 1000),
                        actionEvent -> {
                            int newAngle = GetRandom.getInt(-25, 25);
                            controller.setAngle(newAngle);
                            showShootingBallAndPath();
                        }));
        changingAngleAnimation.setCycleCount(-1);
        changingAngleAnimation.play();
        animations.add(changingAngleAnimation);
    }

}
