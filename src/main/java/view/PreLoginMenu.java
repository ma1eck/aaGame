package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class PreLoginMenu extends Application {
    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        PreLoginMenu.stage = stage;
        stage.setTitle("aa");
        URL url = PreLoginMenu.class.getResource("/FXML/PreLoginMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    public void login() throws Exception {
        System.out.println("want to login");
        new LoginMenu().start(stage);
    }
    public void signup() throws Exception {
        System.out.println("want to signup");
        new SignUpMenu().start(stage);
    }
    public void enterAsGuest(){
        System.out.println("want to enter as guest");
    }
}
