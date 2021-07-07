package View.Console;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controller.Regex;
import Controller.GameProgramController;
import Controller.LoginProgramController;
import Controller.MenuProgramController;
import Model.Menus;
import Model.Players;
import Model.Response;

public class LoginMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static LoginMenu loginMenuSingleton;
    private static HashMap<Menus, String> menuEnter = new HashMap<>();

    static {
        menuEnter.put(Menus.LOGIN_MENU, Menus.LOGIN_MENU.label);
        menuEnter.put(Menus.MAIN_MENU, Menus.MAIN_MENU.label);
        menuEnter.put(Menus.PROFILE_MENU, Menus.PROFILE_MENU.label);
        menuEnter.put(Menus.SHOP_MENU, Menus.SHOP_MENU.label);
        menuEnter.put(Menus.SCOREBOARD_MENU, Menus.SCOREBOARD_MENU.label);
        menuEnter.put(Menus.IMPORT_OR_EXPORT_MENU, Menus.IMPORT_OR_EXPORT_MENU.label);
    }

    public static LoginMenu getInstance() {
        if (loginMenuSingleton == null) {
            loginMenuSingleton = new LoginMenu();
        }
        return loginMenuSingleton;
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
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, LoginMenu.commandChecker::showCurrentMenu);
        commandMap.put(Regex.MENU_ENTER.label, LoginMenu.commandChecker::menuEnterHandler);
        commandMap.put(Regex.LOGIN.label, LoginMenu.commandChecker::loginResponse);
        commandMap.put(Regex.CREATE_USER.label, LoginMenu.commandChecker::signUpResponse);
        while (!command.equals("menu exit")) {
            takeCommand(command);
            command = GameProgramController.scanner.nextLine().trim();
        }
        System.exit(0);
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

        static void loginResponse(Matcher matcher) {
            String password = LoginProgramController.getInstance().loginUser(matcher).get(0);
            String username = LoginProgramController.getInstance().loginUser(matcher).get(1);
            if (!LoginProgramController.getInstance().checkUsernameExist(username)) {
                System.out.println(Response.wrongUsernameOrPassword);
            } else if (LoginProgramController.getInstance().checkInvalidPassword(Players.getPlayerByUsername(username), password)) {
                System.out.println(Response.wrongUsernameOrPassword);
            } else {
                System.out.println(Response.userLoginSuccessfully);
                LoginProgramController.setLoginUsername(Players.getPlayerByUsername(username));
                MenuProgramController.currentMenu = Menus.MAIN_MENU;
            }
        }

        static void signUpResponse(Matcher matcher) {
            String password = LoginProgramController.getInstance().signUpUser(matcher).get(0);
            String username = LoginProgramController.getInstance().signUpUser(matcher).get(1);
            String nickName = LoginProgramController.getInstance().signUpUser(matcher).get(2);
            if (LoginProgramController.getInstance().checkUsernameExist(username)) {
                System.out.println("user with username " + username + " already exists");
            } else if (LoginProgramController.getInstance().checkNicknameExist(nickName)) {
                System.out.println("user with nickname " + nickName + " already exists");
            } else {
                System.out.println(Response.userCreateSuccessfully);
                new Players(username, nickName, password);
            }
        }
    }
}
