package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Players {
    public static ArrayList<Players> allPlayers = new ArrayList<>();
    private static HashMap<String, String> usernameAndPasswordOfPLayer = new HashMap<>();
    private ArrayList<String> playerCards = new ArrayList<>();
    private ArrayList<String> mainDecks = new ArrayList<>();
    private ArrayList<String> sideDecks = new ArrayList<>();
    private ArrayList<String> fieldZone = new ArrayList<>();
    private ArrayList<String> cardsInGraveyard = new ArrayList<>();
    private String[] cardsInHand = new String[6];
    private String[] monsterCardZone = new String[10];
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
        setMoney(100000);
        usernameAndPasswordOfPLayer.put(username, password);
        allPlayers.add(this);
    }

    public static HashMap<String, String> getUsernameAndPasswordOfPLayer() {
        return usernameAndPasswordOfPLayer;
    }

    public static void setUsernameAndPasswordOfPLayer(HashMap<String, String> usernameAndPasswordOfPLayer) {
        Players.usernameAndPasswordOfPLayer = usernameAndPasswordOfPLayer;
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


    public ArrayList<String> getMainDecks() {
        return mainDecks;
    }

    public void setMainDecks(String mainDecks) {
        this.mainDecks.add(mainDecks);
    }

    public ArrayList<String> getSideDecks() {
        return sideDecks;
    }

    public void setSideDecks(String sideDecks) {
        this.sideDecks.add(sideDecks);
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
}
