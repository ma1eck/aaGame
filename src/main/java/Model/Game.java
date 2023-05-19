package Model;

import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    public static final int bigBallCenterX = 200;
    public static final int bigBallCenterY = 200;
    public static final int bigBallRadius = 30;
    public static final int smallBallsRadius = 7;
    public static final int smallBallsDistanceFromCenter = 120;
    private GameSetting gameSetting;
    private ArrayList<Integer> mapBallsPositions;
    private ArrayList<SmallBall> smallBalls;
    private GameMap map;
    public double currentAngle; //todo
    private Circle bigBall = null;

    public Game(GameSetting gameSetting) {
        map = gameSetting.map();
        this.gameSetting = gameSetting;
        this.currentAngle = 0;
        mapBallsPositions = map.ballsPosition;
        setSmallBalls();

    }

    private void setSmallBalls() {
        smallBalls = new ArrayList<>();
        for (int angle : mapBallsPositions) {
            SmallBall smallBall = new SmallBall(angle, smallBallsRadius, getPositionOfAAngle(angle));
            smallBalls.add(smallBall);
        }
    }

    public int[] getPositionOfAAngle(double angle) {
        double finalAngle = angle + currentAngle;
        double angleInRadian = Math.toRadians(finalAngle);
        return new int[]{
                (int) (Math.sin(angleInRadian) * (smallBallsDistanceFromCenter) + bigBallCenterX),
                (int) (-Math.cos(angleInRadian) * (smallBallsDistanceFromCenter) + bigBallCenterY)
        };
    }

    public void addABallWithAbsoluteAngle(int angle) {
        SmallBall smallBall = new SmallBall(angle, smallBallsRadius, getPositionOfAAngle(angle));
        smallBalls.add(smallBall);
    }

    public Circle getBigBall() {
        if (bigBall == null) {
            bigBall = new Circle();
            bigBall.setRadius(bigBallRadius);
            bigBall.setCenterX(bigBallCenterX);
            bigBall.setCenterY(bigBallCenterY);
        }
        return bigBall;
    }

    public void increaseAngle(double amount) {
        currentAngle += amount;
    }

    public ArrayList<SmallBall> smallBalls() {
        return smallBalls;
    }

    public int rotatingRate() {
        return gameSetting.difficulty().rotateSpeed();
    }

    public double getRelativeAngleFromCoordinates(int x, int y) {
        int angle;
        int xDistance = x - bigBallCenterX;
        int yDistance = -(y - bigBallCenterY);
        angle = (int) Math.toDegrees(Math.atan2(xDistance,yDistance));
        angle = (angle+360)%360;
        return angle;
    }
    public int getDistanceToCenter(int x, int y) {
        int xDistance = x - bigBallCenterX;
        int yDistance = y - bigBallCenterY;
        return (int) Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }
}
