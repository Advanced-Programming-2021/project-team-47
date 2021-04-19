package View;

import Controller.GameProgramController;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu implements Runnable{
    public static HashMap<Pattern, GameProgramController<Matcher>> commandMap = new HashMap<>();
    private static MainMenu mainMenuSingleton;

    public static MainMenu getInstance() {
        if (mainMenuSingleton == null) {
            mainMenuSingleton = new MainMenu();
        }
        return mainMenuSingleton;
    }

    public void run() {

    }
}
