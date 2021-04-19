package View;

import Controller.GameProgramController;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu implements Runnable{
    public static HashMap<Pattern, GameProgramController<Matcher>> commandMap = new HashMap<>();
    private static ProfileMenu profileMenuSingleton;

    public static ProfileMenu getInstance() {
        if (profileMenuSingleton == null) {
            profileMenuSingleton = new ProfileMenu();
        }
        return profileMenuSingleton;
    }

    public void run() {

    }
}
