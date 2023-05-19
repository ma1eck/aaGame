package Model;

import java.util.ArrayList;
import java.util.List;

public class GameSetting {
    private GameDifficulty difficulty;
    private int numberOfPlayableBalls;
    private GameMap map;

    public GameSetting() {
        difficulty = GameDifficulty.MEDIUM;
        if (GameMap.maps.size() != 0){
            map = GameMap.maps.get(0);
        }else {
            GameMap.makeOne(new ArrayList<>(List.of(new Integer[]{75, 270})));
        }
        numberOfPlayableBalls = 15;
    }

    public GameSetting(GameDifficulty difficulty, int numberOfPlayableBalls, GameMap map) {
        this.difficulty = difficulty;
        this.numberOfPlayableBalls = numberOfPlayableBalls;
        this.map = map;
    }

    public GameDifficulty difficulty() {
        return difficulty;
    }

    public void setDifficulty(GameDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int numberOfPlayableBalls() {
        return numberOfPlayableBalls;
    }

    public void setNumberOfPlayableBalls(int numberOfPlayableBalls) {
        this.numberOfPlayableBalls = numberOfPlayableBalls;
    }

    public GameMap map() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }
}
