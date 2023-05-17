package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class MainMenu extends Application {
    private static Stage stage;

    public void start(Stage stage) throws Exception {
        MainMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/MainMenu.fxml"); // todo
        BorderPane pane = FXMLLoader.load(url); // todo
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        main.controller().profileMenu().start(stage);
    }
}
