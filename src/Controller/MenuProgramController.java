package Controller;

import Model.Menus;
import View.*;

public class MenuProgramController {
    public static Menus currentMenu = Menus.LOGIN_MENU;

    public void run() {
        while (currentMenu != Menus.EXIT) {
            String command = GameProgramController.scanner.nextLine();
            if (currentMenu == Menus.MAIN_MENU) {
                MainMenu.getInstance().run(command);
            } else if (currentMenu == Menus.IMPORT_OR_EXPORT_MENU) {
                ImportOrExportMenu.getInstance().run(command);
            } else if (currentMenu == Menus.LOGIN_MENU) {
                LoginMenu.getInstance().run(command);
            } else if (currentMenu == Menus.PROFILE_MENU) {
                ProfileMenu.getInstance().run(command);
            } else if (currentMenu == Menus.SCOREBOARD_MENU) {
                ScoreboardMenu.getInstance().run(command);
            } else if (currentMenu == Menus.SHOP_MENU) {
                ShopMenu.getInstance().run(command);
            }
        }
    }
}
