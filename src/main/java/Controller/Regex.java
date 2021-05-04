package main.java.Controller;

import java.util.regex.Pattern;

public enum Regex {
    LOGIN(Pattern.compile("^user login (--password (?:[\\s\\S]+)|--username (?:[\\s\\S]+)) (--password (?:[\\s\\S]+)|--username (?:[\\s\\S]+))$")),
    MENU_ENTER(Pattern.compile("^menu enter ([^\\d]+)$")),
    MENU_EXIT(Pattern.compile("^menu exit$")),
    SHOW_CURRENT_MENU(Pattern.compile("^menu show-current$")),
    CREATE_USER(Pattern.compile("^user create (--password (?:[\\s\\S]+)|--nickname (?:[\\s\\S]+)|--username (?:[\\s\\S]+)) (--password (?:[\\s\\S]+)|--nickname (?:[\\s\\S]+)|--username (?:[\\s\\S]+)) (--password (?:[\\s\\S]+)|--nickname (?:[\\s\\S]+)|--username (?:[\\s\\S]+))$")),
    LOGOUT(Pattern.compile("^user logout$")),
    SCOREBOARD(Pattern.compile("^scoreboard show$")),
    CHANGE_NICKNAME(Pattern.compile("^profile change --nickname ([\\s\\S]+)$")),
    CHANGE_PASSWORD(Pattern.compile("^profile change (--new (?:[\\s\\S]+)|--password|--current (?:[\\s\\S]+)) (--new (?:[\\s\\S]+)|--password|--current (?:[\\s\\S]+)) (--new (?:[\\s\\S]+)|--password|--current (?:[\\s\\S]+))$")),
    CREATE_DECK(Pattern.compile("^deck create ([\\s\\S]+)$")),
    DELETE_DECK(Pattern.compile("^deck delete ([\\s\\S]+)$")),
    SET_ACTIVE_DECK(Pattern.compile("^deck set-activate ([\\s\\S]+)$")),
    DECK_ADD_CARD(Pattern.compile("^deck add-card (--card (?:[\\s\\S]+)|--deck (?:[\\s\\S]+)|--side) (--card (?:[\\S]+)|--deck (?:[\\S]+)|--side)(?:\\s)?(--card (?:[\\s\\S]+)|--deck (?:[\\s\\S]+)|--side)?$")),
    DECK_REMOVE_CARD(Pattern.compile("^deck rm-card (--card (?:[\\s\\S]+)|--deck (?:[\\s\\S]+)|--side) (--card (?:[\\S]+)|--deck (?:[\\S]+)|--side)(?:\\s)?(--card (?:[\\s\\S]+)|--deck (?:[\\s\\S]+)|--side)?$")),
    DECK_SHOW_ALL(Pattern.compile("^deck show --all$")),
    SHOW_DECK(Pattern.compile("^deck show (--deck-name (?:[\\S]+)|--side)(?:\\s)?(--deck-name (?:[\\s\\S]+)|--side)?$")),
    DECK_SHOW_CARD(Pattern.compile("^deck show --cards$")),
    BUY(Pattern.compile("^shop buy ([^\\d]+)$")),
    SHOP_SHOW_ALL(Pattern.compile("^shop show --all$")),
    DUEL_PLAYER(Pattern.compile("^duel (--new|--second-player (?:[\\s\\S]+)|--rounds (?:1|3)) (--new|--second-player (?:[\\s\\S]+)|--rounds (?:1|3)) (--new|--second-player (?:[\\s\\S]+)|--rounds (?:1|3))$")),
    DUEL_AI(Pattern.compile("^duel (--new|--ai|--rounds (?:1|3)) (--new|--ai|--rounds (?:1|3)) (--new|--ai|--rounds (?:1|3))$")),
    SELECT_MONSTER(Pattern.compile("^select --monster ([\\d]+)$")),
    SELECT_SPELL_AND_TRAP(Pattern.compile("^select --spell ([\\d]+)$")),
    SELECT_OPPONENT_MONSTER(Pattern.compile("^select (--monster|--opponent) (--monster|--opponent) ([\\d]+)$")),
    SELECT_OPPONENT_SPELL(Pattern.compile("^select (--spell|--opponent) (--spell|--opponent) ([\\d]+)$")),
    SELECT_FIELD(Pattern.compile("^select --field$")),
    SELECT_OPPONENT_FIELD(Pattern.compile("^select (--field|--opponent) (--field|--opponent)$")),
    SELECT_CARD_IN_HAND(Pattern.compile("^select --hand ([\\d]+)$")),
    DESELECT(Pattern.compile("^select -d$")),
    NEXT_PHASE(Pattern.compile("^next phase$")),
    SUMMON(Pattern.compile("^summon$")),
    FLIP_SUMMON(Pattern.compile("^flip-summon$")),
    SET(Pattern.compile("^set$")),
    SET_POSITION(Pattern.compile("^set -- position (attack|defense)$")),
    ATTACK(Pattern.compile("^attack ([\\d]+)$")),
    DIRECT_ATTACK(Pattern.compile("^attack direct$")),
    ACTIVE_EFFECT(Pattern.compile("^activate effect$")),
    SHOW_GRAVEYARD(Pattern.compile("^show graveyard$")),
    BACK(Pattern.compile("^back$")),
    SHOW_SELECT_CARDS(Pattern.compile("^card show --selected$")),
    SURRENDER(Pattern.compile("^surrender$")),
    INCREASE_MONEY(Pattern.compile("^increase --money ([\\d]+)$")),
    SELECT_HAND(Pattern.compile("^select (--force|--hand (?:[\\s\\S]+)) (--force|--hand (?:[\\s\\S]+))$")),
    INCREASE_LP(Pattern.compile("^increase --LP ([\\d]+)$")),
    DUEL_WIN(Pattern.compile("^duel set-winner ([\\s\\S]+)$")),
    CARD_IMPORT(Pattern.compile("^import card ([\\s\\S]+)$")),
    CARD_EXPORT(Pattern.compile("^export card ([\\s\\S]+)$"));

    public final Pattern label;

    Regex(Pattern label) {
        this.label = label;
    }
}
