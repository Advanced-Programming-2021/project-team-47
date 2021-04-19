package View;

import Controller.GameProgramController;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardMenu implements Runnable{
    public static HashMap<Pattern, GameProgramController<Matcher>> commandMap = new HashMap<>();
    private static ScoreboardMenu scoreboardMenuSingleton;
    public TreeMap<String, Integer> scoreSorting = new TreeMap<>();

    public static ScoreboardMenu getInstance() {
        if (scoreboardMenuSingleton == null) {
            scoreboardMenuSingleton = new ScoreboardMenu();
        }
        return scoreboardMenuSingleton;
    }

    public void showScoreboard(String username) {

    }

    public void run() {

    }
}
