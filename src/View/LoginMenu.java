package View;


import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.*;
import Model.Menus;

public class LoginMenu implements Runnable{
    public static Menus currentMenu = Menus.MAIN_MENU;
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static LoginMenu loginMenuSingleton;
    private String username;
    private String nickname;
    private String password;

    public static LoginMenu getInstance() {
        if (loginMenuSingleton == null) {
            loginMenuSingleton = new LoginMenu();
        }
        return loginMenuSingleton;
    }

    public void run() {
        while (true) {
            String command = GameProgramController.scanner.nextLine();
        }
    }

    public void showCurrentMenu() {
        Menus current = LoginMenu.currentMenu;
        System.out.println(current.label);
    }

}
