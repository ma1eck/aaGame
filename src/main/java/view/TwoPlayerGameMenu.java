package view;

import Controller.GameController;
import Controller.TwoPlayerGameController;
import Model.ShootingBall;
import Model.ShootingBallAnimation;
import Utils.GetRandom;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class TwoPlayerGameMenu extends GameMenu {
    private static TwoPlayerGameController controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new TwoPlayerGameController();
        GameMenu.controller = controller;
        gameMenu = this;
        startANewGame();
        GameMenu.stage = stage;
        pane = new Pane();
        startingGameTime = System.currentTimeMillis();
        setTimeLine();
        pane.setPrefSize(stageWidth, stageHeight);
        main.controller().setGrayScaleBaseOnSetting(pane);
        setInformationBarOnPane();
        setMusicChoiceBox();
        setBallsOnPane();
        showShootingBallAndPath();
        showShootingBallAndPathPlayer2();
        scene = new Scene(pane);
        setKeyEvents(scene);
        rotateRate = controller.rotatingRate();
        updateMainRotation();
        stage.setScene(scene);
        stage.show();
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
                else if (keyEvent.getCode().equals(KeyCode.ENTER))
                    shootBallPlayer2();
                else if (keyEvent.getCode().equals(KeyCode.M)) {
                    main.controller().reverseMuteBoolean();
                    main.controller().playMusicDependingOnSetting();
                } else if (keyEvent.getCode().equals(KeyCode.LEFT) &&
                        phase() >= 4)
                    shootingPosToLeft();
                else if (keyEvent.getCode().equals(KeyCode.A) && phase() >= 4)
                    shootingPosToLeftPlayer2();
                else if (keyEvent.getCode().equals(KeyCode.RIGHT) && phase() >= 4)
                    shootingPosToRight();
                else if (keyEvent.getCode().equals(KeyCode.D) && phase() >= 4)
                    shootingPosToRightPlayer2();
            }
        });
    }

    private void shootingPosToRightPlayer2() {
        if (controller.shootingX() < stageWidth - 20 - 3) controller.changeShootingXPlayer2(3);
        showShootingBallAndPathPlayer2();
    }

    private void shootingPosToLeftPlayer2() {
        if (controller.shootingXPlayer2() > 20 + 3) controller.changeShootingXPlayer2(-3);
        showShootingBallAndPathPlayer2();
    }

    private void shootBallPlayer2() {
        if (!isGameOn) return;
        int startingX = controller.shootingXPlayer2(); // todo
        ShootingBall ball = controller.makeAShootingBallPlayer2(controller.shootingAnglePlayer2(), startingX);
        pane.getChildren().add(ball);
        animations.add(new ShootingBallAnimation(ball, pane, controller.currentGame()));
        animations.get(animations.size() - 1).play();
    }

    private static Line pathLinePlayer2;
    private static Circle shootingBallInstancePlayer2;

    private static void showShootingBallAndPathPlayer2() {
        if (pathLinePlayer2 != null) pane.getChildren().remove(pathLinePlayer2);
        if (shootingBallInstancePlayer2 != null) pane.getChildren().remove(shootingBallInstancePlayer2);
        pathLinePlayer2 = makePathLinePlayer2();
        shootingBallInstancePlayer2 =
                controller.makeABallPlayer2(controller.shootingXPlayer2(), controller.shootingYPlayer2());
        pane.getChildren().add(pathLinePlayer2);
        pane.getChildren().add(shootingBallInstancePlayer2);
    }

    private static Line makePathLinePlayer2() {
        return new Line(controller.shootingXPlayer2(), controller.shootingYPlayer2(),
                controller.shootingXPlayer2() + 100 * Math.sin(Math.toRadians(controller.shootingAnglePlayer2()))
                , controller.shootingYPlayer2() - 100 * Math.cos(Math.toRadians(controller.shootingAnglePlayer2())));
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

    protected static void startPhase4() {
        Timeline changingAngleAnimation = new Timeline(
                new KeyFrame(Duration.millis(controller.timeBetweenChangingShootingAngle() * 1000),
                        actionEvent -> {
                            int newAngle = GetRandom.getInt(-25, 25);
                            controller.setAngle(newAngle);
                            showShootingBallAndPath();
                            showShootingBallAndPathPlayer2();
                        }));
        changingAngleAnimation.setCycleCount(-1);
        changingAngleAnimation.play();
        animations.add(changingAngleAnimation);
    }
}
