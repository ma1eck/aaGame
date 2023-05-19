package Model;

public enum GameDifficulty {
    EASY(5,1.2,7,"easy"),
    MEDIUM(10,1.5,5,"medium"),
    HARD(15,1.8,3,"hard")

    ;
    private int rotateSpeed;
    private double windSpeed;
    private int frozenTimer;
    private String name;

    GameDifficulty(int rotateSpeed, double windSpeed, int frozenTimer,String name) {
        this.rotateSpeed = rotateSpeed;
        this.windSpeed = windSpeed;
        this.frozenTimer = frozenTimer;
        this.name = name;
    }

    public int rotateSpeed() {
        return rotateSpeed;
    }

    public double windSpeed() {
        return windSpeed;
    }

    public int frozenTimer() {
        return frozenTimer;
    }

    @Override
    public String toString() {
        return name;
    }

    public static GameDifficulty getByName(String name){
        for (GameDifficulty difficulty : GameDifficulty.values()){
            if (difficulty.name.equals(name)) return difficulty;
        }
        return null;
    }
}
