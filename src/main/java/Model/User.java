package Model;

import Controller.ControllerLoginMenu;
import Utils.GetRandom;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import Utils.makeHash;
import com.google.gson.*;


public class User {
    private static ArrayList<User> users;
    private String username = null;
    private String passwordHash = null;

    static {
        users = new ArrayList<>();
    }

    private User(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        this.passwordHash = makeHash.toSHA256(password);
    }

    public static User makeUser(String username, String password) throws IOException, NoSuchAlgorithmException {
        User user = new User(username, password);
        users.add(user);
        writeUsers();
        return user;
    }

    public static User makeGuest() throws IOException, NoSuchAlgorithmException {
        String randomUsername = makeARandomUsername();
        String randomPassword = makeARandomPassword();
        return makeUser(randomUsername, randomPassword);
    }

    private static String makeARandomPassword() {
        StringBuilder randomPassword;
        String password;

        do {
            randomPassword = new StringBuilder();
            int size = GetRandom.getInt(10, 20);
            for (int i = 0; i < size; i++) {
                randomPassword.append((char) GetRandom.getInt('!', '}'));//all printable char
            }
            password = randomPassword.toString();
        } while (ControllerLoginMenu.isPasswordWeek(password));

        return password;
    }

    private static String makeARandomUsername() {
        StringBuilder randomUsername;
        String username;
        do {
            randomUsername = new StringBuilder();
            int size = GetRandom.getInt(2, 7);
            for (int i = 0; i < size; i++) {
                randomUsername.append((char) GetRandom.getInt('a', 'z'));
            }
            for (int i = size; i < 7; i++) {
                randomUsername.append((char) GetRandom.getInt('1', '9'));
            }
            username = "Guest_" + randomUsername.toString();
        } while (getUser(username) != null);
        return username;
    }

    public static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    public static void pureUsers() throws IOException { // delete Guests
        for (int i=users.size()-1; i>=0 ; i--){
            if (users.get(i).isGuest()) users.remove(i);
        }
        writeUsers();
    }

    private boolean isGuest() {
        return this.username.startsWith("Guest_");
    }

    public boolean checkPassword(String password) throws NoSuchAlgorithmException {
        String toHashPassword = makeHash.toSHA256(password);
        return this.passwordHash.equals(toHashPassword);
    }

    public String getUsername() {
        return username;
    }

    public static void readUsers() throws IOException {
        FileReader file = new FileReader("src/main/java/DB/Users");
        Scanner scanner = new Scanner(file);
        if (!scanner.hasNextLine()) {
            scanner.close();
            file.close();
            return;
        }
        String allUsers = scanner.nextLine();
        scanner.close();
        file.close();
        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(allUsers, JsonArray.class);
        users.clear();
        for (JsonElement jsonElement : jsonArray) {
            users.add(gson.fromJson(jsonElement, User.class));
        }
    }

    public static void writeUsers() throws IOException {
        Gson gson = new Gson();
        FileWriter file = new FileWriter("src/main/java/DB/Users");
        file.write(gson.toJson(users));
        file.close();
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        passwordHash = makeHash.toSHA256(password);
    }

    public void setUsername(String newUsername) {
        username = newUsername;
    }
}
