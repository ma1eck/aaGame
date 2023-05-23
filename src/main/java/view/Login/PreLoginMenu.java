package view.Login;

import Controller.ControllerLoginMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.main;

import java.net.URL;

public class PreLoginMenu extends Application {
    private static Stage stage;
    ControllerLoginMenu loginMenuController;
    {
        loginMenuController = new ControllerLoginMenu();
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        main.controller().setStage(stage);
        PreLoginMenu.stage = stage;
        stage.setTitle("aa");
        URL url = PreLoginMenu.class.getResource("/FXML/PreLoginMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        main.controller().setGrayScaleBaseOnSetting(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    public void login() throws Exception {
        new LoginMenu().start(stage);
    }
    public void signup() throws Exception {
        new SignUpMenu().start(stage);
    }
    public void enterAsGuest() throws Exception {
        loginMenuController.loginAsGuest();
        main.controller().mainMenu().start(stage);
    }
}
