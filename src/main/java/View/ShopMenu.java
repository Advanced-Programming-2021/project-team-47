package View;

import Controller.MenuProgramController;
import Controller.GameProgramController;
import Controller.ShopProgramController;
import Controller.Regex;
import Model.Menus;
import Model.Cards;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ShopMenu shopMenuSingleton;
    private static HashMap<Menus, String> menuEnter = new HashMap<>();

    static {
        menuEnter.put(Menus.LOGIN_MENU, Menus.LOGIN_MENU.label);
        menuEnter.put(Menus.MAIN_MENU, Menus.MAIN_MENU.label);
        menuEnter.put(Menus.PROFILE_MENU, Menus.PROFILE_MENU.label);
        menuEnter.put(Menus.SHOP_MENU, Menus.SHOP_MENU.label);
        menuEnter.put(Menus.SCOREBOARD_MENU, Menus.SCOREBOARD_MENU.label);
        menuEnter.put(Menus.IMPORT_OR_EXPORT_MENU, Menus.IMPORT_OR_EXPORT_MENU.label);
    }

    public static ShopMenu getInstance() {
        if (shopMenuSingleton == null) {
            shopMenuSingleton = new ShopMenu();
        }
        return shopMenuSingleton;
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
        commandMap.put(Regex.BUY.label, commandChecker::printMoneyError);
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, commandChecker::showCurrentMenu);
        commandMap.put(Regex.SHOP_SHOW_ALL.label, commandChecker::printAllCards);
        commandMap.put(Regex.MENU_ENTER.label, commandChecker::menuEnterHandler);
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

        static void printMoneyError(Matcher matcher) {
            if (ShopProgramController.getInstance().checkEnoughMoney(LoginMenu.getInstance().getLoginUsername(), matcher.group(1))) {
                LoginMenu.getInstance().getLoginUsername().setPlayerCards(matcher.group(1));
                LoginMenu.getInstance().getLoginUsername().decreaseMoney(Cards.getCardByName(matcher.group(1)).getPrice());
            } else if (Cards.getCardByName(matcher.group(1)) == null) {
                System.out.println(Response.cardNameNotExist);
            } else {
                System.out.println(Response.notEnoughMoney);
            }
        }

        static void printAllCards(Matcher matcher) {
            TreeMap<String, Integer> sortCard = ShopProgramController.getInstance().sortCard();
            for (Map.Entry<String, Integer> entry : sortCard.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }

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
    }
}
