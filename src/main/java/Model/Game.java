package Model;

import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Game {
    public static final int bigBallCenterX = 200;
    public static final int bigBallCenterY = 200;
    public static final int bigBallRadius = 30;
    public static final int smallBallsRadius = 7;
    public static final int smallBallsDistanceFromCenter = 120;
    public static final int shootingY = 500;
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
        for (double angle : mapBallsPositions) {
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

    public SmallBall addABallWithAngle(double angle) {
        SmallBall smallBall = new SmallBall(angle, smallBallsRadius, getPositionOfAAngle(angle));
        smallBalls.add(smallBall);
        return smallBall;
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

    public double getAngleFromCoordinates(double x, double y) {
        double angle;
        double xDistance = x - bigBallCenterX;
        double yDistance = -(y - bigBallCenterY);
        angle =  Math.toDegrees(Math.atan2(xDistance,yDistance));
        angle = (angle+360)%360;
        return angle;
    }
    public double getDistanceToCenter(double x, double y) {
        double xDistance = x - bigBallCenterX;
        double yDistance = y - bigBallCenterY;
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
    }

    public int freezeTime() {
        return gameSetting.difficulty().frozenTimer();
    }
}
