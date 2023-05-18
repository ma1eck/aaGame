package view.ProfileMenu.Avatar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainMenu;
import view.main;

import java.net.URL;

public class AvatarMenu extends Application {
    private static Stage stage;

    public void start(Stage stage) throws Exception {
        AvatarMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/AvatarMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        main.controller().profileMenu().start(stage);
    }

    public void avatarFromDevice(MouseEvent mouseEvent) {
        //todo
    }

    public void defaultAvatars(MouseEvent mouseEvent) throws Exception {
        main.controller().defaultAvatarMenu().start(stage);
        //todo
    }
}
