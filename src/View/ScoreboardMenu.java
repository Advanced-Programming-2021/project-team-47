package View;

import Model.Menus;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
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

    public void showCurrentMenu() {
        Menus current = LoginMenu.currentMenu;
        System.out.println(current.label);
    }
}
