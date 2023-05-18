package view.ProfileMenu;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import view.MainMenu;
import view.main;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.FileNameMap;
import java.net.URL;
import javafx.scene.shape.Rectangle;

public class ProfileMenu extends Application {
    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/ProfileMenu.fxml");
        HBox pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void changeUsername(MouseEvent mouseEvent) throws Exception {
        main.controller().changeUsernameMenu().start(stage);
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception {
        main.controller().changePasswordMenu().start(stage);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        main.controller().mainMenu().start(stage);
    }

    public void logout(MouseEvent mouseEvent) throws Exception {
        main.controller().logout();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("You logged out");
        alert.showAndWait();
        main.controller().preLoginMenu().start(stage);
    }

    public void removeAccount(MouseEvent mouseEvent) throws Exception {
        if (getConfirmationToRemove()) {
            main.controller().removeCurrentUser();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("You have remove an account successfully");
            alert.showAndWait();
            main.controller().preLoginMenu().start(stage);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Removing account got cancelled");
            alert.showAndWait();
        }
    }

    private boolean getConfirmationToRemove() {
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(100, 75));
        panel.setLayout(null);
        JLabel label1 = new JLabel("Ary you sure ?");
        label1.setVerticalAlignment(SwingConstants.BOTTOM);
        label1.setBounds(10, 10, 100, 30);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label1);
        UIManager.put("OptionPane.minimumSize", new Dimension(200, 150));
        int confirmation = JOptionPane.showConfirmDialog(null, panel, "removing account",
                JOptionPane.YES_NO_OPTION);
        return confirmation == 0;
    }

    @FXML
    private Label usernameLabel;
    @FXML
    private VBox vBoxAvatar;
    @FXML
    private void initialize(){
        usernameLabel.setText(main.controller().currentUser().getUsername());
        ImagePattern imagePattern = getAvatar();
        if (imagePattern==null) return;
        ((Rectangle)vBoxAvatar.getChildren().get(1)).setFill(imagePattern);
    }

    private static ImagePattern getAvatar() {
        try{
            URL url = ProfileMenu.class.getResource("/images/Avatars/" + main.controller().currentUser().avatar());
            Image image = new Image(url.toExternalForm());
            ImagePattern imagePattern = new ImagePattern(image);
            return imagePattern;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void avatar(MouseEvent mouseEvent) throws Exception {
        main.controller().avatarMenu().start(stage);
    }
}
