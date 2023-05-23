package Model;

import javafx.scene.paint.Color;

public class TwoPlayerGame extends Game{
    public static final Color player2Color = Color.rgb(181,50,195);
    private int shootingXPlayer2 = bigBallCenterX;
    public static final int shootingYPlayer2 = 100;
    public TwoPlayerGame(GameSetting gameSetting) {
        super(gameSetting);
    }

    public int shootingYPlayer2() {
        return shootingYPlayer2;
    }

    public int shootingXPlayer2() {
        return shootingXPlayer2;
    }

    public void changeShootingXPlayer2(int amount) {
        this.shootingXPlayer2 += amount;
    }
}
