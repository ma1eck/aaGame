package Controller;

import Controller.Messages.LoginMessages;
import Model.User;
import view.main;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class ControllerLoginMenu {
    public LoginMessages login(String username, String password) throws NoSuchAlgorithmException {
        User user = User.getUser(username);
        if (user == null) return LoginMessages.USERNAME_DOESNT_EXIST;
        if (!user.checkPassword(password)) return LoginMessages.WRONG_PASSWORD;
        main.controller().setCurrentUser(user);
        return LoginMessages.SUCCESSFUL;
    }

    public LoginMessages signIn(String username, String password, String passwordConfirmation) throws IOException, NoSuchAlgorithmException {
        if (username.contains(" ") || password.contains(" "))
            return LoginMessages.HAS_SPACE;
        if (username.startsWith("Guest_"))
            return LoginMessages.GUEST_USERNAME;
        if (username.length() < 3) return LoginMessages.SHORT_USERNAME;
        if (User.getUser(username) != null) return LoginMessages.USERNAME_EXISTS;
        if (isPasswordWeek(password)) return LoginMessages.WEEK_PASSWORD;
        if (!password.equals(passwordConfirmation)) return LoginMessages.WRONG_CONFIRMATION;
        User user = User.makeUser(username, password);
        main.controller().setCurrentUser(user);
        return LoginMessages.SUCCESSFUL;
    }

    public LoginMessages loginAsGuest() throws IOException, NoSuchAlgorithmException {
        User user = User.makeGuest();
        main.controller().setCurrentUser(user);
        return LoginMessages.SUCCESSFUL;
    }

    public static boolean isPasswordWeek(String password) {
        if (password.length() < 8) return true;
        if (!password.matches(".*\\d.*") ||
                !password.matches(".*[a-zA-Z].*") ||
                !password.matches(".*\\W.*")) // password has to contain numbers and letters and symbols
            return true;
        return false;
    } // todo : check the method

}
