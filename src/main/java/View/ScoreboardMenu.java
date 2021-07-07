package View;

import Controller.Regex;
import Controller.GameProgramController;
import Controller.MenuProgramController;
import Model.Menus;
import Model.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ScoreboardMenu scoreboardMenuSingleton;
    private static HashMap<Menus, String> menuEnter = new HashMap<>();

    static {
        menuEnter.put(Menus.LOGIN_MENU, Menus.LOGIN_MENU.label);
        menuEnter.put(Menus.MAIN_MENU, Menus.MAIN_MENU.label);
        menuEnter.put(Menus.PROFILE_MENU, Menus.PROFILE_MENU.label);
        menuEnter.put(Menus.SHOP_MENU, Menus.SHOP_MENU.label);
        menuEnter.put(Menus.SCOREBOARD_MENU, Menus.SCOREBOARD_MENU.label);
        menuEnter.put(Menus.IMPORT_OR_EXPORT_MENU, Menus.IMPORT_OR_EXPORT_MENU.label);
    }

    public static ScoreboardMenu getInstance() {
        if (scoreboardMenuSingleton == null) {
            scoreboardMenuSingleton = new ScoreboardMenu();
        }
        return scoreboardMenuSingleton;
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
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, ScoreboardMenu.commandChecker::showCurrentMenu);
        commandMap.put(Regex.SCOREBOARD.label, ScoreboardMenu.commandChecker::showScoreboard);
        commandMap.put(Regex.MENU_ENTER.label, ScoreboardMenu.commandChecker::menuEnterHandler);
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
            for (Map.Entry<Menus, String> entry : menuEnter.entrySet()) {
                if (matcher.group(1).equals(entry.getValue()) && entry.getKey().key == 1) {
                    MenuProgramController.currentMenu = entry.getKey();
                    break;
                } else if (matcher.group(1).equals(entry.getValue()) && entry.getKey().key == 2) {
                    System.out.println(Response.menuNotPossible);
                }
            }
        }

        static void showScoreboard(Matcher matcher) {
            int rank = 1;
            ArrayList<String> sortWithArrayList = GameProgramController.getInstance().scoreboardShow();
            for (int j = 0; j < sortWithArrayList.size(); ++j) {
                System.out.println(rank + "-" + sortWithArrayList.get(j) + ":" + " " + Players.getPlayerByNickName(sortWithArrayList.get(j)).getScore());
                if (j != 0 && j < sortWithArrayList.size() - 1 && Players.getPlayerByNickName(sortWithArrayList.get(j)).getScore() != Players.getPlayerByNickName(sortWithArrayList.get(j + 1)).getScore())
                    ++rank;
                if (j != 0 && j == sortWithArrayList.size() - 1 && Players.getPlayerByNickName(sortWithArrayList.get(j)).getScore() != Players.getPlayerByNickName(sortWithArrayList.get(j - 1)).getScore())
                    ++rank;
            }
        }
    }
}
