package View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenu {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private ArrayList<DuelMenu> duelGame = new ArrayList<>();
    private String firstPlayer;
    private String secondPlayer;
    private String phaseName;
    private String showTurn;
    private int round;
    private String cardZoneSelected;
    private int cardAddressNumberSelected;

    public DuelMenu(String firstPlayer, String secondPlayer, int round) {
        setFirstPlayer(firstPlayer);
        setSecondPlayer(secondPlayer);
        setRound(round);
        duelGame.add(this);
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(String firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(String secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public void showGameBoard(String firstPlayer, String secondPlayer) {

    }

    public void setSelectCard(String cardAddressSelected, int cardAddressNumberSelected) {
        setCardZoneSelected(cardAddressSelected);
        setCardAddressNumberSelected(cardAddressNumberSelected);
    }

    public void deselectCard() {
        setCardZoneSelected(null);
        setCardAddressNumberSelected(0);
    }

    public void setWinner() {

    }

    public void cheatSelectCardHand() {

    }

    public String getPhaseName() {
        return phaseName;
    }

    public void setPhaseName(String phaseName) {
        this.phaseName = phaseName;
    }

    public String getShowTurn() {
        return showTurn;
    }

    public void setShowTurn(String showTurn) {
        this.showTurn = showTurn;
    }

    public void summonCard(String cardName) {

    }

    public String getCardZoneSelected() {
        return cardZoneSelected;
    }

    public void setCardZoneSelected(String cardZoneSelected) {
        this.cardZoneSelected = cardZoneSelected;
    }

    public int getCardAddressNumberSelected() {
        return cardAddressNumberSelected;
    }

    public void setCardAddressNumberSelected(int cardAddressNumberSelected) {
        this.cardAddressNumberSelected = cardAddressNumberSelected;
    }
}