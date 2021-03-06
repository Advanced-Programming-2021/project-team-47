package View.Console;

import Controller.GameProgramController;
import Controller.LoginProgramController;
import Controller.Regex;
import Model.Menus;
import Model.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ProfileMenu profileMenuSingleton;
    private static HashMap<Menus, String> menuEnter = new HashMap<>();

    static {
        menuEnter.put(Menus.LOGIN_MENU, Menus.LOGIN_MENU.label);
        menuEnter.put(Menus.MAIN_MENU, Menus.MAIN_MENU.label);
        menuEnter.put(Menus.PROFILE_MENU, Menus.PROFILE_MENU.label);
        menuEnter.put(Menus.SHOP_MENU, Menus.SHOP_MENU.label);
        menuEnter.put(Menus.SCOREBOARD_MENU, Menus.SCOREBOARD_MENU.label);
        menuEnter.put(Menus.IMPORT_OR_EXPORT_MENU, Menus.IMPORT_OR_EXPORT_MENU.label);
    }

    public static ProfileMenu getInstance() {
        if (profileMenuSingleton == null) {
            profileMenuSingleton = new ProfileMenu();
        }
        return profileMenuSingleton;
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
        commandMap.put(Regex.SHOW_CURRENT_MENU.label, ProfileMenu.commandChecker::showCurrentMenu);
        commandMap.put(Regex.CHANGE_NICKNAME.label, ProfileMenu.commandChecker::changeNickNameResponse);
        commandMap.put(Regex.CHANGE_PASSWORD.label, ProfileMenu.commandChecker::changePasswordResponse);
        commandMap.put(Regex.MENU_ENTER.label, ProfileMenu.commandChecker::menuEnterHandler);
        while (!command.equals("menu exit")) {
            takeCommand(command);
            command = GameProgramController.scanner.nextLine().trim();
        }
        GameProgramController.currentMenu = Menus.LOGIN_MENU;
    }

    static class commandChecker {
        static void showCurrentMenu(Matcher matcher) {
            Menus current = GameProgramController.currentMenu;
            System.out.println(current.label);
        }

        static void menuEnterHandler(Matcher matcher) {
            for (Map.Entry<Menus, String> entry : menuEnter.entrySet()) {
                if (matcher.group(1).equals(entry.getValue()) && entry.getKey().key == 1) {
                    GameProgramController.currentMenu = entry.getKey();
                    break;
                } else if (matcher.group(1).equals(entry.getValue()) && entry.getKey().key == 2) {
                    System.out.println(Response.menuNotPossible);
                }
            }
        }

        static void changeNickNameResponse(Matcher matcher) {
            if (LoginProgramController.getInstance().checkNicknameExist(matcher.group(1))) {
                System.out.println(Response.changeNicknameSuccessfully);
                LoginProgramController.getLoginUsername().changeNickname(matcher.group(1));
            } else {
                System.out.println("user with nickname " + matcher.group(1) + " already exists");
            }
        }

        static void changePasswordResponse(Matcher matcher) {
            String newPassword = LoginProgramController.getInstance().changePassword(matcher).get(0);
            String currentPassword = LoginProgramController.getInstance().changePassword(matcher).get(1);
            if (LoginProgramController.getInstance().checkInvalidPassword(LoginProgramController.getLoginUsername(), currentPassword)) {
                System.out.println(Response.invalidCurrentPassword);
            } else if (LoginProgramController.getInstance().checkSamePassword(LoginProgramController.getLoginUsername(), newPassword)) {
                System.out.println(Response.samePasswordError);
            } else {
                System.out.println(Response.changePasswordSuccessfully);
                LoginProgramController.getLoginUsername().changePassword(newPassword);
            }
        }
    }
}
