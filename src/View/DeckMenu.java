package View;

import Controller.GameProgramController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenu {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    public static ArrayList<DeckMenu> decks = new ArrayList<>();
    private ArrayList<String> cardsInDecks = new ArrayList<>();
    private String deckName;
    private String owner;
    private String type;
    private int allCardsNumber;
    private boolean isActive;
    private boolean invalidDeck;

    public DeckMenu(String deckName, String owner, String type) {
        setDeckName(deckName);
        setOwner(owner);
        setType(type);
        setActive(false);
    }

    public static DeckMenu getDeckByName(String deckName) {
        for (DeckMenu deck : decks) {
            if (deck.getDeckName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public static DeckMenu getDeckByOwner(String owner) {
        for (DeckMenu deck : decks) {
            if (deck.getOwner().equals(owner)) {
                return deck;
            }
        }
        return null;
    }

    public ArrayList<String> getCardsInDecks() {
        return cardsInDecks;
    }

    public void setCardsInDecks(ArrayList<String> cardsInDecks) {
        this.cardsInDecks = cardsInDecks;
    }

    public int getAllCardsNumber() {
        return allCardsNumber;
    }

    public void setAllCardsNumber(int allCardsNumber) {
        this.allCardsNumber = allCardsNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isInvalidDeck() {
        return invalidDeck;
    }

    public void setInvalidDeck(boolean invalidDeck) {
        this.invalidDeck = invalidDeck;
    }
}
