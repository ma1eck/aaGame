package view.ProfileMenu;

import Controller.ControllerLoginMenu;
import Controller.ControllerProfileMenu;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainMenu;
import view.main;

import java.io.IOException;
import java.net.URL;

public class ChangeUsernameMenu extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception { // todo
        ChangeUsernameMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/ChangeUsernameMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        main.controller().setGrayScaleBaseOnSetting(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private ControllerProfileMenu controllerProfileMenu;

    {
        controllerProfileMenu = new ControllerProfileMenu();
    }

    @FXML
    private TextField newUsername;


    public void reset() {
        newUsername.setText("");
    }

    public void back() throws Exception {
        main.controller().profileMenu().start(stage);
    }

    public void change() throws Exception {
        String newUsername = this.newUsername.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (controllerProfileMenu.changeUsername(newUsername)) {
            case HAS_SPACE:
                alert.setHeaderText("has space");
                alert.setContentText("username can't have space");
                alert.showAndWait();
                return;
            case SHORT_USERNAME:
                alert.setHeaderText("short username");
                alert.setContentText("username must contain at least 3 characters");
                alert.showAndWait();
                return;
            case USERNAME_EXISTS:
                alert.setHeaderText("username exist");
                alert.setContentText("this username is used");
                alert.showAndWait();
                return;
            case SUCCESSFUL:
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("changing was successful");
                alert.showAndWait();
                main.controller().profileMenu().start(stage);
                return;
        }
    }
}
