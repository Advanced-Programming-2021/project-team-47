package View;

import Controller.GameProgramController;
import Model.Menus;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu implements Runnable{
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static MainMenu mainMenuSingleton;

    public static MainMenu getInstance() {
        if (mainMenuSingleton == null) {
            mainMenuSingleton = new MainMenu();
        }
        return mainMenuSingleton;
    }

    public void run() {

    }

    public void showCurrentMenu() {
        Menus current = LoginMenu.currentMenu;
        System.out.println(current.label);
    }
}
