package view;

import Controller.GameController;
import Model.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class GameMenu extends Application {
    private static Stage stage;
    private static GameController controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new GameController();
        GameMenu.stage = stage;
        Pane pane = new Pane();
        pane.setPrefSize(400,600);
        Circle bigBall = new Circle();
        bigBall.setRadius(controller.bigBallRadius());
        bigBall.setCenterX(controller.bigBallCenterX());
        bigBall.setCenterY(controller.bigBallCenterY());
        pane.getChildren().add(bigBall);
        for(int angle : main.controller().currentGame().ballsPosition){
            Circle smallBall = new Circle();
            smallBall.setRadius(Game.smallBallsRadius);
            smallBall.setCenterX(main.controller().currentGame().getPositionOfAAngle(angle)[0]);
            smallBall.setCenterY(main.controller().currentGame().getPositionOfAAngle(angle)[1]);
            pane.getChildren().add(smallBall);
        }
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}
