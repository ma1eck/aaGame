package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ShootingBall extends Circle {
    public static final int speed = 10;
    private double angle; // this is in degree

    public ShootingBall(double angle,int radius, int x, int y) {
        this.angle = angle;
        setRadius(radius);
        setCenterX(x);
        setCenterY(y);
    }
    public ShootingBall(double angle,int radius, int x, int y, Color color) {
        this.angle = angle;
        setRadius(radius);
        setCenterX(x);
        setCenterY(y);
        setFill(color);
    }
    public double xVelocity(){
        return  speed * Math.sin(Math.toRadians(angle));
    }
    public double yVelocity(){
        return speed * Math.cos(Math.toRadians(angle));
    }
}
