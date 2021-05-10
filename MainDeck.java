package Model;

import java.util.ArrayList;
import java.util.Collections;

public class MainDeck extends Deck {
    public MainDeck(String deckName, String owner) {
        super(deckName, owner);
    }

    public static boolean isDeckValid(MainDeck mainDeck) {
        for (Cards cards : mainDeck.mainDeckCards) {
            if (Collections.frequency(mainDeck.mainDeckCards, cards) > 3) return false;
        }

        return getNumberOfCards(mainDeck) >= 40 && getNumberOfCards(mainDeck) <= 60;

    }

    public static int getNumberOfCards(MainDeck mainDeck) {
        return mainDeck.mainDeckCards.size();
    }

    private ArrayList<Cards> mainDeckCards = new ArrayList<>();

    public ArrayList<Cards> getMainDeckCards() {
        return mainDeckCards;
    }

    public void addToDeck(Cards card) {
        this.mainDeckCards.add(card);
    }
}
