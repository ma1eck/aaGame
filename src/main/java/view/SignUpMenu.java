package view;

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

import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

public class SignUpMenu extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        SignUpMenu.stage = stage;
        URL url = SignUpMenu.class.getResource("/FXML/SignUpMenu.fxml");
        BorderPane borderPane = FXMLLoader.load(url);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirmation;
    private ControllerLoginMenu controllerLoginMenu;

    {
        controllerLoginMenu = new ControllerLoginMenu(main.controller());
    }

    public void signUp(MouseEvent mouseEvent) throws IOException, NoSuchAlgorithmException {
        String username = this.username.getText();
        String password = this.password.getText();
        String passwordConfirmation = this.passwordConfirmation.getText();
        Alert alert;
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("signup error");
        switch  (controllerLoginMenu.signIn(username,password,passwordConfirmation)){
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
            case WEEK_PASSWORD:
                alert.setHeaderText("week password");
                alert.setContentText("use letters, numbers and symbols." +
                        " Also password must contain at least 8 characters");
                alert.showAndWait();
                return;
            case WRONG_CONFIRMATION:
                alert.setHeaderText("confirmation error");
                alert.setContentText("password confirmation is not the same as password");
                alert.showAndWait();
                return;
            case SUCCESSFUL:
                System.out.println("signed up");
                return; // todo : complete these

        }
    }

    public void reset(MouseEvent mouseEvent) {
        username.setText("");
        password.setText("");
        passwordConfirmation.setText("");
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        main.controller().preLoginMenu().start(stage);
    }
}
