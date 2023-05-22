package Model;

public enum GameDifficulty {
    EASY(5, 6, 7, 1 , "easy"),
    MEDIUM(10, 4, 5,2, "medium"),
    HARD(15, 2, 3,3, "hard");
    private int rotateSpeed;
    private int timeBetweenChangingShootingAngle;
    private int frozenTimer;
    private String name;
    private int invisibilityDuration;

    GameDifficulty(int rotateSpeed, int timeBetweenChangingShootingAngle, int frozenTimer,
                   int invisibilityDuration, String name) {
        this.rotateSpeed = rotateSpeed;
        this.timeBetweenChangingShootingAngle = timeBetweenChangingShootingAngle;
        this.frozenTimer = frozenTimer;
        this.name = name;
        this.invisibilityDuration = invisibilityDuration;
    }

    public int rotateSpeed() {
        return rotateSpeed;
    }

    public int timeBetweenChangingShootingAngle() {
        return timeBetweenChangingShootingAngle;
    }

    public int frozenTimer() {
        return frozenTimer;
    }

    @Override
    public String toString() {
        return name;
    }

    public static GameDifficulty getByName(String name) {
        for (GameDifficulty difficulty : GameDifficulty.values()) {
            if (difficulty.name.equals(name)) return difficulty;
        }
        return null;
    }

    public int invisibilityDuration() {
        return invisibilityDuration;
    }
}
