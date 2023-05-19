package view;

import Controller.ControllerSetting;
import Model.GameDifficulty;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.IllegalCharsetNameException;
import java.util.ArrayList;
import java.util.Collection;

import javafx.scene.control.Label;

public class SettingMenu extends Application {
    private static Stage stage;
    private static ControllerSetting controller;

    @Override
    public void start(Stage stage) throws Exception {
        controller = new ControllerSetting();
        SettingMenu.stage = stage;
        stage.setTitle("aa");
        URL url = MainMenu.class.getResource("/FXML/SettingMenu.fxml");
        Pane pane = FXMLLoader.load(url);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private ChoiceBox<GameDifficulty> difficultyChoiceBox;
    @FXML
    private ChoiceBox<Integer> playableBallsChoiceBox;
    @FXML
    private ChoiceBox<String> languageChoiceBox; // todo
    @FXML
    private Label languageLabel;
    @FXML
    private Label playableBallsLabel;
    @FXML
    private Label difficultyLabel;
    @FXML
    private CheckBox muteCheckbox;
    @FXML
    private CheckBox grayScaleCheckBox;
    @FXML
    private void initialize() {

        difficultyChoiceBox.getItems().clear();
        playableBallsChoiceBox.getItems().clear();
        languageChoiceBox.getItems().clear();
        difficultyChoiceBox.getItems().addAll(GameDifficulty.values());
        difficultyLabel.setText(controller.getDifficulty().toString());
        setPlayableBallsChoiceBox();
        setLanguageChoiceBox();
        languageLabel.setText("English");
        playableBallsLabel.setText(controller.getPlayableBalls().toString());
        muteCheckbox.setSelected(controller.isMute());
        grayScaleCheckBox.setSelected(controller.isGrayScale());

    }

    private void setLanguageChoiceBox() {
        ArrayList<String> languages = new ArrayList<>();
        languages.add("English");
        languageChoiceBox.getItems().addAll(languages);
    }

    private void setPlayableBallsChoiceBox() {
        ArrayList<Integer> playableBalls = new ArrayList<>();
        for (int i = 3; i <= 40; i++) {
            playableBalls.add(i);
        }
        playableBallsChoiceBox.getItems().addAll(playableBalls);
    }


    public void back() throws Exception {
        main.controller().mainMenu().start(stage);
    }

    public void setDifficulty(ActionEvent actionEvent) throws IOException {
        controller.setDifficulty(difficultyChoiceBox.getValue());
    }

    public void setPlayableBalls(ActionEvent actionEvent) throws IOException {
        controller.setPlayableBalls(playableBallsChoiceBox.getValue());
    }

    public void setMute(ActionEvent actionEvent) {
        controller.setMute(muteCheckbox.isSelected());
    }

    public void setGrayScale(ActionEvent actionEvent) {
        controller.setGrayScale(grayScaleCheckBox.isSelected());
    }
}