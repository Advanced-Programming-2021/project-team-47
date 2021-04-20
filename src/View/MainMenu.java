package View;

import Controller.GameProgramController;
import Controller.MenuProgramController;
import Controller.Regex;
import Model.Menus;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static MainMenu mainMenuSingleton;

    public static MainMenu getInstance() {
        if (mainMenuSingleton == null) {
            mainMenuSingleton = new MainMenu();
        }
        return mainMenuSingleton;
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
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, MainMenu.commandChecker::showCurrentMenu);
        commandMap.put(Regex.LOGOUT.label, MainMenu.commandChecker::logOutResponse);
        while (!command.equals("menu exit")) {
            takeCommand(command);
            command = GameProgramController.scanner.nextLine().trim();
        }
        MenuProgramController.currentMenu=Menus.LOGIN_MENU;
    }

    static class commandChecker {
        static void showCurrentMenu(Matcher matcher) {
            Menus current = MenuProgramController.currentMenu;
            System.out.println(current.label);
        }

        static void logOutResponse(Matcher matcher) {
            System.out.println(Response.userLogoutSuccessfully);
            MenuProgramController.currentMenu = Menus.LOGIN_MENU;
        }
    }
}
