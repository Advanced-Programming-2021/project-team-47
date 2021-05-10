package View;

import Controller.GameProgramController;
import Controller.MenuProgramController;
import Controller.Regex;
import Model.Menus;
import Model.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ScoreboardMenu scoreboardMenuSingleton;

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
            if (matcher.group(1).equals(Menus.MAIN_MENU.label)) {
                MenuProgramController.currentMenu = Menus.MAIN_MENU;
            } else if (matcher.group(1).equals(Menus.LOGIN_MENU.label)) {
                System.out.println(Response.menuNotPossible);
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
