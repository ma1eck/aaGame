package Model;

import javafx.scene.shape.Circle;

public class SmallBall extends Circle {
    private double relativeAngle;

    public SmallBall(double relativeAngle, int radius, int[] coordinates) {
        this.relativeAngle = relativeAngle;
        this.setRadius(radius);
        this.setCenterX(coordinates[0]);
        this.setCenterY(coordinates[1]);
    }

    public void updateCoordinates(int[] coordinate ){
        this.setCenterX(coordinate[0]);
        this.setCenterY(coordinate[1]);
    }

    public double relativeAngle() {
        return relativeAngle;
    }
}
