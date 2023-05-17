package view;

import Controller.Controller;
import Model.User;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
        FileWriter file = new FileWriter("src/main/java/DB/Users");
        Gson gson = new Gson();
        ArrayList<User> users = new ArrayList<>();
        users.add(User.makeGuest());
        System.out.println(gson.toJson(users));
//        User.readUsers();
//        main.args = args;
//        PreLoginMenu.main(args);
        // write users
    }
}
