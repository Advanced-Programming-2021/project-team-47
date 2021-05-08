package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class Players {
    public static ArrayList<Players> allPlayers = new ArrayList<>();
    private static HashMap<String, String> usernameAndPasswordOfPLayer = new HashMap<>();
    private ArrayList<String> playerCards = new ArrayList<>();
    private MainDeck mainDecks;
    private SideDeck sideDecks;
    private ArrayList<String> fieldZone = new ArrayList<>();
    private ArrayList<String> cardsInGraveyard = new ArrayList<>();
    private String[] cardsInHand = new String[6];
    private String[] monsterCardZone = new String[6];
    private String[] spellCardZone = new String[6];
    private String username;
    private String nickname;
    private String password;
    private int score;
    private int lifePoint;
    private int money;
    private ArrayList<Deck> activeDeck = new ArrayList<>(); //first element:mainDeck ,, second element:sideDeck

    public Players(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        setMoney(100000);
        usernameAndPasswordOfPLayer.put(username, password);
        allPlayers.add(this);
    }

    public ArrayList<Deck> getActiveDeck() {
        return activeDeck;
    }

    public void setActiveDeck(ArrayList<Deck> activeDeck) {
        this.activeDeck = activeDeck;
        for (Deck deck:this.activeDeck
             ) {
            deck.setActive();
        }
    }

    public static boolean isActiveDeckValid(Players players) {
        if (players.activeDeck.size() != 2) return false;
        if (!(players.activeDeck.get(0) instanceof MainDeck)) return false;
        if (!(players.activeDeck.get(1) instanceof SideDeck)) return false;
        ArrayList<Cards> allOfCards = new ArrayList<>();
        MainDeck playerMainDeck = (MainDeck) players.activeDeck.get(0);
        SideDeck playerSideDeck = (SideDeck) players.activeDeck.get(1);

        allOfCards.addAll(playerMainDeck.getMainDeckCards());
        allOfCards.addAll(playerSideDeck.getSideDeckCards());
        for (Cards card : allOfCards) {
            if (Collections.frequency(allOfCards, card) > 3) return false;
        }
        return true;
    }

    public static HashMap<String, String> getUsernameAndPasswordOfPLayer() {
        return usernameAndPasswordOfPLayer;
    }

    public static void setUsernameAndPasswordOfPLayer(HashMap<String, String> usernameAndPasswordOfPLayer) {
        Players.usernameAndPasswordOfPLayer = usernameAndPasswordOfPLayer;
    }
    public void setMainDecks(MainDeck mainDecks) {
        this.mainDecks = mainDecks;
    }

    public void setSideDecks(SideDeck sideDecks) {
        this.sideDecks = sideDecks;
    }

    public static Players getPlayerByUsername(String username) {
        for (Players players : allPlayers) {
            if (players.username.equals(username)) {
                return players;
            }
        }
        return null;
    }

    public static Players getPlayerByNickName(String nickname) {
        for (Players players : allPlayers) {
            if (players.nickname.equals(nickname)) {
                return players;
            }
        }
        return null;
    }

    public SideDeck getSideDecks() {
        return this.sideDecks;
    }

    public MainDeck getMainDecks() {
        return this.mainDecks;
    }

    public String getMonsterCardZone(int x) {
        return monsterCardZone[x];
    }

    public String[] getSpellCardZone() {
        return spellCardZone;
    }

    public String getSpellCardZoneByCoordinate(int x) {
        return spellCardZone[x];
    }

    public void setMonsterCardZone(String monsterCardZone, int x) {
        this.monsterCardZone[x] = monsterCardZone;
    }

    public void setSpellCardZone(String spellCardZone, int x) {
        this.spellCardZone[x] = spellCardZone;
    }

    public String getCardsInHand(int x) {
        return cardsInHand[x];
    }

    public void setCardsInHand(String cardsInHand, int x) {
        this.cardsInHand[x] = cardsInHand;
    }

    public ArrayList<String> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(String playerCards) {
        this.playerCards.add(playerCards);
    }


    public ArrayList<String> getFieldZone() {
        return fieldZone;
    }

    public void setFieldZone(String fieldZone) {
        this.fieldZone.add(fieldZone);
    }

    public ArrayList<String> getCardsInGraveyard() {
        return cardsInGraveyard;
    }

    public void setCardsInGraveyard(String cardName) {
        this.cardsInGraveyard.add(cardName);
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    private void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
        usernameAndPasswordOfPLayer.put(this.username, password);
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void decreaseMoney(int money) {
        this.money -= money;
    }

    public void increaseMoney(int money) {
        this.money += money;
    }

    public void increaseLifePoint(int lifePoint) {
        this.lifePoint += lifePoint;
    }

    public void decreaseLifePoint(int lifePoint) {
        this.lifePoint -= lifePoint;
    }
}
