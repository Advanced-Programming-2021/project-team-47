package Model;

public enum Menus {
    DECK_MENU("Deck Menu"),
    DUEL_MENU("Duel Menu"),
    LOGIN_MENU("Login Menu"),
    MAIN_MENU("Main Menu"),
    PROFILE_MENU("Profile Menu"),
    SHOP_MENU("Shop Menu"),
    SCOREBOARD_MENU("Scoreboard Menu"),
    IMPORT_OR_EXPORT_MENU("Import/Export Menu"),
    EXIT("Exit");

    public final String label;

    Menus(String label) {
        this.label = label;
    }
}
