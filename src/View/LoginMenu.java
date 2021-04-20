package View;


import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.*;
import Model.Menus;

public class LoginMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static LoginMenu loginMenuSingleton;
    private String loginUsername;

    public static LoginMenu getInstance() {
        if (loginMenuSingleton == null) {
            loginMenuSingleton = new LoginMenu();
        }
        return loginMenuSingleton;
    }

    public void run(String command) {
        while (true) {
        }
    }

    public void showCurrentMenu() {
        Menus current = MenuProgramController.currentMenu;
        System.out.println(current.label);
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }
}
