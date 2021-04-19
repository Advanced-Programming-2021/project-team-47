package Controller;

import java.util.regex.Pattern;

public enum Regex {
    LOGIN,
    MENU_ENTER,
    SHOW_CURRENT_MENU,
    CREATE_USER,
    LOGOUT,
    SCOREBOARD,
    CHANGE_NICKNAME,
    CHANGE_PASSWORD,
    CREATE_DECK,
    DELETE_DECK,
    SET_ACTIVE_DECK,
    DECK_ADD_CARD,
    DECK_REMOVE_CARD,
    DECK_SHOW_ALL,
    SHOW_DECK,
    DECK_SHOW_CARD,
    BUY,
    SHOP_SHOW_ALL,
    DUEL_PLAYER,
    DUEL_AI,
    SELECT_MONSTER,
    SELECT_OPPONENT_MONSTER,
    SELECT_FIELD,
    SELECT_OPPONENT_FIELD,
    SELECT_CARD,
    DESELECT,
    NEXT_PHASE,
    SUMMON,
    FLIP_SUMMON,
    SET,
    SET_POSITION,
    ATTACT,
    DIRECT_ATTACT,
    ACTIVE_EFFECT,
    SHOW_GRAVEYARD,
    BACK,
    SHOW_SELECT_CARDS,
    SURRENDER,
    INCREASE_MONEY,
    SELECT_HAND,
    INCREASE_LP,
    DUELWIN,
    CARD_IMPORT,
    CARD_EXPORT;

    public final Pattern label;

    Regex(Pattern label) {
        this.label = label;
    }
}
