package view.ProfileMenu;

import Controller.ControllerProfileMenu;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainMenu;
import view.main;

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class ChangePasswordMenu extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception { // todo
        ChangePasswordMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/ChangePasswordMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private ControllerProfileMenu controllerProfileMenu;

    {
        controllerProfileMenu = new ControllerProfileMenu();
    }

    @FXML
    private TextField newPass;


    public void reset() {
        newPass.setText("");
    }

    public void back() throws Exception {
        main.controller().profileMenu().start(stage);
    }

    public void change() throws Exception {
        String password = this.newPass.getText();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (controllerProfileMenu.changePassword(password)) {
            case HAS_SPACE:
                alert.setHeaderText("has space");
                alert.setContentText("password can't have space");
                alert.showAndWait();
                return;
            case WEEK_PASSWORD:
                alert.setHeaderText("week password");
                alert.setContentText("use letters, numbers and symbols." +
                        " Also password must contain at least 8 characters");
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
