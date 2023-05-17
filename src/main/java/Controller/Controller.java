package Controller;

import Model.User;
import javafx.application.Application;
import view.LoginMenu;
import view.MainMenu;
import view.PreLoginMenu;
import view.ProfileMenu;

public class Controller {
    private PreLoginMenu preLoginMenu = null;
    private ProfileMenu profileMenu = null;
    private MainMenu mainMenu = null;
    private User currentUser;

    public User currentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public PreLoginMenu preLoginMenu(){
        if (preLoginMenu == null) preLoginMenu = new PreLoginMenu();
        return preLoginMenu;
    }
    public ProfileMenu profileMenu(){
        if (profileMenu == null) profileMenu = new ProfileMenu();
        return profileMenu;
    }

    public MainMenu mainMenu() {
        if ( mainMenu == null) mainMenu = new MainMenu();
        return mainMenu;
    }
}
