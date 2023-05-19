package Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameMap {
    public static ArrayList<GameMap> maps;
    public ArrayList<Integer> ballsPosition;

    static {
        maps = new ArrayList<>();
    }
    private GameMap(ArrayList<Integer> ballsPosition) {
        this.ballsPosition = ballsPosition;
    }
    public static void readMaps() {
        try{
            FileReader file = new FileReader("src/main/java/DB/Maps");
            Scanner scanner = new Scanner(file);
            if (!scanner.hasNextLine()) {
                scanner.close();
                file.close();
                return;
            }
            String allMaps = scanner.nextLine();
            scanner.close();
            file.close();
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(allMaps, JsonArray.class);
            maps.clear();
            for (JsonElement jsonElement : jsonArray) {
                maps.add(gson.fromJson(jsonElement, GameMap.class));
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public static void writeMaps()  {
        try{
            Gson gson = new Gson();
            FileWriter file = new FileWriter("src/main/java/DB/Maps");
            file.write(gson.toJson(maps));
            file.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }
    public static GameMap makeOne(ArrayList<Integer> ballsPosition){
        GameMap gameMap = new GameMap(ballsPosition);
        maps.add(gameMap);
        writeMaps();
        return gameMap;
    }


}
