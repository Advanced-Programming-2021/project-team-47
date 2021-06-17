package Controller;

import Model.Cards;
import Model.Deck;
import Model.MonsterCard;
import Model.Players;
import View.DuelMenu;
import View.LoginMenu;

import java.util.ArrayList;

public class EffectController {
    public void heraldOfCreation() {
        DuelMenu.getInstance().getCardsInHand().remove(0);
        DuelMenu.getInstance().setCardsInHand(LoginMenu.loginUsername);
    }

    public void exploderDagon() {
        DuelMenu.getInstance().setSelectCard(null, 0);
    }

    public void terratiger() {
        GameProgramController.getInstance().summon(LoginMenu.loginUsername.getUsername());
    }

    public void theTricky() {
        DuelMenu.getInstance().setCardsInHand(DuelMenu.duelMenu.getFirstPlayer());
        GameProgramController.getInstance().summon(DuelMenu.duelMenu.getFirstPlayer().getUsername());
    }

    public void terraforming() {
        for (int i = 0; i < Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getFieldZone().size(); ++i) {
            if (Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getFieldZone().get(i).getKind().equals("Field Spell")) {
                Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).setCardsInHand(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getFieldZone().get(i), 0);
                break;
            }
        }
    }

    public void raigeki() {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getMonsterCardZoneArray().clear();
    }

    public void changeOfHeart() {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).setMonsterCardZone(Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getMonsterCardZone(0), 0);
    }

    public void harpiesFeatherDuster() {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getSpellCardZone().clear();
    }

    public void mysticalSpaceTyphoon() {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getSpellCardZone().remove(0);
    }

    public void twinTwisters() {
        Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).setCardsInHand(null, 0);
        Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getSpellCardZone().remove(0);
        Players.getPlayerByUsername(DuelMenu.duelMenu.getSecondPlayer().getUsername()).getSpellCardZone().remove(1);
    }

    public void supplySquad() {
        Deck.getDeckByOwner(DuelMenu.duelMenu.getFirstPlayer()).setCardsInDecks(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getMainDecks().getMainDeckCards().get(0).getCardName());
    }

    public void potOfGreed() {
        Deck.getDeckByOwner(DuelMenu.duelMenu.getFirstPlayer()).setCardsInDecks(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getMainDecks().getMainDeckCards().get(0).getCardName());
        Deck.getDeckByOwner(DuelMenu.duelMenu.getFirstPlayer()).setCardsInDecks(Players.getPlayerByUsername(DuelMenu.duelMenu.getFirstPlayer().getUsername()).getMainDecks().getMainDeckCards().get(1).getCardName());
    }

    public void monsterReborn() {
        GameProgramController.getInstance().summon(LoginMenu.loginUsername.getUsername());
    }

    public void yami(String opponent) {
        for (Cards card : Players.getPlayerByUsername(opponent).getMonsterCardZoneArray()) {
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

    public void forest(String opponent) {
        for (Cards card : Players.getPlayerByUsername(opponent).getMonsterCardZoneArray()) {
            if (card.getKind().equals("Insect") || card.getKind().equals("Beast") || card.getKind().equals("Beast-Warrior")) {
                card.increaseATK(200);
                card.increaseDEF(200);
            }
        }
    }

    public void closedForest(String turn) {
        for (Cards card : Players.getPlayerByUsername(turn).getCardsInGraveyard()) {
            if (card.getKind().equals("Insect") || card.getKind().equals("Beast") || card.getKind().equals("Beast-Warrior")) {
                card.increaseATK(100);
            }
        }
    }

    public void umiiruka() {
        for (Cards card : Cards.allCards) {
            if (card.getKind().equals("Aqua")) {
                card.increaseATK(500);
                card.decreaseDEF(400);
            }
        }
    }

    public void swordOfDarkDestruction(Cards cards) {
        if (cards.getKind().equals("Aqua")) {
            cards.decreaseDEF(400);
            cards.increaseATK(500);
        }
    }

    public void blackPendant(Cards cards) {
        cards.increaseATK(500);
    }

    public void unitedWeStand(Cards cards) {
        cards.increaseATK(800);
    }

    public void magnumShield(String turn) {
        for (Cards card : Players.getPlayerByUsername(turn).getMonsterCardZoneArray()) {
            if (Players.getPlayerByUsername(turn).getMonsterZone().get(card).equals("OO")) {
                card.increaseATK(card.getDEF());
            }
            if (Players.getPlayerByUsername(turn).getMonsterZone().get(card).equals("DH")) {
                card.increaseDEF(card.getATK());
            }

        }
    }

    public void advancedRitualArt(String turn, String position, Cards cardName) {
        GameProgramController.getInstance().ritualSummon(turn, position);
        Players.getPlayerByUsername(turn).setCardsInGraveyard(cardName);
    }

    public void magicCylinder(Cards cards, String opponent) {
        Players.getPlayerByUsername(opponent).decreaseLifePoint(cards.getATK());
    }

    public void mirrorForce(String opponent) {
        for (Cards card : Players.getPlayerByUsername(opponent).getMonsterCardZoneArray()) {
            if (Players.getPlayerByUsername(opponent).getMonsterZone().get(card).equals("OO")) {
                Players.getPlayerByUsername(opponent).removeFromArrayList(Players.getPlayerByUsername(opponent).getMonsterCardZoneArray(), Players.getPlayerByUsername(opponent).getMonsterZone(), card);
                Players.getPlayerByUsername(opponent).setCardsInGraveyard(card);
            }
        }
    }

    public void mindCrush(String opponent, Cards cards) {
        int done = 0;
        for (Cards card : Players.getPlayerByUsername(opponent).getMonsterCardZoneArray()) {
            if (card.equals(cards)) {
                Players.getPlayerByUsername(opponent).removeFromArrayList(Players.getPlayerByUsername(opponent).getMonsterCardZoneArray(), Players.getPlayerByUsername(opponent).getMonsterZone(), cards);
                ++done;
            }
        }
        if (done == 0) {
            for (Cards card : Players.getPlayerByUsername(opponent).getMonsterCardZoneArray()) {
                Players.getPlayerByUsername(opponent).removeFromArrayList(Players.getPlayerByUsername(opponent).getMonsterCardZoneArray(), Players.getPlayerByUsername(opponent).getMonsterZone(), cards);
                return;
            }
        }
    }

    public void solemnWarning(String turn) {
        Players.getPlayerByUsername(turn).decreaseLifePoint(2000);
    }

    public void callOfTheHaunted(String turn) {
        GameProgramController.getInstance().summon(turn);
    }

    public void commandKnight(String turn) {
        for (Cards card : Players.getPlayerByUsername(turn).getMonsterCardZoneArray()
        ) {
            Players.getPlayerByUsername(turn).increaseATK(card);
        }
        while (Players.getPlayerByUsername(turn).getMonsterCardZoneArray().size() > 1) {
            Cards.getCardByName("command Knight").setAttackable(false);
        }
    }

    public void yomiShip(String opponent, MonsterCard attackingCard) {
        Cards thisCard = Cards.getCardByName("yomi ship");
        if (attackingCard.getATK() > thisCard.getDEF()) {
            Players.getPlayerByUsername(opponent).getMonsterCardZoneArray().remove(attackingCard);
        }
    }

    public void suijin(String opponent, MonsterCard attackingCard) {
        int repeat = 0;
        int originalATK = attackingCard.getATK();
        if (repeat == 0) {

            attackingCard.setATK(0);
            repeat += 1;
        }
        attackingCard.setATK(originalATK);

    }

    public void crabTurtle() {
        Cards.getCardByName("crab Turtle").canRitualSummon();
    }

    public void skullGuardian() {
        Cards.getCardByName("skull Guardian").canRitualSummon();
    }

    public void manEaterBug(String opponent, Cards card) {
        Players.getPlayerByUsername(opponent).getMonsterCardZoneArray().remove(card);
    }

    public void gateGuardian(String turn, ArrayList<MonsterCard> toBeSacirfised) {
        for (MonsterCard card : toBeSacirfised
        ) {
            Players.getPlayerByUsername(turn).getMonsterCardZoneArray().remove(card);
        }
        Cards.getCardByName("Gate Guardian").setCanSummon(true);
    }

    public void scannerCard(String turn, String opponent, Cards cards) {
        Players.getPlayerByUsername(turn).putInMonsterZone(cards, cards.getCardName());
    }

}
