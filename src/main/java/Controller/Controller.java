package Controller;

import Model.User;
import javafx.application.Application;
import view.MainMenu;
import view.Login.PreLoginMenu;
import view.ProfileMenu.ChangePasswordMenu;
import view.ProfileMenu.ChangeUsernameMenu;
import view.ProfileMenu.ProfileMenu;

public class Controller {
    private PreLoginMenu preLoginMenu = null;
    private ProfileMenu profileMenu = null;
    private MainMenu mainMenu = null;
    private ChangeUsernameMenu changeUsernameMenu = null;
    private ChangePasswordMenu changePasswordMenu = null;

    private User currentUser;

    public User currentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public PreLoginMenu preLoginMenu() {
        if (preLoginMenu == null) preLoginMenu = new PreLoginMenu();
        return preLoginMenu;
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

    public void logout() {
        currentUser = null;
    }
}
