package view;

import Controller.Controller;
import Model.GameMap;
import Model.User;
import view.Login.PreLoginMenu;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class main {
    private static Controller controller = new Controller();
    private  static String[] args;

    public static String[] args() {
        return args;
    }

    public static Controller controller() {
        return controller;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException { //run this
        GameMap.readMaps();
        User.readUsers();
        User.pureUsers();
        main.args = args;
        PreLoginMenu.main(args);
    }
}
