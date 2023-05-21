package Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SmallBall extends Circle {
    private double relativeAngle;

    public SmallBall(double relativeAngle, int radius, int[] coordinates) {
        this.relativeAngle = relativeAngle;
        this.setRadius(radius);
        this.setCenterX(coordinates[0]);
    }
    public SmallBall(double relativeAngle, int radius, int[] coordinates, Color color) {
        this.relativeAngle = relativeAngle;
        this.setRadius(radius);
        this.setCenterX(coordinates[0]);
        this.setFill(color);
    }

    public void updateCoordinates(int[] coordinate ){
        this.setCenterX(coordinate[0]);
        this.setCenterY(coordinate[1]);
    }

    public double relativeAngle() {
        return relativeAngle;
    }
}
