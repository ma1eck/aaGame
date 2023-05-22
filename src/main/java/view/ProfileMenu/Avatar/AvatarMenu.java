package view.ProfileMenu.Avatar;

import Controller.ControllerAvatar;
import Utils.GetRandom;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainMenu;
import view.main;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;

public class AvatarMenu extends Application {
    private static Stage stage;
    private static ControllerAvatar controllerAvatar;


    public void start(Stage stage) throws Exception {
        controllerAvatar = new ControllerAvatar();
        AvatarMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/AvatarMenu.fxml");
        BorderPane pane = FXMLLoader.load(url);
        main.controller().setGrayScaleBaseOnSetting(pane);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void back() throws Exception {
        main.controller().profileMenu().start(stage);
    }

    public void avatarFromDevice(MouseEvent mouseEvent) throws Exception {
        File file = getFileFromDevice();
        if (file != null) {
            File destination = saveChosenAvatarToImages(file);
            controllerAvatar.setAvatar(destination.getName());
        }
        back();
    }

    private File saveChosenAvatarToImages(File file) throws IOException {
        File destination;
        do {
            destination = new File(AvatarMenu.class.getResource("/images/Avatars").toString().substring("file:/".length())
                    + "/" + GetRandom.getString(10) + "_" + file.getName());
        } while (destination.exists());
        Files.copy(file.toPath(), destination.toPath());
        return destination;
    }

    private File getFileFromDevice() {
        JFileChooser jfc = new JFileChooser();
        jfc.showDialog(null, "Please Select the File");
        jfc.setVisible(true);
        File file = jfc.getSelectedFile();
        return file;
    }

    public void defaultAvatars(MouseEvent mouseEvent) throws Exception {
        main.controller().defaultAvatarMenu().start(stage);
    }
}
