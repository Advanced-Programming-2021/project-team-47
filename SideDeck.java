package Model;

import java.util.ArrayList;

public class SideDeck extends Deck {
    public SideDeck(String deckName, String owner) {
        super(deckName, owner);
    }

    public static boolean isDeckValid(SideDeck sideDeck) {

        if (sideDeck == null) return false;

        return getNumberOfCards(sideDeck) <= 15;


    }

    public ArrayList<Cards> sideDeckCards = new ArrayList<>();

    public ArrayList<Cards> getSideDeckCards() {
        return sideDeckCards;
    }

    public void addToDeck(Cards card) {
        this.sideDeckCards.add(card);
    }

}
