package View;

import Controller.MenuProgramController;
import Model.Menus;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ProfileMenu profileMenuSingleton;

    public static ProfileMenu getInstance() {
        if (profileMenuSingleton == null) {
            profileMenuSingleton = new ProfileMenu();
        }
        return profileMenuSingleton;
    }

    public void run(String command) {

    }

    public void showCurrentMenu() {
        Menus current = MenuProgramController.currentMenu;
        System.out.println(current.label);
    }
}
