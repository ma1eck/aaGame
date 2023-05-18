package view.ProfileMenu.Avatar;

import Controller.ControllerDefaultAvatar;
import Utils.GetRandom;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import view.MainMenu;
import view.ProfileMenu.ProfileMenu;
import view.main;

import java.awt.*;
import java.io.File;
import java.net.URL;
import javafx.scene.shape.Rectangle;
public class DefaultAvatarMenu extends Application {
    private static Stage stage;
    private static File[] avatars;
    private static ControllerDefaultAvatar controllerDefaultAvatar;

    @Override
    public void start(Stage stage) throws Exception {
        controllerDefaultAvatar = new ControllerDefaultAvatar();
        DefaultAvatarMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/DefaultAvatarMenu.fxml"); // there should be exactly 4 avatar
        VBox pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Rectangle rect1;
    @FXML
    private Rectangle rect2;
    @FXML
    private Rectangle rect3;
    @FXML
    private Rectangle rect4;

    @FXML
    private void initialize() {
        try {
            File folder = new File(
                    DefaultAvatarMenu.class.getResource(
                            "/images/Avatars/default").toString().substring("file:/".length())); // todo clean
            avatars = folder.listFiles();
            rect1.setFill(getImage(avatars[0]));
            rect2.setFill(getImage(avatars[1]));
            rect3.setFill(getImage(avatars[2]));
            rect4.setFill(getImage(avatars[3]));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
    private static ImagePattern getImage(File file) {
        try{
            String path = file.getPath();
            javafx.scene.image.Image image = new Image(path);
            ImagePattern imagePattern = new ImagePattern(image);
            return imagePattern;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    public void back() throws Exception {
        main.controller().avatarMenu().start(stage);
    }


    public void random(MouseEvent mouseEvent) throws Exception {
        int avatarIndex = GetRandom.getInt(0,avatars.length);
        controllerDefaultAvatar.setAvatar("default/"+avatars[avatarIndex].getName());
        main.controller().profileMenu().start(stage);
    }
// todo : clean these
    public void setRect1(MouseEvent mouseEvent) throws Exception {
        try{
            controllerDefaultAvatar.setAvatar("default/" + avatars[0].getName());
            main.controller().profileMenu().start(stage);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void setRect2(MouseEvent mouseEvent) throws Exception {
        try{
            controllerDefaultAvatar.setAvatar("default/" + avatars[1].getName());
            main.controller().profileMenu().start(stage);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void setRect3(MouseEvent mouseEvent) throws Exception {
        try{
            controllerDefaultAvatar.setAvatar("default/" + avatars[2].getName());
            main.controller().profileMenu().start(stage);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public void setRect4(MouseEvent mouseEvent) throws Exception {
        try{
            controllerDefaultAvatar.setAvatar("default/" + avatars[3].getName());
            main.controller().profileMenu().start(stage);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
