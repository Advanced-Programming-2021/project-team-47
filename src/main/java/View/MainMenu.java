package main.java.View;

import Model.Menus;
import main.java.Controller.GameProgramController;
import main.java.Controller.MenuProgramController;
import main.java.Controller.Regex;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu implements Runnable {
    public HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static HashMap<Menus, String> menuEnter = new HashMap<>();
    private static MainMenu mainMenuSingleton;

    static {
        menuEnter.put(Menus.LOGIN_MENU, Menus.LOGIN_MENU.label);
        menuEnter.put(Menus.MAIN_MENU, Menus.MAIN_MENU.label);
        menuEnter.put(Menus.PROFILE_MENU, Menus.PROFILE_MENU.label);
        menuEnter.put(Menus.SHOP_MENU, Menus.SHOP_MENU.label);
        menuEnter.put(Menus.SCOREBOARD_MENU, Menus.SCOREBOARD_MENU.label);
        menuEnter.put(Menus.IMPORT_OR_EXPORT_MENU, Menus.IMPORT_OR_EXPORT_MENU.label);
    }

    public static MainMenu getInstance() {
        if (mainMenuSingleton == null) {
            mainMenuSingleton = new MainMenu();
        }
        return mainMenuSingleton;
    }

    public void takeCommand(String command) {
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
        commandMap.put(Regex.MENU_ENTER.label, MainMenu.commandChecker::menuEnterHandler);
        while (!command.equals("menu exit")) {
            takeCommand(command);
            command = GameProgramController.scanner.nextLine().trim();
        }
        MenuProgramController.currentMenu = Menus.LOGIN_MENU;
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

        static void menuEnterHandler(Matcher matcher) {
            for (Map.Entry<Menus, String> entry : menuEnter.entrySet()) {
                if (matcher.group(1).equals(entry.getValue())) {
                    MenuProgramController.currentMenu = entry.getKey();
                    break;
                }
            }
        }
    }
}
