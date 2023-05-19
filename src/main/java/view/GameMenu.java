package view;

import Controller.GameController;
import Model.Game;
import Model.RotateAnimation;
import Model.SmallBall;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.util.ArrayList;

public class GameMenu extends Application {
    private static Stage stage;
    private static GameController controller;

    private static ArrayList<Transition> transitions = new ArrayList<>();
    private static Pane pane;
    @Override
    public void start(Stage stage) throws Exception {
        controller = new GameController();
        GameMenu.stage = stage;
        pane = new Pane();
        pane.setPrefSize(400, 700);

        Circle bigBall = controller.getBigBall();
        pane.getChildren().add(bigBall);
        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        pane.getChildren().addAll(smallBalls);
        Scene scene = new Scene(pane);
        updateSmallBallsInTransition();
        stage.setScene(scene);
        stage.show();
    }
    public static void updateSmallBallsInTransition(){
        ArrayList<SmallBall> smallBalls = controller.getSmallBalls();
        if (transitions.size() > 0) {
            transitions.get(0).stop();
            transitions.remove(0);
        }
        transitions.add(0,
                new RotateAnimation(main.controller().currentGame(),smallBalls, controller.rotatingRate()));
        transitions.get(0).play();
    }

    public static Pane pane() {
        return pane;
    }
}
