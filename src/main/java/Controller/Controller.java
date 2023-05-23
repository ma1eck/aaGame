package Controller;

import Model.Game;
import Model.TwoPlayerGame;
import Model.User;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.*;
import view.Login.PreLoginMenu;
import view.ProfileMenu.Avatar.AvatarMenu;
import view.ProfileMenu.Avatar.DefaultAvatarMenu;
import view.ProfileMenu.ChangePasswordMenu;
import view.ProfileMenu.ChangeUsernameMenu;
import view.ProfileMenu.ProfileMenu;

import java.io.File;
import java.net.MalformedURLException;
import java.security.spec.ECField;

public class Controller {
    private PreLoginMenu preLoginMenu = null;
    private ProfileMenu profileMenu = null;
    private MainMenu mainMenu = null;
    private ChangeUsernameMenu changeUsernameMenu = null;
    private ChangePasswordMenu changePasswordMenu = null;
    private DefaultAvatarMenu defaultAvatarMenu = null;
    private ScoreboardMenu scoreboardMenu = null;
    private GameMenu gameMenu = null;
    private TwoPlayerGameMenu twoPlayerGameMenu = null;
    private SettingMenu settingMenu = null;

    private AvatarMenu avatarMenu = null;
    private Stage stage;

    private User currentUser;
    private Game currentGame;
    private TwoPlayerGame currentTwoPlayerGame;

    public User currentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Game currentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public TwoPlayerGame currentTwoPlayerGame() {
        return currentTwoPlayerGame;
    }

    public void setCurrentTwoPlayerGame(TwoPlayerGame currentTwoPlayerGame) {
        this.currentTwoPlayerGame = currentTwoPlayerGame;
    }

    public PreLoginMenu preLoginMenu() {
        if (preLoginMenu == null) preLoginMenu = new PreLoginMenu();
        return preLoginMenu;
    }

    public DefaultAvatarMenu defaultAvatarMenu() {
        if (defaultAvatarMenu == null) defaultAvatarMenu = new DefaultAvatarMenu();
        return defaultAvatarMenu;
    }

    public ProfileMenu profileMenu() {
        if (profileMenu == null) profileMenu = new ProfileMenu();
        return profileMenu;
    }

    public MainMenu mainMenu() {
        if (mainMenu == null) mainMenu = new MainMenu();
        return mainMenu;
    }

    public ChangeUsernameMenu changeUsernameMenu() {
        if (changeUsernameMenu == null) changeUsernameMenu = new ChangeUsernameMenu();
        return changeUsernameMenu;
    }

    public ChangePasswordMenu changePasswordMenu() {
        if (changePasswordMenu == null) changePasswordMenu = new ChangePasswordMenu();
        return changePasswordMenu;
    }

    public AvatarMenu avatarMenu() {
        if (avatarMenu == null) avatarMenu = new AvatarMenu();
        return avatarMenu;
    }

    public SettingMenu settingMenu() {
        if (settingMenu == null) settingMenu = new SettingMenu();
        return settingMenu;
    }

    public GameMenu gameMenu() {
        if (gameMenu == null) gameMenu = new GameMenu();
        return gameMenu;
    }

    public TwoPlayerGameMenu twoPlayerGameMenu() {
        if (twoPlayerGameMenu == null) twoPlayerGameMenu = new TwoPlayerGameMenu();
        return twoPlayerGameMenu;
    }

    public ScoreboardMenu scoreboardMenu() {
        if (scoreboardMenu == null) scoreboardMenu = new ScoreboardMenu();
        return scoreboardMenu;
    }

    public void logout() {
        currentUser = null;
    }

    public void removeCurrentUser() {
        if (currentUser == null) return;
        User.remove(currentUser);
        currentUser = null;
        User.writeUsers();
    }

    public void setGrayScaleBaseOnSetting(Pane pane) {
        if (currentUser == null || pane == null) return;
        if (currentUser.isGrayScale()) {
            ColorAdjust grayScale = new ColorAdjust();
            grayScale.setSaturation(-1.0);
            pane.setEffect(grayScale);
        } else {
            pane.setEffect(null);
        }
    }

    public Stage stage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public MediaPlayer mediaPlayer;

    public void setDefaultMusic() {
        String address = PreLoginMenu.class.getResource("/music/Mozart-Serenade-in-G-major.mp3")
                .toString();
        System.out.println(address);
        Media media = new Media(address);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        playMusicDependingOnSetting();
    }

    public void playMusicDependingOnSetting() {
        if (currentUser == null) mediaPlayer.play();
        else mediaPlayer.setMute(currentUser.isMute());
    }

    public void reverseMuteBoolean() {
        currentUser.setMute(!currentUser.isMute());
    }

    public void setMusicByIndex(int index) {
        mediaPlayer.stop();
        File folder = new File(
                Controller.class.getResource(
                        "/music").toString().substring("file:/".length()));
        File[] musics = folder.listFiles();
        String address = null;
        try {
            address = musics[index].toURI().toString();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(address);
        Media media = new Media(address);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        playMusicDependingOnSetting();
    }
}
