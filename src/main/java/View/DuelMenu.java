package View;

import Controller.GameProgramController;
import Model.Players;

import Controller.GameProgramController;
import Model.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    private static DuelMenu duelMenu;
    private String firstPlayer;
    private String secondPlayer;
    private String phaseName;
    private String showTurn;
    private int round;
    private String cardZoneSelected;
    private int cardAddressNumberSelected;

    public void run(String command) {

    }

    public static DuelMenu getInstance() {
        if (duelMenu == null) {
            duelMenu = new DuelMenu();
        }
        return duelMenu;
    }

    public void takeCommand(String command) {
        for (Pattern commandReg : commandMap.keySet())
            if (command.matches(commandReg.pattern())) {
                commandMap.get(commandReg).accept(commandReg.matcher(command));
                return;
            }
        System.out.println("invalid command");
    }
    public void startTwoPlayerDuel(String firstPlayerUsername,String secondPlayerUsername, int rounds) {
        Players thisPlayer=Players.getPlayerByUsername(firstPlayerUsername);
        if (rounds != 3 && rounds != 1) {
            System.out.println(Response.invalidRoundNumber);
            return;
        }
        Players secondPlayer = Players.getPlayerByUsername(secondPlayerUsername);
        if (secondPlayer == null) {
            System.out.println(Response.usernameNotExist);
            return;
        }
        if (secondPlayer.getMainDecks() == null) {
            System.out.println(secondPlayerUsername+" "+Response.activeDeckNotAvailable);
            return;
        }
        Deck firstDeck = Deck.getDeckByID(thisPlayer.getMainDeckID());
        if (!firstDeck.isDeckValid()) {
            System.out.println(firstPlayerUsername+"'s "+Response.invalidDeck);
            return;
        }
        Deck secondDeck = Deck.getDeckByID(secondPlayer.getMainDeckID());
        if (!secondDeck.isDeckValid()) {
            System.out.println(secondPlayerUsername+"'s "+Response.invalidDeck);
            return;
        }
        Random random = new Random();
        boolean isFirstPlayerTurn = random.nextBoolean();
        PlayerController firstPlayerController = new NormalPlayerController(isFirstPlayerTurn ? firstPlayer : secondPlayer);
        PlayerController secondPlayerController = new NormalPlayerController(isFirstPlayerTurn ? secondPlayer : firstPlayer);
        GameProgramController gameController = GameProgramController.getInstance();
        ProgramController.setGameControllerID(gameController.getId());
        gameController.play();
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
