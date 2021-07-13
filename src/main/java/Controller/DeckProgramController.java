package Controller;

import Model.Cards;
import Model.Deck;

import java.util.ArrayList;
import java.util.regex.Matcher;

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

    public ArrayList<String> deckAddCard(Matcher matcher) {
        ArrayList<String> data = new ArrayList<>();
        String card = null;
        String deck = null;
        String side = null;
        for (int i = 1; i < 4; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--card")) {
                    card = matcher.group(i).replaceAll("--card", "").trim();
                } else if (matcher.group(i).contains("--deck")) {
                    deck = matcher.group(i).replaceAll("--deck", "").trim();
                } else if (matcher.group(i).contains("--side")) {
                    side = matcher.group(i).replaceAll("--side", "").trim();
                }
            }
        }
        data.add(card);
        data.add(deck);
        data.add(side);
        return data;
    }

    public ArrayList<String> deckRemoveCard(Matcher matcher) {
        ArrayList<String> data = new ArrayList<>();
        String card = null;
        String deck = null;
        String side = null;
        for (int i = 1; i < 4; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--card")) {
                    card = matcher.group(i).replaceAll("--card", "").trim();
                } else if (matcher.group(i).contains("--deck")) {
                    deck = matcher.group(i).replaceAll("--deck", "").trim();
                } else if (matcher.group(i).contains("--side")) {
                    side = matcher.group(i).replaceAll("--side", "").trim();
                }
            }
        }
        data.add(card);
        data.add(deck);
        data.add(side);
        return data;
    }

    public ArrayList<String> deckShow(Matcher matcher) {
        ArrayList<String> data = new ArrayList<>();
        String deck = null;
        String side = null;
        for (int i = 1; i < 3; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--deck")) {
                    deck = matcher.group(i).replaceAll("--deck", "").trim();
                } else if (matcher.group(i).contains("--side")) {
                    side = matcher.group(i).replaceAll("--side", "").trim();
                }
            }
        }
        data.add(deck);
        data.add(side);
        return data;
    }
}
