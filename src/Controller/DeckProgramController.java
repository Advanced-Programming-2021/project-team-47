package Controller;

import Model.Cards;
import View.DeckMenu;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckProgramController {

    public boolean checkDeckNameExist(String deckName) {
        return DeckMenu.getDeckByName(deckName) != null;
    }

    public boolean checkCardNameExist(String cardName) {
        return Cards.getCardByName(cardName) != null;
    }

    public boolean isDeckFull(String deckName, String cardName) {
        int counter = 0;
        for (DeckMenu checkDeck : DeckMenu.decks) {
            if (checkDeck.getDeckName().equals(deckName)) {
                for (int i = 0; i < checkDeck.getCardsInDecks().size(); ++i) {
                    if (checkDeck.getCardsInDecks().get(i).equals(cardName)) {
                        ++counter;
                    }
                    if (counter == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
