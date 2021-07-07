package Model;

public enum Menus {
    DECK_MENU(1, "Deck Menu"),
    DUEL_MENU(1, "Duel Menu"),
    LOGIN_MENU(2, "Login Menu"),
    MAIN_MENU(1, "Main Menu"),
    PROFILE_MENU(1, "Profile Menu"),
    SHOP_MENU(1, "Shop Menu"),
    SCOREBOARD_MENU(1, "Scoreboard Menu"),
    IMPORT_OR_EXPORT_MENU(1, "Import/Export Menu"),
    EXIT(1, "Exit");
    public final int key;
    public final String label;

    Menus(int key, String label) {
        this.label = label;
        this.key = key;
    }
}
