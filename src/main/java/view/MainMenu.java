package view;

import Controller.ControllerMainMenu;
import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends Application {
    private static Stage stage;
    private static ControllerMainMenu controllerMainMenu;

    public void start(Stage stage) throws Exception {
        main.controller().playMusicDependingOnSetting();
        controllerMainMenu = new ControllerMainMenu();
        MainMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/MainMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        main.controller().setGrayScaleBaseOnSetting(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void enterProfileMenu(MouseEvent mouseEvent) throws Exception {
        main.controller().profileMenu().start(stage);
    }

    public void exitGame(MouseEvent mouseEvent) throws IOException {
        if (getConfirmationToExit()){
            User.pureUsers();
            User.writeUsers();
            System.exit(0);
        }
    }

    private boolean  getConfirmationToExit() {
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(100, 75));
        panel.setLayout(null);
        JLabel label1 = new JLabel("Ary you sure?");
        label1.setVerticalAlignment(SwingConstants.BOTTOM);
        label1.setBounds(10, 10, 100, 30);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label1);
        UIManager.put("OptionPane.minimumSize", new Dimension(200, 150));
        int confirmation = JOptionPane.showConfirmDialog(null, panel, "exiting",
                JOptionPane.YES_NO_OPTION);
        return confirmation == 0;
    }

    public void enterSetting() throws Exception {
        main.controller().settingMenu().start(stage);
    }

    public void newGame() throws Exception {
        controllerMainMenu.newGame();
        main.controller().gameMenu().start(stage);
    }

    public void enterScoreboard() throws Exception {
        main.controller().scoreboardMenu().start(stage);
    }

    public void twoPlayerGame() throws Exception {
        controllerMainMenu.newTwoPlayerGame();
        main.controller().twoPlayerGameMenu().start(stage);
    }
}
