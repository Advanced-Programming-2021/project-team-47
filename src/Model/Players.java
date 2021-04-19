package Model;

import View.DeckMenu;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Players {
    private static HashMap<String, String> usernameAndPasswordOfPLayer = new HashMap<>();
    private static ArrayList<Players> allPlayers = new ArrayList<>();
    private ArrayList<String> cardsInHand = new ArrayList<>();
    private ArrayList<String> mainDecks = new ArrayList<>();
    private ArrayList<String> sideDecks = new ArrayList<>();
    private ArrayList<String> fieldZone = new ArrayList<>();
    private ArrayList<String> cardsInGraveyard = new ArrayList<>();
    private String[] deckZone = new String[6];
    private String username;
    private String nickname;
    private String password;
    private int score;
    private int lifePoint;
    private int money;

    public Players(String username, String nickname, String password) {
        setUsername(username);
        setNickname(nickname);
        setPassword(password);
        usernameAndPasswordOfPLayer.put(username, password);
        allPlayers.add(this);
    }

    public static HashMap<String, String> getUsernameAndPasswordOfPLayer() {
        return usernameAndPasswordOfPLayer;
    }

    public static void setUsernameAndPasswordOfPLayer(HashMap<String, String> usernameAndPasswordOfPLayer) {
        Players.usernameAndPasswordOfPLayer = usernameAndPasswordOfPLayer;
    }

    public static ArrayList<Players> getAllPlayers() {
        return allPlayers;
    }

    public static void setAllPlayers(ArrayList<Players> allPlayers) {
        Players.allPlayers = allPlayers;
    }

    public static Players getPlayerByUsername(String username) {
        for (Players players : allPlayers) {
            if (players.username.equals(username)) {
                return players;
            }
        }
        return null;
    }

    public ArrayList<String> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<String> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    public void setCardsInHand(String cardName) {
        this.cardsInHand.add(cardName);
    }

    public ArrayList<String> getMainDecks() {
        return mainDecks;
    }

    public void setMainDecks(ArrayList<String> mainDecks) {
        this.mainDecks = mainDecks;
    }

    public void setMainDecks(String mainDecks) {
        this.mainDecks.add(mainDecks);
    }

    public ArrayList<String> getSideDecks() {
        return sideDecks;
    }

    public void setSideDecks(ArrayList<String> sideDecks) {
        this.sideDecks = sideDecks;
    }

    public void setSideDecks(String sideDecks) {
        this.sideDecks.add(sideDecks);
    }

    public ArrayList<String> getFieldZone() {
        return fieldZone;
    }

    public void setFieldZone(ArrayList<String> fieldZone) {
        this.fieldZone = fieldZone;
    }

    public void setFieldZone(String fieldZone) {
        this.fieldZone.add(fieldZone);
    }

    public ArrayList<String> getCardsInGraveyard() {
        return cardsInGraveyard;
    }

    public void setCardsInGraveyard(ArrayList<String> cardsInGraveyard) {
        this.cardsInGraveyard = cardsInGraveyard;
    }

    public void setCardsInGraveyard(String cardName) {
        this.cardsInGraveyard.add(cardName);
    }

    public String[] getDeckZone() {
        return deckZone;
    }

    public void setDeckZone(String[] deckZone) {
        this.deckZone = deckZone;
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

    public void increaseMoney(int money) {
        this.money += money;
    }

    public void increaseLifePoint(int lifePoint) {
        this.lifePoint += lifePoint;
    }
}
