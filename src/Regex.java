

import java.util.regex.Pattern;

public enum Regex {
    LOGIN(Pattern.compile("^user login ((--password|--p) (?:[\\s\\S]+)|(--username|--u) (?:[\\s\\S]+)) ((--password|--p) (?:[\\s\\S]+)|(--username|--u) (?:[\\s\\S]+))$")),
    SIGNUP(Pattern.compile("^user signup ((--password|--p) (?:[\\s\\S]+)|(--username|--u) (?:[\\s\\S]+)) ((--password|--p) (?:[\\s\\S]+)|(--username|--u) (?:[\\s\\S]+))$")),
    MENU_ENTER(Pattern.compile("^menu enter ([^\\d]+)$")),
    CANCEL(Pattern.compile("^cancel$")),
    SHOW_CURRENT_MENU(Pattern.compile("^menu show-current$")),
    CREATE_USER(Pattern.compile("^user create ((--password|--p) (?:[\\s\\S]+)|(--nickname|--n) (?:[\\s\\S]+)|(--username|--u) (?:[\\s\\S]+)) ((--password|--p) (?:[\\s\\S]+)|(--nickname|--n) (?:[\\s\\S]+)|(--username|--u) (?:[\\s\\S]+)) ((--password|--p) (?:[\\s\\S]+)|(--nickname|--n) (?:[\\s\\S]+)|(--username|--u) (?:[\\s\\S]+))$")),
    LOGOUT(Pattern.compile("^user logout$")),
    SCOREBOARD(Pattern.compile("^scoreboard show$")),
    CHANGE_NICKNAME(Pattern.compile("^profile change --nickname ([\\s\\S]+)$")),
    CHANGE_PASSWORD(Pattern.compile("^profile change ((--new|--n) (?:[\\s\\S]+)|(--password|--p)|--current (?:[\\s\\S]+)) ((--new|--n) (?:[\\s\\S]+)|(--password|--p)|--current (?:[\\s\\S]+)) ((--new|--n) (?:[\\s\\S]+)|(--password|--p)|--current (?:[\\s\\S]+))$")),
    CREATE_DECK(Pattern.compile("^deck create ([\\s\\S]+)$")),
    DELETE_DECK(Pattern.compile("^deck delete ([\\s\\S]+)$")),
    SET_ACTIVE_DECK(Pattern.compile("^deck set-activate ([\\s\\S]+)$")),
    DECK_ADD_CARD(Pattern.compile("^deck add-card ((--card|--c) (?:[\\s\\S]+)|(--deck|--d) (?:[\\s\\S]+)|(--side|--s)) ((--card|--c) (?:[\\s\\S]+)|(--deck|--d) (?:[\\s\\S]+)|(--side|--s))(?:\\s)?((--card|--c) (?:[\\s\\S]+)|(--deck|--d) (?:[\\s\\S]+)|(--side|--s))?$")),
    DECK_REMOVE_CARD(Pattern.compile("^deck rm-card ((--card|--c) (?:[\\s\\S]+)|(--deck|--d) (?:[\\s\\S]+)|(--side|--s)) ((--card|--c) (?:[\\s\\S]+)|(--deck|--d) (?:[\\s\\S]+)|(--side|--s))(?:\\s)?((--card|--c) (?:[\\s\\S]+)|(--deck|--d) (?:[\\s\\S]+)|(--side|--s))?$")),
    DECK_SHOW_ALL(Pattern.compile("^deck show --all$")),
    SHOW_DECK(Pattern.compile("^deck show (--deck-name (?:[\\S]+)|(--side|--s))(?:\\s)?(--deck-name (?:[\\s\\S]+)|(--side|--s))?$")),
    DECK_SHOW_CARD(Pattern.compile("^deck show --cards$")),
    BUY(Pattern.compile("^shop buy ([^\\d]+)$")),
    SHOP_SHOW_ALL(Pattern.compile("^shop show --all$")),
    DUEL_PLAYER(Pattern.compile("^duel ((--new|--n)|--second-player (?:[\\s\\S]+)|(--rounds|--r) (?:1|3)) ((--new|--n)|--second-player (?:[\\s\\S]+)|(--rounds|--r) (?:1|3)) ((--new|--n)|--second-player (?:[\\s\\S]+)|(--rounds|--r) (?:1|3))$")),
    DUEL_AI(Pattern.compile("^duel ((--new|--n)|--ai|(--rounds|--r) (?:1|3)) ((--new|--n)|--ai|(--rounds|--r) (?:1|3)) ((--new|--n)|--ai|(--rounds|--r) (?:1|3))$")),
    SELECT_MONSTER(Pattern.compile("^select --monster ([\\d]+)$")),
    SELECT_SPELL_AND_TRAP(Pattern.compile("^select --spell ([\\d]+)$")),
    SELECT_OPPONENT_MONSTER(Pattern.compile("^select ((--monster|--m)|(--opponent|--o)) ((--monster|--m)|(--opponent|--o)) ([\\d]+)$")),
    SELECT_OPPONENT_SPELL(Pattern.compile("^select ((--spell|--s)|(--opponent|--o)) ((--spell|--s)|(--opponent|--o)) ([\\d]+)$")),
    SELECT_OPPONENT_FIELD(Pattern.compile("^select ((--field|--f)|(--opponent|--o)) ((--field|--f)|(--opponent|--o))$")),
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
    SELECT_HAND(Pattern.compile("^select ((--force|--f)|(--hand|--h) (?:[\\s\\S]+)) ((--force|--f)|(--hand|--h) (?:[\\s\\S]+))$")),
    INCREASE_LP(Pattern.compile("^increase --LP ([\\d]+)$")),
    DUEL_WIN(Pattern.compile("^duel set-winner ([\\s\\S]+)$")),
    CARD_IMPORT(Pattern.compile("^import card ([\\s\\S]+)$")),
    CARD_EXPORT(Pattern.compile("^export card ([\\s\\S]+)$")),
    HERALD_OF_CREATION(Pattern.compile("heraldOfCreation")),
    EXPLODER_DOGON(Pattern.compile("exploderDagon")),
    TEEEAFORMING(Pattern.compile("terraforming")),
    TERRATIGER(Pattern.compile("terratiger")),
    THE_TRICKY(Pattern.compile("theTricky")),
    RAIGEKI(Pattern.compile("raigeki")),
    CHANGE_OF_HEART(Pattern.compile("changeOfHeart")),
    HARPIES_FEATHER_DUSTER(Pattern.compile("harpiesFeatherDuster")),
    MYSTICAL_SPACE_TYPHOON(Pattern.compile("mysticalSpaceTyphoon")),
    TWIN_TWISTERS(Pattern.compile("twinTwisters")),
    SUPPLY_SQUAD(Pattern.compile("supplySquad")),
    POT_OF_GREED(Pattern.compile("potOfGreed")),
    MONSTER_REBORN(Pattern.compile("monsterReborn")),
    YAMI(Pattern.compile("yami")),
    FOREST(Pattern.compile("forest")),
    CLOSED_FOREST(Pattern.compile("closedForest")),
    UMIIRUKA(Pattern.compile("umiiruka")),
    SWORD_OF_DARK_DESTRUCTION(Pattern.compile("swordOfDarkDestruction")),
    BLACK_PENDANT(Pattern.compile("blackPendant")),
    UNITED_WE_STAND(Pattern.compile("unitedWeStand")),
    MAGNUM_SHIELD(Pattern.compile("magnumShield")),
    MIRROR_FORCE(Pattern.compile("mirrorForce")),
    ADVANCED_RITUAL_ART(Pattern.compile("advancedRitualArt")),
    MAGIC_CYLINDER(Pattern.compile("magicCylinder")),
    MIND_CRUSH(Pattern.compile("mindCrush")),
    SOLEMN_WARNING(Pattern.compile("solemnWarning")),
    CALL_OF_THE_HAUNTED(Pattern.compile("callOfTheHaunted")),
    COMMAND_KNIGHT(Pattern.compile("commandKnight")),
    YOMI_SHIP(Pattern.compile("yomiShip")),
    SUIJIN(Pattern.compile("suijin")),
    CRAB_TURTLE(Pattern.compile("crabTurtle")),
    SKULL_GUARDIAN(Pattern.compile("skullGuardian")),
    MAN_EATER_BUG(Pattern.compile("manEaterBug")),
    GATE_GUARDIAN(Pattern.compile("gateGuardian")),
    SCANNER_CARD(Pattern.compile("scannerCard")),
    RITUAL_SUMMON(Pattern.compile("^ritual-summon$")),
    MAGIC_JAMMER(Pattern.compile("^magicJammer$")),
    TORRENTIAL_TRIBUTE(Pattern.compile("^torrentialTribute$")),
    SPELL_ABSORPTION(Pattern.compile("^Spell Absorption$")),
    DARK_HOLE(Pattern.compile("^Dark Hole$")),
    THE_CALCULATOR(Pattern.compile("^The Calculator$")),
    SCANNER(Pattern.compile("^Scanner$")),
    MARSHMALLON(Pattern.compile("^Marshmallon$")),
    BEAST_KING_BARBAROS(Pattern.compile("^Beast King Barbaros$")),
    SPECIAL_SUMMON(Pattern.compile("^special-summon$")),
    CHATROOM(Pattern.compile("message ")),// form : message <message> username token
    EDIT_MESSAGE(Pattern.compile("edit ")) ,// form : edit <new message> <oldMessage ID> token
    DELETE_MESSAEG(Pattern.compile("delete ")), // form : delete <message ID> token
    REPLY_TO_MESSAGE(Pattern.compile("reply ")), // form : reply <message id> message token
    SHOW_ONLINE_PLAYERS(Pattern.compile("number of online players"));

    public final Pattern label;

    Regex(Pattern label) {
        this.label = label;
    }

    ;

    public String getLabel() {
        return this.getLabel();
    }

}
