package Model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Deck {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    public static ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<String> cardsInDecks = new ArrayList<>();
    private ArrayList<String> cardsInSideDecks = new ArrayList<>();
    private String deckName;
    private String owner;
    private int allCardsNumber;
    private boolean isActive;
    private boolean invalidDeck;

    public Deck(String deckName, String owner) {
        setDeckName(deckName);
        setOwner(owner);
        setActive(false);
        decks.add(this);
    }

    public static boolean isDeckValid(Deck deck, int type) {
        // type=1 --> mainDeck type=-1 --> sideDeck

        if (deck == null) return false;
        if (type == 1) {
            if (getNumberOfCards(deck) < 40 || getNumberOfCards(deck) > 60) return false;
            return true;
        }
        if (type == -1) {
            if (getNumberOfCards(deck) > 15) return false;
            return true;
        }
        return false;
    }

    public static Deck getDeckByName(String deckName) {
        for (Deck deck : decks) {
            if (deck.getDeckName().equals(deckName)) {
                return deck;
            }
        }
        return null;
    }

    public static int getNumberOfCards(Deck deck) {
        return deck.cardsInDecks.size();
    }

    public static Deck getDeckByOwner(String owner) {
        for (Deck deck : decks) {
            if (deck.getOwner().equals(owner)) {
                return deck;
            }
        }
        return null;
    }

    public abstract void addToDeck(Cards card);

    public ArrayList<String> getCardsInDecks() {
        return cardsInDecks;
    }

    public ArrayList<String> getCardsInSideDecks() {
        return cardsInSideDecks;
    }

    public void setCardsInDecks(String cardsInDecks) {
        this.cardsInDecks.add(cardsInDecks);
    }

    public void removeCardsInDecks(String cardsInDecks) {
        this.cardsInDecks.remove(cardsInDecks);
    }

    public void removeCardsInSideDecks(String cardsInSideDecks) {
        this.cardsInSideDecks.remove(cardsInSideDecks);
    }

    public void setCardsInSideDecks(String cardsInSideDecks) {
        this.cardsInSideDecks.add(cardsInSideDecks);
    }

    public int getAllCardsNumber() {
        return allCardsNumber;
    }

    public void setAllCardsNumber(int allCardsNumber) {
        this.allCardsNumber = allCardsNumber;
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
