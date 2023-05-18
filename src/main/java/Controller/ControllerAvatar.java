package Controller;

import Model.User;
import view.main;

import java.io.IOException;

public class ControllerAvatar {
    public void setAvatar(String avatar) throws IOException {
        main.controller().currentUser().setAvatar(avatar);
        User.writeUsers();
    }
}
