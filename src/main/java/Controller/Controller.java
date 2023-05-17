package Controller;

import Model.User;
import view.LoginMenu;
import view.PreLoginMenu;

public class Controller {
    private PreLoginMenu preLoginMenu = null;
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
}
