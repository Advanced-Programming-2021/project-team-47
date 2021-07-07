package Controller;

import Model.Menus;
import View.Console.LoginMenu;
import View.Console.MainMenu;
import View.Console.ProfileMenu;

public class MenuProgramController {
    public static Menus currentMenu = Menus.LOGIN_MENU;
    private static MenuProgramController menuProgramController;

    public static MenuProgramController getInstance() {
        if (menuProgramController == null) {
            menuProgramController = new MenuProgramController();
        }
        return menuProgramController;
    }

    public void run() {
        while (currentMenu != Menus.EXIT) {
            String command = GameProgramController.scanner.nextLine();
            if (currentMenu.equals(Menus.MAIN_MENU)) {
                MainMenu.getInstance().run(command);
            } else if (currentMenu.equals(Menus.LOGIN_MENU)) {
                LoginMenu.getInstance().run(command);
            } else if (currentMenu.equals(Menus.PROFILE_MENU)) {
                ProfileMenu.getInstance().run(command);
            }
        }
        System.exit(0);
    }
}
