package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AfterGameMenu extends Application {
    public static String score;
    public static String timeSpent;

    @Override
    public void start(Stage stage) throws Exception {
        VBox pane = new VBox();
        pane.setSpacing(20);
        HBox informationBar = new HBox();
        informationBar.setSpacing(20);
        informationBar.getChildren().add(new Text("score : " + score.toString()));
        informationBar.getChildren().add(new Text("time  : " + timeSpent));

        HBox buttons = new HBox();
        buttons.setSpacing(20);
        Button mainMenuButton = new Button();
        mainMenuButton.setText("main menu");
        mainMenuButton.setOnAction(actionEvent -> {
            stage.close();
            try {
                main.controller().mainMenu().start(main.controller().stage());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        buttons.getChildren().add(mainMenuButton);
        Button scoreboardButton = new Button();
        scoreboardButton.setText("scoreboard");
        scoreboardButton.setOnAction(actionEvent -> {
            stage.close();
            try {
                main.controller().scoreboardMenu().start(main.controller().stage());
            } catch (Exception e) {
                System.out.println(e);
            }
        });
        buttons.getChildren().add(scoreboardButton);

        pane.getChildren().add(informationBar);
        pane.getChildren().add(buttons);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

}
