package View;

import Controller.GameProgramController;
import Controller.LoginProgramController;
import Controller.MenuProgramController;
import Controller.Regex;
import Model.Menus;

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

    public static void takeCommand(String command) {
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

        static void changeNickNameResponse(Matcher matcher) {
            if (LoginProgramController.getInstance().checkNicknameExist(matcher.group(1))) {
                System.out.println(Response.changeNicknameSuccessfully);
            } else {
                System.out.println("user with nickname " + matcher.group(1) + " already exists");
            }
        }

        static void changePasswordResponse(Matcher matcher) {
            int newPassword = 0;
            int currentPassword = 0;
            for (int i = 1; i < 4; ++i) {
                if (matcher.group(i).contains("--new")) {
                    newPassword = i;
                } else if (matcher.group(i).contains("--current")) {
                    currentPassword = i;
                }
            }
            if (LoginProgramController.getInstance().checkInvalidPassword(LoginMenu.getInstance().getLoginUsername(), matcher.group(currentPassword))) {
                System.out.println(Response.invalidCurrentPassword);
            } else if (LoginProgramController.getInstance().checkSamePassword(LoginMenu.getInstance().getLoginUsername(), matcher.group(newPassword))) {
                System.out.println(Response.samePasswordError);
            } else {
                System.out.println(Response.changePasswordSuccessfully);
            }
        }
    }
}
