package Model;

import Controller.GameProgramController;

public class Effects {

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
}
