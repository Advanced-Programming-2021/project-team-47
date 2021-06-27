package Controller;

import Model.Cards;
import Model.Deck;
import Model.MonsterCard;
import Model.Players;
import View.DuelMenu;
import View.LoginMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EffectController {
    public static HashMap<Pattern, Consumer<Matcher>> effectMap = new HashMap<>();
    private static EffectController effectController;

    public static EffectController getInstance() {
        if (effectController == null) {
            effectController = new EffectController();
        }
        return effectController;
    }

    static {
        effectMap.put(Regex.HERALD_OF_CREATION.label, EffectController::heraldOfCreation);
        effectMap.put(Regex.EXPLODER_DOGON.label, EffectController::exploderDagon);
        effectMap.put(Regex.TEEEAFORMING.label, EffectController::terraforming);
        effectMap.put(Regex.TERRATIGER.label, EffectController::terratiger);
        effectMap.put(Regex.THE_TRICKY.label, EffectController::theTricky);
        effectMap.put(Regex.RAIGEKI.label, EffectController::raigeki);
        effectMap.put(Regex.CHANGE_OF_HEART.label, EffectController::changeOfHeart);
        effectMap.put(Regex.HARPIES_FEATHER_DUSTER.label, EffectController::harpiesFeatherDuster);
        effectMap.put(Regex.MYSTICAL_SPACE_TYPHOON.label, EffectController::mysticalSpaceTyphoon);
        effectMap.put(Regex.TWIN_TWISTERS.label, EffectController::twinTwisters);
        effectMap.put(Regex.SUPPLY_SQUAD.label, EffectController::supplySquad);
        effectMap.put(Regex.POT_OF_GREED.label, EffectController::potOfGreed);
        effectMap.put(Regex.MONSTER_REBORN.label, EffectController::monsterReborn);
        effectMap.put(Regex.YAMI.label, EffectController::yami);
        effectMap.put(Regex.FOREST.label, EffectController::forest);
        effectMap.put(Regex.CLOSED_FOREST.label, EffectController::closedForest);
        effectMap.put(Regex.UMIIRUKA.label, EffectController::umiiruka);
        effectMap.put(Regex.SWORD_OF_DARK_DESTRUCTION.label, EffectController::swordOfDarkDestruction);
        effectMap.put(Regex.BLACK_PENDANT.label, EffectController::blackPendant);
        effectMap.put(Regex.UNITED_WE_STAND.label, EffectController::unitedWeStand);
        effectMap.put(Regex.MAGNUM_SHIELD.label, EffectController::magnumShield);
        effectMap.put(Regex.MIRROR_FORCE.label, EffectController::mirrorForce);
        effectMap.put(Regex.ADVANCED_RITUAL_ART.label, EffectController::advancedRitualArt);
        effectMap.put(Regex.MAGIC_CYLINDER.label, EffectController::magicCylinder);
        effectMap.put(Regex.MIND_CRUSH.label, EffectController::mindCrush);
        effectMap.put(Regex.SOLEMN_WARNING.label, EffectController::solemnWarning);
        effectMap.put(Regex.CALL_OF_THE_HAUNTED.label, EffectController::callOfTheHaunted);
        effectMap.put(Regex.COMMAND_KNIGHT.label, EffectController::commandKnight);
        effectMap.put(Regex.YOMI_SHIP.label, EffectController::yomiShip);
        effectMap.put(Regex.SUIJIN.label, EffectController::suijin);
        effectMap.put(Regex.CRAB_TURTLE.label, EffectController::crabTurtle);
        effectMap.put(Regex.SKULL_GUARDIAN.label, EffectController::skullGuardian);
        effectMap.put(Regex.MAN_EATER_BUG.label, EffectController::manEaterBug);
        effectMap.put(Regex.GATE_GUARDIAN.label, EffectController::gateGuardian);
        effectMap.put(Regex.SCANNER_CARD.label, EffectController::scannerCard);
    }

    public boolean takeCommand(String command) {
        for (Pattern commandReg : effectMap.keySet()) {
            if (command.matches(commandReg.pattern())) {
                effectMap.get(commandReg).accept(commandReg.matcher(command));
                return true;
            }
        }
        return false;
    }

    public static void heraldOfCreation(Matcher matcher) {
        DuelMenu.getInstance().getCardsInHand().remove(0);
        DuelMenu.getInstance().setCardsInHand(LoginMenu.loginUsername);
    }

    public static void exploderDagon(Matcher matcher) {
        DuelMenu.getInstance().setSelectCard(null, 0);
    }

    public static void terratiger(Matcher matcher) {
        GameProgramController.getInstance().summon(LoginMenu.loginUsername.getUsername());
    }

    public static void theTricky(Matcher matcher) {
        DuelMenu.getInstance().setCardsInHand(DuelMenu.duelMenu.getFirstPlayer());
        GameProgramController.getInstance().summon(DuelMenu.duelMenu.getFirstPlayer().getUsername());
    }

    public static void terraforming(Matcher matcher) {
        for (int i = 0; i < Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getFieldZone().size(); ++i) {
            if (Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getFieldZone().get(i).getKind().equals("Field Spell")) {
                Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).setCardsInHand(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getFieldZone().get(i), 0);
                break;
            }
        }
    }

    public static void raigeki(Matcher matcher) {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getMonsterCardZoneArray().clear();
    }

    public static void changeOfHeart(Matcher matcher) {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).setMonsterCardZone(Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getMonsterCardZone(0), 0);
    }

    public static void harpiesFeatherDuster(Matcher matcher) {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getSpellCardZone().clear();
    }

    public static void mysticalSpaceTyphoon(Matcher matcher) {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getSpellCardZone().remove(0);
    }

    public static void twinTwisters(Matcher matcher) {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).setCardsInHand(null, 0);
        Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getSpellCardZone().remove(0);
        Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getSpellCardZone().remove(1);
    }

    public static void supplySquad(Matcher matcher) {
        Deck.getDeckByOwner(DuelMenu.duelMenu.getFirstPlayer()).setCardsInDecks(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getMainDecks().getMainDeckCards().get(0).getCardName());
    }

    public static void potOfGreed(Matcher matcher) {
        Deck.getDeckByOwner(DuelMenu.duelMenu.getFirstPlayer()).setCardsInDecks(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getMainDecks().getMainDeckCards().get(0).getCardName());
        Deck.getDeckByOwner(DuelMenu.duelMenu.getFirstPlayer()).setCardsInDecks(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getMainDecks().getMainDeckCards().get(1).getCardName());
    }

    public static void monsterReborn(Matcher matcher) {
        GameProgramController.getInstance().summon(LoginMenu.loginUsername.getUsername());
    }

    public static void yami(Matcher opponent) {
        for (Cards card : Players.getPlayerByUsername(opponent.group(1)).getMonsterCardZoneArray()) {
            if (card.getKind().equals("Fiend") || card.getKind().equals("Fiend")) {
                card.increaseATK(200);
                card.increaseDEF(200);
            }
            if (card.getKind().equals("Fairy")) {
                card.decreaseATK(200);
                card.decreaseDEF(200);
            }
        }
    }

    public static void forest(Matcher opponent) {
        for (Cards card : Players.getPlayerByUsername(opponent.group(1)).getMonsterCardZoneArray()) {
            if (card.getKind().equals("Insect") || card.getKind().equals("Beast") || card.getKind().equals("Beast-Warrior")) {
                card.increaseATK(200);
                card.increaseDEF(200);
            }
        }
    }

    public static void closedForest(Matcher turn) {
        for (Cards card : Players.getPlayerByUsername(turn.group(1)).getCardsInGraveyard()) {
            if (card.getKind().equals("Insect") || card.getKind().equals("Beast") || card.getKind().equals("Beast-Warrior")) {
                card.increaseATK(100);
            }
        }
    }

    public static void umiiruka(Matcher matcher) {
        for (Cards card : Cards.allCards) {
            if (card.getKind().equals("Aqua")) {
                card.increaseATK(500);
                card.decreaseDEF(400);
            }
        }
    }

    public static void swordOfDarkDestruction(Matcher cards) {
        if (Cards.getCardByName(cards.group(1)).getKind().equals("Aqua")) {
            Cards.getCardByName(cards.group(1)).decreaseDEF(400);
            Cards.getCardByName(cards.group(1)).increaseATK(500);
        }
    }

    public static void blackPendant(Matcher cards) {
        Cards.getCardByName(cards.group(1)).increaseATK(500);
    }

    public static void unitedWeStand(Matcher cards) {
        Cards.getCardByName(cards.group(1)).increaseATK(800);
    }

    public static void magnumShield(Matcher turn) {
        for (Cards card : Players.getPlayerByUsername(turn.group(1)).getMonsterCardZoneArray()) {
            if (Players.getPlayerByUsername(turn.group(1)).getMonsterZone().get(card).equals("OO")) {
                card.increaseATK(card.getDEF());
            }
            if (Players.getPlayerByUsername(turn.group(1)).getMonsterZone().get(card).equals("DH")) {
                card.increaseDEF(card.getATK());
            }

        }
    }

    public static void advancedRitualArt(Matcher matcher) {//group1 -> turn / group2 -> Position / group3 -> Card
        GameProgramController.getInstance().ritualSummon(matcher.group(1), matcher.group(2));
        Players.getPlayerByUsername(matcher.group(1)).setCardsInGraveyard(Cards.getCardByName(matcher.group(3)));
    }

    public static void magicCylinder(Matcher matcher) {//group1 -> Opponent / group2 -> Card
        Players.getPlayerByUsername(matcher.group(1)).decreaseLifePoint(Cards.getCardByName(matcher.group(2)).getATK());
    }

    public static void mirrorForce(Matcher opponent) {
        for (Cards card : Players.getPlayerByUsername(opponent.group(1)).getMonsterCardZoneArray()) {
            if (Players.getPlayerByUsername(opponent.group(1)).getMonsterZone().get(card).equals("OO")) {
                Players.getPlayerByUsername(opponent.group(1)).removeFromArrayList(Players.getPlayerByUsername(opponent.group(1)).getMonsterCardZoneArray(), Players.getPlayerByUsername(opponent.group(1)).getMonsterZone(), card);
                Players.getPlayerByUsername(opponent.group(1)).setCardsInGraveyard(card);
            }
        }
    }

    public static void mindCrush(Matcher matcher) {//group1 -> Opponent / group2 -> Card
        int done = 0;
        for (Cards card : Players.getPlayerByUsername(matcher.group(1)).getMonsterCardZoneArray()) {
            if (card.equals(Cards.getCardByName(matcher.group(2)))) {
                Players.getPlayerByUsername(matcher.group(1)).removeFromArrayList(Players.getPlayerByUsername(matcher.group(1)).getMonsterCardZoneArray(), Players.getPlayerByUsername(matcher.group(1)).getMonsterZone(), Cards.getCardByName(matcher.group(2)));
                ++done;
            }
        }
        if (done == 0) {
            for (Cards card : Players.getPlayerByUsername(matcher.group(1)).getMonsterCardZoneArray()) {
                Players.getPlayerByUsername(matcher.group(1)).removeFromArrayList(Players.getPlayerByUsername(matcher.group(1)).getMonsterCardZoneArray(), Players.getPlayerByUsername(matcher.group(1)).getMonsterZone(), Cards.getCardByName(matcher.group(2)));
                return;
            }
        }
    }

    public static void solemnWarning(Matcher turn) {
        Players.getPlayerByUsername(turn.group(1)).decreaseLifePoint(2000);
    }

    public static void callOfTheHaunted(Matcher turn) {
        GameProgramController.getInstance().summon(turn.group(1));
    }

    public static void commandKnight(Matcher turn) {
        for (Cards card : Players.getPlayerByUsername(turn.group(1)).getMonsterCardZoneArray()
        ) {
            Players.getPlayerByUsername(turn.group(1)).increaseATK(card);
        }
        while (Players.getPlayerByUsername(turn.group(1)).getMonsterCardZoneArray().size() > 1) {
            Cards.getCardByName("command Knight").setAttackable(false);
        }
    }

    public static void yomiShip(Matcher matcher) {//group1 -> Opponent / group2 -> attackingCard
        Cards thisCard = Cards.getCardByName("yomi ship");
        if (Cards.getCardByName(matcher.group(2)).getATK() > thisCard.getDEF()) {
            Players.getPlayerByUsername(matcher.group(1)).getMonsterCardZoneArray().remove(Cards.getCardByName(matcher.group(2)));
            Players.getPlayerByUsername(matcher.group(1)).getAllCardsInHandsArray().remove(Cards.getCardByName(matcher.group(2)));
            Players.getPlayerByUsername(matcher.group(1)).getCardsInGraveyard().add(Cards.getCardByName(matcher.group(2)));
        }
    }

    public static void suijin(Matcher attackingCard) {
        int repeat = 0;
        int originalATK = MonsterCard.getCardByName(attackingCard.group(1)).getATK();
        if (repeat == 0) {

            MonsterCard.getCardByName(attackingCard.group(1)).setATK(0);
            repeat += 1;
        }
        MonsterCard.getCardByName(attackingCard.group(1)).setATK(originalATK);

    }

    public static void crabTurtle(Matcher matcher) {
        Cards.getCardByName("crab Turtle").canRitualSummon();
    }

    public static void skullGuardian(Matcher matcher) {
        Cards.getCardByName("skull Guardian").canRitualSummon();
    }

    public static void manEaterBug(Matcher matcher) {      //group1 -> Opponent / group2 -> Card;
        Players.getPlayerByUsername(matcher.group(1)).getMonsterCardZoneArray().remove(Cards.getCardByName(matcher.group(2)));
    }

    public static void gateGuardian(Matcher matcher) {//group1 -> Turn
        for (Cards card : MonsterCard.allCards) {
            Players.getPlayerByUsername(matcher.group(1)).getMonsterCardZoneArray().remove(card);
        }
        Cards.getCardByName("Gate Guardian").setCanSummon(true);
    }

    public static void scannerCard(Matcher matcher) {//group1 -> Turn / gropu2 -> Card
        Players.getPlayerByUsername(matcher.group(1)).putInMonsterZone(Cards.getCardByName(matcher.group(2)), Cards.getCardByName(matcher.group(2)).getCardName());
    }

}
