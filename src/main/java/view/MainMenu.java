package view;

import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends Application {
    private static Stage stage;

    public void start(Stage stage) throws Exception {
        MainMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/MainMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
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

    public void enterSetting(MouseEvent mouseEvent) throws Exception {
        main.controller().settingMenu().start(stage);
    }
}
