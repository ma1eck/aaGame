package Controller;

import Controller.Messages.LoginMessages;
import Controller.Messages.ProfileMessages;
import Model.User;
import view.ProfileMenu.ProfileMenu;
import view.main;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ControllerProfileMenu {

    public ProfileMessages changePassword(String newPass) throws NoSuchAlgorithmException, IOException {
        if (newPass.contains(" "))
            return ProfileMessages.HAS_SPACE;
        User currentUser = main.controller().currentUser();
        if (ControllerLoginMenu.isPasswordWeek(newPass)) return ProfileMessages.WEEK_PASSWORD;
        currentUser.setPassword(newPass);
        User.writeUsers();
        return ProfileMessages.SUCCESSFUL;
    }

    public ProfileMessages changeUsername(String newUsername) throws IOException {
        if (newUsername.contains(" ") )
            return ProfileMessages.HAS_SPACE;
        if (newUsername.length()<3) return ProfileMessages.SHORT_USERNAME;
        if (User.getUser(newUsername) != null) return ProfileMessages.USERNAME_EXISTS;
        User currentUser = main.controller().currentUser();
        currentUser.setUsername(newUsername);
        User.writeUsers();
        return ProfileMessages.SUCCESSFUL;
    }
}
