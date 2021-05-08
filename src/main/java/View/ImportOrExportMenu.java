package View;

import main.java.Controller.Regex;
import Controller.GameProgramController;
import main.java.Controller.MenuProgramController;
import main.java.Model.Menus;
import main.java.View.Response;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImportOrExportMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ImportOrExportMenu importOrExportMenuSingleton;

    public static ImportOrExportMenu getInstance() {
        if (importOrExportMenuSingleton == null) {
            importOrExportMenuSingleton = new ImportOrExportMenu();
        }
        return importOrExportMenuSingleton;
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
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, ImportOrExportMenu.commandChecker::showCurrentMenu);
        commandMap.put(Regex.MENU_ENTER.label, ImportOrExportMenu.commandChecker::menuEnterHandler);
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

        static void menuEnterHandler(Matcher matcher) {
            if (matcher.group(1).equals(Menus.MAIN_MENU.label)) {
                MenuProgramController.currentMenu = Menus.MAIN_MENU;
            } else if (matcher.group(1).equals(Menus.LOGIN_MENU.label)) {
                System.out.println(Response.menuNotPossible);
            }
        }
    }
}
