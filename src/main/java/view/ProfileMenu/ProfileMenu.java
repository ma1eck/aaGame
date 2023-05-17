package view.ProfileMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainMenu;
import view.main;

import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class ProfileMenu extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/ProfileMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void changeUsername(MouseEvent mouseEvent) throws Exception {
        main.controller().changeUsernameMenu().start(stage);
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception {
        main.controller().changePasswordMenu().start(stage);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        main.controller().mainMenu().start(stage);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        main.controller().logout();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("You logged out");
        alert.showAndWait();
        main.controller().preLoginMenu().start(stage);
    }
}
