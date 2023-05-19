package Controller;

import Model.Game;
import Model.User;
import javafx.application.Application;
import view.GameMenu;
import view.MainMenu;
import view.Login.PreLoginMenu;
import view.ProfileMenu.Avatar.AvatarMenu;
import view.ProfileMenu.Avatar.DefaultAvatarMenu;
import view.ProfileMenu.ChangePasswordMenu;
import view.ProfileMenu.ChangeUsernameMenu;
import view.ProfileMenu.ProfileMenu;
import view.SettingMenu;

import java.io.IOException;

public class Controller {
    private PreLoginMenu preLoginMenu = null;
    private ProfileMenu profileMenu = null;
    private MainMenu mainMenu = null;
    private ChangeUsernameMenu changeUsernameMenu = null;
    private ChangePasswordMenu changePasswordMenu = null;
    private DefaultAvatarMenu defaultAvatarMenu = null;
    private GameMenu gameMenu = null;
    private SettingMenu settingMenu = null;

    private AvatarMenu avatarMenu = null;

    private User currentUser;
    private Game currentGame;

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

    public void logout() {
        currentUser = null;
    }

    public void removeCurrentUser() throws IOException {
        if (currentUser==null) return;
        User.remove(currentUser);
        currentUser = null;
        User.writeUsers();
    }



}
