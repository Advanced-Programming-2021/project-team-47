package View;

import Controller.GameProgramController;
import Controller.LoginProgramController;
import Controller.MenuProgramController;
import Controller.Regex;
import Model.Menus;
import Model.Players;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static ProfileMenu profileMenuSingleton;

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

        static void changeNickNameResponse(Matcher matcher) {
            if (LoginProgramController.getInstance().checkNicknameExist(matcher.group(1))) {
                System.out.println(Response.changeNicknameSuccessfully);
                Players.getPlayerByUsername(LoginMenu.getInstance().getLoginUsername()).changeNickname(matcher.group(1));
            } else {
                System.out.println("user with nickname " + matcher.group(1) + " already exists");
            }
        }

        static void changePasswordResponse(Matcher matcher) {
            String newPassword = LoginProgramController.getInstance().changePassword(matcher).get(0);
            String currentPassword = LoginProgramController.getInstance().changePassword(matcher).get(1);
            if (LoginProgramController.getInstance().checkInvalidPassword(LoginMenu.getInstance().getLoginUsername(), currentPassword)) {
                System.out.println(Response.invalidCurrentPassword);
            } else if (LoginProgramController.getInstance().checkSamePassword(LoginMenu.getInstance().getLoginUsername(), newPassword)) {
                System.out.println(Response.samePasswordError);
            } else {
                System.out.println(Response.changePasswordSuccessfully);
                Players.getPlayerByUsername(LoginMenu.getInstance().getLoginUsername()).changePassword(newPassword);
            }
        }
    }
}
