package view.Login;

import Controller.ControllerLoginMenu;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.main;

import java.net.URL;

public class LoginMenu extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        URL url = LoginMenu.class.getResource("/FXML/loginMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        main.controller().setGrayScaleBaseOnSetting(borderPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }


    private ControllerLoginMenu controllerLoginMenu;

    {
        controllerLoginMenu = new ControllerLoginMenu();
    }
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    public void submit(MouseEvent mouseEvent) throws Exception {
        String password = this.password.getText();
        String username = this.username.getText();
        switch (controllerLoginMenu.login(username, password)) {
            case USERNAME_DOESNT_EXIST:
                Alert alertUsername = new Alert(Alert.AlertType.ERROR);
                alertUsername.setTitle("Error");
                alertUsername.setHeaderText("Login error");
                alertUsername.setContentText("username doesn't exist");
                alertUsername.showAndWait();
                return;
            case WRONG_PASSWORD:
                Alert alertPassword = new Alert(Alert.AlertType.ERROR);
                alertPassword.setTitle("Error");
                alertPassword.setHeaderText("Login error");
                alertPassword.setContentText("your password is wrong");
                alertPassword.showAndWait();
                return;
            case SUCCESSFUL:
                main.controller().mainMenu().start(stage);
                return;
        }
    }

    public void reset(MouseEvent mouseEvent) {
        username.setText("");
        password.setText("");
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        main.controller().preLoginMenu().start(stage);
    }
}