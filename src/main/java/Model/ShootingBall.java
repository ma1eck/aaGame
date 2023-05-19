package Model;

import javafx.scene.shape.Circle;

public class ShootingBall extends Circle {
    public static final int speed = 3;
    private double angle; // this is in degree

    public ShootingBall(double angle,int radius, int x, int y) {
        this.angle = angle;
        setRadius(radius);
        setCenterX(x);
        setCenterY(y);
    }
    public double xVelocity(){
        return  speed * Math.sin(Math.toRadians(angle));
    }
    public double yVelocity(){
        return speed * Math.cos(Math.toRadians(angle));
    }
}
