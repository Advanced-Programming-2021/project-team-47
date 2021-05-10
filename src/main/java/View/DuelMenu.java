package View;

import Controller.GameProgramController;
import Controller.Regex;
import Model.Deck;
import Model.MainDeck;
import Model.Players;
import Model.SideDeck;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    public static DuelMenu duelMenu;
    private String firstPlayer;
    private String secondPlayer;
    private String phaseName;
    private String showTurn;
    private int round;
    private String cardZoneSelected;
    private int cardAddressNumberSelected;

    public static DuelMenu getInstance() {
        if (duelMenu == null) {
            duelMenu = new DuelMenu();
        }
        return duelMenu;
    }

    public void run(String command) {
        commandMap.put(Regex.DIRECT_ATTACK.label, DuelMenu.commandChecker::directAttack);
        commandMap.put(Regex.ACTIVE_EFFECT.label, DuelMenu.commandChecker::activeEffect);
        commandMap.put(Regex.SET.label, DuelMenu.commandChecker::set);
        commandMap.put(Regex.SHOW_GRAVEYARD.label, DuelMenu.commandChecker::showGraveyard);
        commandMap.put(Regex.DUEL_PLAYER.label, DuelMenu.commandChecker::startTwoPlayerDuel);
        commandMap.put(Regex.DUEL_AI.label, DuelMenu.commandChecker::startTwoPlayerDuel);
    }

    public void takeCommand(String command) {
        for (Pattern commandReg : commandMap.keySet())
            if (command.matches(commandReg.pattern())) {
                commandMap.get(commandReg).accept(commandReg.matcher(command));
                return;
            }
        System.out.println("invalid command");
    }

    public void checkErrorsGame(String username, int rounds) {
        MainDeck playerMainDeck = (MainDeck) Players.getPlayerByUsername(username).getActiveDeck().get(0);
        if (Players.getPlayerByUsername(username) == null) {
            System.out.println(Response.usernameNotExist);
        } else if (Players.getPlayerByUsername(username).getActiveDeck() == null) {
            System.out.println(username + " " + Response.activeDeckNotAvailable);
        } else if (!Players.getPlayerByUsername(username).isActiveDeckValid(Players.getPlayerByUsername(username))) {
            System.out.println(username + "'s " + Response.invalidDeck);
        } else if (!playerMainDeck.isDeckValid(playerMainDeck)) {
            System.out.println(username + "'s " + Response.invalidDeck);
        } else if (rounds != 3 && rounds != 1) {
            System.out.println(Response.invalidRoundNumber);
            return;
        }
        if (rounds == 3) {
            SideDeck playerSideDeck = (SideDeck) Players.getPlayerByUsername(username).getActiveDeck().get(1);
            if (!playerSideDeck.isDeckValid(playerSideDeck)) {
                System.out.println(username + "'s " + Response.invalidDeck);
            }
        }
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

    public void gameBoardPrint(String username) {
        System.out.println(Players.getPlayerByUsername(username).getNickname() + ":" + Players.getPlayerByUsername(username).getLifePoint());
        System.out.println("    ");
        for (int i = 0; i < Players.getPlayerByUsername(username).getAllCardsInHandsArray().size(); ++i) {
            System.out.print(Players.getPlayerByUsername(username).getCardsInHand(i));
            System.out.println("    ");
        }
        System.out.println();
        System.out.println(Deck.getDeckByOwner(username).getAllCardsNumber());
        System.out.println("    ");
        for (int z = 0; z < Players.getPlayerByUsername(username).getMonsterCardZoneArray().length; ++z) {
            System.out.print(Players.getPlayerByUsername(username).getMonsterCardZone(z));
            System.out.println("    ");
        }
        for (int j = 0; j < Players.getPlayerByUsername(username).getSpellCardZone().length; ++j) {
            System.out.print(Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(j));
            System.out.println("    ");
        }
        String fieldZone;
        if (Players.getPlayerByUsername(username).getFieldZone().size() == 0) {
            fieldZone = "E";
        } else {
            fieldZone = "O";
        }
        System.out.println(Players.getPlayerByUsername(username).getCardsInGraveyard().size() + "                      " + fieldZone);
    }

    public void gameBoard(String firstPlayer, String secondPlayer) {
        gameBoardPrint(firstPlayer);
        System.out.println("--------------------------");
        gameBoardPrint(secondPlayer);
    }

    static class commandChecker {
        static void directAttack(Matcher matcher) {

        }

        static void activeEffect(Matcher matcher) {

        }

        static void set(Matcher matcher) {

        }

        static void showGraveyard(Matcher matcher) {

        }

        static void startTwoPlayerDuel(Matcher matcher) {
            String firstPlayerUsername = DuelMenu.getInstance().firstPlayer;
            String secondPlayerUsername = DuelMenu.getInstance().secondPlayer;
            int rounds = DuelMenu.getInstance().round;
            if (!firstPlayerUsername.equals("ai") || !secondPlayerUsername.equals("ai")) {
                DuelMenu.getInstance().checkErrorsGame(firstPlayerUsername, rounds);
                DuelMenu.getInstance().checkErrorsGame(secondPlayerUsername, rounds);
                GameProgramController.getInstance().startMultiplePlayerGame(matcher);
            } else {
                DuelMenu.getInstance().checkErrorsGame(firstPlayerUsername, rounds);
                GameProgramController.getInstance().startMultiplePlayerGame(matcher);
            }
        }
    }
}