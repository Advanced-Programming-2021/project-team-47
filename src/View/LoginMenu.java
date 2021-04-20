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

    public static void takeCommand(String command) {
        for (Pattern commandReg : commandMap.keySet())
            if (command.matches(commandReg.pattern())) {
                commandMap.get(commandReg).accept(commandReg.matcher(command));
                return;
            }
        System.out.println("invalid command");
    }

    public void run(String command) {
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, LoginMenu.commandChecker::showCurrentMenu);
        while (!command.equals("menu exit")) {
            takeCommand(command);
            command = GameProgramController.scanner.nextLine().trim();
        }
        System.exit(0);
    }

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    static class commandChecker {
        static void showCurrentMenu(Matcher matcher) {
            Menus current = MenuProgramController.currentMenu;
            System.out.println(current.label);
        }
    }
}
