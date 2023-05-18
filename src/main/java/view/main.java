package view;

import Controller.Controller;
import Model.User;
import Utils.GetRandom;
import view.Login.PreLoginMenu;
import view.ProfileMenu.Avatar.AvatarMenu;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        User.readUsers();
        User.pureUsers();
        main.args = args;
        PreLoginMenu.main(args);

//        JFileChooser jfc = new JFileChooser();
//        jfc.showDialog(null,"Please Select the File");
//        jfc.setVisible(true);
//        File file = jfc.getSelectedFile();
//        System.out.println("File name "+file.getName());
//        File destination = new File(
//                AvatarMenu.class.getResource("/images/Avatars").toString().substring("file:".length())+"/"+file.getName());
//        System.out.println(file.getPath());
//        Files.copy(file.toPath(),destination.toPath());
    }
}
