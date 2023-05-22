package Controller;

import Model.GameDifficulty;
import Model.User;
import view.main;

import java.io.IOException;

public class ControllerSetting {
    public GameDifficulty getDifficulty() {
        return main.controller().currentUser().difficulty();
    }

    public void setDifficulty(GameDifficulty difficulty) throws IOException {
        main.controller().currentUser().setDifficulty(difficulty);
        User.writeUsers();
    }

    public Integer getPlayableBalls() {
        return main.controller().currentUser().getPlayableBalls();
    }

    public void setPlayableBalls(Integer number) {
        main.controller().currentUser().setPlayableBalls(number);
    }

    public void setMute(boolean isMute) {
        main.controller().currentUser().setMute(isMute);
    }

    public boolean isMute() {
        return main.controller().currentUser().isMute();
    }

    public void setGrayScale(boolean isGrayScale) {
        main.controller().currentUser().setGrayScale(isGrayScale);
    }

    public boolean isGrayScale() {
        return main.controller().currentUser().isGrayScale();
    }

    public void setGameMap(int index) {
        main.controller().currentUser().setMapByIndex(index);
    }
}
