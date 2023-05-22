package Controller;

import Model.GameDifficulty;
import Model.User;

import java.util.ArrayList;

public class ScoreboardController {
    public String[][] getTopPlayers(GameDifficulty difficulty) {
        ArrayList<User> topPlayers = User.getTop10Players(difficulty);
        String[][] result = new String[topPlayers.size()][4];
        Integer rank = 1;
        for (User user : topPlayers) {
            result[rank - 1][3] = rank.toString();
            result[rank - 1][2] = user.getUsername();
            result[rank - 1][1] = String.valueOf(user.getTimeSpent(difficulty));
            result[rank - 1][0] = ((Integer) user.getScore(difficulty)).toString();
            rank++;
        }
        return result;
    }
}
