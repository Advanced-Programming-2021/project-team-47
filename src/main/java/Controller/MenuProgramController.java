package main.java.Controller;

import main.java.Model.Menus;
import main.java.View.*;

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
            } else if (currentMenu.equals(Menus.IMPORT_OR_EXPORT_MENU)) {
                ImportOrExportMenu.getInstance().run(command);
            } else if (currentMenu.equals(Menus.LOGIN_MENU)) {
                LoginMenu.getInstance().run(command);
            } else if (currentMenu.equals(Menus.PROFILE_MENU)) {
                ProfileMenu.getInstance().run(command);
            } else if (currentMenu.equals(Menus.SCOREBOARD_MENU)) {
                ScoreboardMenu.getInstance().run(command);
            } else if (currentMenu.equals(Menus.SHOP_MENU)) {
                ShopMenu.getInstance().run(command);
            }
        }
        System.exit(0);
    }
}
