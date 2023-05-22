package view;

import Controller.ScoreboardController;
import Model.GameDifficulty;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;

public class ScoreboardMenu extends Application {
    private static Stage stage;
    private static GameDifficulty difficulty;
    private static ScoreboardController controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new ScoreboardController();
        difficulty = null;
        ScoreboardMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/ScoreboardMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        main.controller().setGrayScaleBaseOnSetting(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private ChoiceBox<GameDifficulty> difficultyChoiceBox;

    @FXML
    private void initialize() {
        difficultyChoiceBox.getItems().clear();
        difficultyChoiceBox.getItems().addAll(GameDifficulty.values());
    }

    @FXML
    private void back() throws Exception {
        main.controller().mainMenu().start(stage);
    }

    @FXML
    private GridPane gridPane;

    private void updateScoreboard() {
        gridPane.getChildren().removeIf(node -> node instanceof Text); // to not remove labels

        String[][] boarderInfo = controller.getTopPlayers(difficulty);
        int i = 1;
        for (String[] infoOfARow : boarderInfo) {
            Text rank = new Text(infoOfARow[3]);
            switch (i) {
                case 1:
                    rank.setFill(Color.YELLOW);
                    break;
                case 2:
                    rank.setFill(Color.GREEN);
                    break;
                case 3:
                    rank.setFill(Color.RED);
                    break;
            }
            gridPane.add(new Text(infoOfARow[0]), 0, i);
            gridPane.add(new Text(infoOfARow[1]), 1, i);
            gridPane.add(new Text(infoOfARow[2]), 2, i);
            gridPane.add(rank, 3, i);
            i++;
        }
    }

    @FXML
    private void changeDifficulty() {
        difficulty = difficultyChoiceBox.getValue();
        updateScoreboard();
    }
}
