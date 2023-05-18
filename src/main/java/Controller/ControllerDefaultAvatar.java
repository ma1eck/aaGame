package Controller;

import view.main;

public class ControllerDefaultAvatar {
    public void setAvatar(String avatar){
        main.controller().currentUser().setAvatar(avatar);
    }
}
