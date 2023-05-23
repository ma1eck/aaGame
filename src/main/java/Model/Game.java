package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class Game {
    public static final int bigBallCenterX = 200;
    public static final int bigBallCenterY = 350;
    public static final int bigBallRadius = 30;
    public static final int smallBallsRadius = 7;
    public static final int smallBallsDistanceFromCenter = 100;
    public static final int shootingYPlayer1 = 600;
    public static final Color player1Color = Color.rgb(127, 62, 62);
    public static final int ballsToShootBeforeFreezing = 5;
    public static final int maxTimeOfAGame = 30;
    private GameSetting gameSetting;
    private ArrayList<Integer> mapBallsPositions;
    private ArrayList<SmallBall> smallBalls;
    private GameMap map;
    public double currentAngle;
    private Circle bigBall = null;
    private int ballsToShoot;
    private int score = 0;
    private double shootingAngle = 0;
    private int shootingXPlayer1 = bigBallCenterX;
    private int numberOFBallsShotFromPreviousFreeze = 0;

    public Game(GameSetting gameSetting) {
        map = gameSetting.map();
        this.gameSetting = gameSetting;
        this.currentAngle = 0;
        mapBallsPositions = map.ballsPosition;
        setSmallBalls();
        ballsToShoot = gameSetting.numberOfPlayableBalls();
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

    public double shootingAngle() {
        return shootingAngle;
    }

    public void setShootingAngle(double shootingAngle) {
        this.shootingAngle = shootingAngle;
    }

    public int shootingX() {
        return shootingXPlayer1;
    }


    public void changeShootingX(int amount) {
        this.shootingXPlayer1 += amount;
    }

    public SmallBall addABallWithAngle(double angle) {
        SmallBall smallBall = new SmallBall(angle, smallBallsRadius, getPositionOfAAngle(angle));
        smallBalls.add(smallBall);
        return smallBall;
    }

    public SmallBall addABallWithAngle(double angle, Color color) {
        SmallBall smallBall = new SmallBall(angle, smallBallsRadius, getPositionOfAAngle(angle), color);
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
    public GameDifficulty difficulty() {
        return gameSetting.difficulty();
    }

    public int timeBetweenChangingShootingAngle() {
        return gameSetting.difficulty().timeBetweenChangingShootingAngle();
    }
    public int invisibilityTime() {
        return gameSetting.difficulty().invisibilityDuration();
    }

    public double getAngleFromCoordinates(double x, double y) {
        double angle;
        double xDistance = x - bigBallCenterX;
        double yDistance = -(y - bigBallCenterY);
        angle = Math.toDegrees(Math.atan2(xDistance, yDistance));
        angle = (angle + 360) % 360;
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

    public int ballsToShoot() {
        return ballsToShoot;
    }

    public void decreaseBallsToShoot() {
        ballsToShoot--;
    }

    public void increaseScore() {
        score++;
    }

    public int score() {
        return score;
    }

    public int numberOfPlayableBalls() {
        return gameSetting.numberOfPlayableBalls();
    }


    public int numberOFBallsShotFromPreviousFreeze() {
        return numberOFBallsShotFromPreviousFreeze;
    }
    public void setNumberOFBallsShotFromPreviousFreeze0 (){
        numberOFBallsShotFromPreviousFreeze = 0;
    }
    public void increaseNumberOFBallsShotFromPreviousFreeze (){
        numberOFBallsShotFromPreviousFreeze++;
    }
}
