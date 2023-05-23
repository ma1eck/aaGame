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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ProgressBar;
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
    protected static Stage stage;
    protected static Scene scene;
    protected static boolean isGameOn = true;
    protected static int currentPhase = 1;
    protected static GameController controller;
    protected static int rotateRate;
    protected static HBox informationBar;
    protected static Text ballsToShootText;
    protected static Text scoreText;
    protected static Text timeText;
    protected static ProgressBar freezeProgressBar;
    protected static ChoiceBox<Integer> musicChoiceBox;
    protected static ArrayList<Animation> animations = new ArrayList<>();
    protected static RotateAnimation rotateAnimation = null;
    protected static Timeline timerTimeLine;
    protected static Pane pane;
    protected static AnimationTimer stopFreezeAnimationTimer = null; // to reset freeze
    protected static long startingGameTime;

    public static GameController controller() {
        return controller;
    }

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

    protected void setTimeLine() {
        timerTimeLine = new Timeline(new KeyFrame(Duration.millis(500), actionEvent -> {
            Long timePassed = (System.currentTimeMillis() - startingGameTime) / 1000;
            if (phase() != currentPhase) {
                updatePhase();
            }
            showTimeOnScreen(timePassed);
            if (controller.checkIfTimeIsUp(timePassed)) return;

        }));
        timerTimeLine.setCycleCount(-1);
        timerTimeLine.play();
        animations.add(timerTimeLine);
    }

    public static Integer getTimePassedFromLabel() {
        String minStr = timeText.getText().substring(0, 2);
        String secStr = timeText.getText().substring(3, 5);
        try {
            Integer timePassed = Integer.parseInt(minStr) * 60 + Integer.parseInt(secStr);
            return timePassed;
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        return null;
    }

    protected static void showTimeOnScreen(Long timePassed) {
        Integer second = (int) (timePassed % 60);
        Integer minute = (int) (timePassed / 60);
        String minuteStr = minute < 10 ? ("0" + minute.toString()) : minute.toString();
        String secondStr = second < 10 ? ("0" + second.toString()) : second.toString();

        timeText.setText(minuteStr + ":" + secondStr);
    }

    protected static void preEndGame() {
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

    protected static void postEndGame() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                try {
                    controller.addUserScore();
                    controller.addUserTimeSpent();
                    AfterGameMenu.score = scoreText.getText();
                    AfterGameMenu.timeSpent = timeText.getText();
                    new AfterGameMenu().start(new Stage());
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


    protected static void setInformationBarOnPane() {
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

    protected static void setBallsOnPane() {
        Circle bigBall = controller.getBigBall();
        pane.getChildren().add(bigBall);
        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        pane.getChildren().addAll(smallBalls);
    }

    protected static Line pathLine;
    protected static Circle shootingBallInstance;

    protected static void showShootingBallAndPath() {
        if (pathLine != null) pane.getChildren().remove(pathLine);
        if (shootingBallInstance != null) pane.getChildren().remove(shootingBallInstance);
        pathLine = makePathLine();
        shootingBallInstance = controller.makeABall(controller.shootingX(), controller.shootingY());
        pane.getChildren().add(pathLine);
        pane.getChildren().add(shootingBallInstance);

    }

    protected static Line makePathLine() {
        return new Line(controller.shootingX(), controller.shootingY(),
                controller.shootingX() + 100 * Math.sin(Math.toRadians(controller.shootingAngle()))
                , controller.shootingY() - 100 * Math.cos(Math.toRadians(controller.shootingAngle())));
    }

    protected void setKeyEvents(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.NUMPAD1))
                    main.controller().setMusicByIndex(0);
                else if (keyEvent.getCode().equals(KeyCode.NUMPAD2))
                    main.controller().setMusicByIndex(1);
                else if (keyEvent.getCode().equals(KeyCode.NUMPAD3))
                    main.controller().setMusicByIndex(2);
                else if (keyEvent.getCode().equals(KeyCode.TAB))
                    freeze();
                else if (keyEvent.getCode().equals(KeyCode.SPACE))
                    shootBall();
                else if (keyEvent.getCode().equals(KeyCode.M)) {
                    main.controller().reverseMuteBoolean();
                    main.controller().playMusicDependingOnSetting();
                } else if ((keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.A)) &&
                        phase() >= 4)
                    shootingPosToLeft();
                else if ((keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.D)) &&
                        phase() >= 4)
                    shootingPosToRight();
            }
        });
    }

    protected void shootingPosToRight() {
        if (controller.shootingX() < stageWidth - 20 - 3) controller.changeShootingX(3);
        showShootingBallAndPath();
    }

    protected void shootingPosToLeft() {
        if (controller.shootingX() > 20 + 3) controller.changeShootingX(-3);
        showShootingBallAndPath();
    }

    protected void shootBall() {
        if (!isGameOn) return;
        int startingX = controller.shootingX(); // todo
        ShootingBall ball = controller.makeAShootingBall(controller.shootingAngle(), startingX);
        pane.getChildren().add(ball);
        animations.add(new ShootingBallAnimation(ball, pane, controller.currentGame()));
        animations.get(animations.size() - 1).play();
    }

    public static void updateMainRotation() {
        if (!isGameOn) return;

        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        if (rotateAnimation != null) {
            rotateAnimation.stop();
            animations.remove(rotateAnimation);
        }
        rotateAnimation = new RotateAnimation(controller.currentGame(), smallBalls, rotateRate);
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

    protected static void returnToDefaultAfterFrozenDuration() {
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

    protected static void updatePhase() {
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


    protected static void startPhase2() {
        reversingRotateTimeLine();
        changingBallsSize();
    }

    protected static ChangingBallsSizeAnimation changingBallsSizeAnimation;

    protected static void changingBallsSize() {
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

    protected static long lastRevers;
    protected static int nextReversTime;

    protected static void reversingRotateTimeLine() {
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

    protected static boolean currentVisibility = true;

    protected static void startPhase3() {
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

    protected static void startPhase4() {
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
