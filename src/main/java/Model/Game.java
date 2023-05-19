package Model;

import java.util.ArrayList;

public class Game {
    public static final int bigBallCenterX = 200;
    public static final int bigBallCenterY = 200;
    public static final int BigBallRadius = 50;
    public static final int smallBallsRadius = 10;
    public static final int smallBallsDistanceFromCenter = 100;
    private GameSetting gameSetting;
    public ArrayList<Integer> ballsPosition;
    private GameMap map;
    int currentAngle;

    public Game(GameSetting gameSetting) {
        map = gameSetting.map();
        this.gameSetting = gameSetting;
        this.currentAngle = 0;
        ballsPosition = map.ballsPosition;
    }

    public int[] getPositionOfAAngle(int angle) {
        int finalAngle = angle + currentAngle;
        double angleInRadian = Math.toRadians(finalAngle);
        return new int[]{
                (int) (Math.sin(angleInRadian) * (smallBallsDistanceFromCenter) + bigBallCenterX),
                (int) ( - Math.cos(angleInRadian) * (smallBallsDistanceFromCenter) + bigBallCenterY)
        };
    }
    public void addABallWithAbsoluteAngle(int angle){
        ballsPosition.add(angle);
    }
}
