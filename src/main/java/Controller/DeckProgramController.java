package Controller;

import Model.Cards;
import Model.Deck;

public class DeckProgramController {

    private static DeckProgramController deckProgramController;

    public static DeckProgramController getInstance() {
        if (deckProgramController == null) {
            deckProgramController = new DeckProgramController();
        }
        return deckProgramController;
    }

    public boolean checkDeckNameExist(String deckName) {
        return Deck.getDeckByName(deckName) != null;
    }

    public boolean checkCardNameExist(String cardName) {
        return Cards.getCardByName(cardName) != null;
    }

    public boolean isDeckFull(String deckName, String cardName) {
        int counter = 0;
        for (Deck checkDeck : Deck.decks) {
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
