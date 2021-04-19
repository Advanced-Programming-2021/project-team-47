package Model;

import java.util.regex.Pattern;

public enum Menus {
    DECK_MENU("Main Menu"),
    DUEL_MENU("Main Menu"),
    LOGIN_MENU("Login Menu"),
    MAIN_MENU("Main Menu"),
    PROFILE_MENU("Main Menu"),
    SHOP_MENU("Main Menu"),
    SCOREBOARD_MENU("Main Menu"),
    IMPORT_OR_EXPORT_MENU("Main Menu");

    public final String label;

    Menus(String label) {
        this.label = label;
    }
}
