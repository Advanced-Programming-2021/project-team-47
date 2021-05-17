package View;

import Controller.GameProgramController;
import Controller.Regex;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    public static DuelMenu duelMenu;
    private String firstPlayer;
    private String secondPlayer;
    private ArrayList<Cards> cardsInHand = new ArrayList<>();
    private Cards selectedCard;
    private Cards summonedCard;
    private Cards setCard;
    private String showTurn;
    private String showOpponent;
    private int round;
    private Cards cardZoneSelected;
    private int cardAddressNumberSelected;
    private static ArrayList<Phase> phaseChanger = new ArrayList<>();
    private ArrayList<MonsterCard> monsterCardZone;


    private static Phase phase;

    static {
        phaseChanger.add(Phase.DRAW_PHASE);
        phaseChanger.add(Phase.STANDBY_PHASE);
        phaseChanger.add(Phase.MAIN_PHASE1);
        phaseChanger.add(Phase.BATTLE_PHASE);
        phaseChanger.add(Phase.MAIN_PHASE2);
        phaseChanger.add(Phase.END_PHASE);
    }

    public static DuelMenu getInstance() {
        if (duelMenu == null) {
            duelMenu = new DuelMenu();
        }
        return duelMenu;
    }
    private ArrayList<MonsterCard> monsterCardZone;
    private Phase phase=Phase.MAIN_PHASE1;
    public void run(String command) {
        commandMap.put(Regex.SELECT_MONSTER.label, DuelMenu.commandChecker::selectMonster);
        commandMap.put(Regex.SELECT_SPELL_AND_TRAP.label, DuelMenu.commandChecker::selectSpellAndTrap);
        commandMap.put(Regex.SELECT_OPPONENT_MONSTER.label, DuelMenu.commandChecker::selectOpponentMonster);
        commandMap.put(Regex.SELECT_OPPONENT_SPELL.label, DuelMenu.commandChecker::selectOpponentSpell);
        commandMap.put(Regex.SELECT_FIELD.label, DuelMenu.commandChecker::selectField);
        commandMap.put(Regex.SELECT_OPPONENT_SPELL.label, DuelMenu.commandChecker::selectOpponentSpell);
        commandMap.put(Regex.SELECT_HAND.label, DuelMenu.commandChecker::selectHand);
        commandMap.put(Regex.DIRECT_ATTACK.label, DuelMenu.commandChecker::directAttack);
        commandMap.put(Regex.ACTIVE_EFFECT.label, DuelMenu.commandChecker::activeEffect);
        commandMap.put(Regex.SET.label, DuelMenu.commandChecker::set);
        commandMap.put(Regex.SHOW_GRAVEYARD.label, DuelMenu.commandChecker::showGraveyard);
        commandMap.put(Regex.DUEL_PLAYER.label, DuelMenu.commandChecker::startTwoPlayerDuel);
        commandMap.put(Regex.DUEL_AI.label, DuelMenu.commandChecker::startTwoPlayerDuel);
        commandMap.put(Regex.NEXT_PHASE.label, DuelMenu.commandChecker::nextPhase);

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

    public void setMonsterCardZone(ArrayList<MonsterCard> monsterCardZone) {
        this.monsterCardZone = monsterCardZone;
    }

    public ArrayList<MonsterCard> getMonsterCardZone() {
        return monsterCardZone;
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

    public String getShowOpponent() {
        return showOpponent;
    }

    public void setShowOpponent(String showOpponent) {
        this.showOpponent = showOpponent;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ArrayList<Cards> getAttackedThisTurn() {
        return attackedThisTurn;
    }

    public void showGameBoard(String firstPlayer, String secondPlayer) {

    }

    public void setSelectCard(Cards cardAddressSelected, int cardAddressNumberSelected) {
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

    public void setSummonedCard(Cards summonedCard) {
        this.summonedCard = summonedCard;
    }

    public Cards getSummonedCard() {
        return summonedCard;
    }

    public void setSelectedCard(Cards selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Cards getSelectedCard() {
        return selectedCard;
    }


    public ArrayList<Cards> getCardsInHand() {
        return cardsInHand;
    }

    public void setSetCard(Cards setCard) {
        this.setCard = setCard;
    }

    public Cards getSetCard() {
        return setCard;
    }

    public String getShowTurn() {
        return showTurn;
    }

    public void setShowTurn(String showTurn) {
        this.showTurn = showTurn;
    }

    public void setPhaseByKey(int key) {
        if (key == 1) {
            this.phase = Phase.MAIN_PHASE1;
            return;
        }
        if (key == 2) {
            this.phase = Phase.MAIN_PHASE2;
            return;
        }
        if (key == 3) {
            this.phase = Phase.DRAW_PHASE;
            return;
        }
        if (key == 4) {
            this.phase = Phase.STANDBY_PHASE;
            return;
        }
        if (key == 5) {
            this.phase = Phase.BATTLE_PHASE;
            return;
        }
        if (key == 6) {
            this.phase = Phase.END_PHASE;

        }
    }

    public void nextPhase() {
        Phase currentPhase = DuelMenu.getInstance().getPhase();
        this.setPhaseByKey(currentPhase.getKey() + 1);
        String currentPhaseLabel = this.getPhase().getLabel();
        System.out.println("Phase : " + currentPhase + currentPhaseLabel);
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public void summonCard() {

        if (this.getSelectedCard() == null) {
            System.out.println(Response.notCardSelected);
            return;
        }
        if (!(this.getSelectedCard() instanceof MonsterCard)) {
            System.out.println(Response.cantSummon);
            return;
        }
        Cards hold = this.getSelectedCard();
        this.setSelectedCard((MonsterCard) hold);
        if (!this.getCardsInHand().contains(selectedCard)
                || !((MonsterCard) this.getSelectedCard()).isNormalSummonValid()) {
            System.out.println(Response.cantSummon);
            return;
        }
        if (this.getPhase() != Phase.MAIN_PHASE1 && this.getPhase() != Phase.MAIN_PHASE2) {
            System.out.println(Response.cantDoActionInThisPhase);
            return;
        }
        if (this.getMonsterCardZone().size() > 4) {
            System.out.println(Response.monsterCardZoneFull);
            return;
        }
        if (this.summonedCard != null
                || this.setCard != null) {
            System.out.println(Response.alreadySummonedOrSet);
            return;
        }

        if (this.selectedCard.getLevel() <= 4) {
            this.setSummonedCard(selectedCard);
            monsterCardZone.add((MonsterCard) selectedCard);
            System.out.println(Response.summonedSuccessfully);
            selectedCard = null;
            summonedCard = null;
            setCard = null;
            return;
        }
        if (this.selectedCard.getLevel() < 7) {
            if (this.getMonsterCardZone().size() == 0) {
                System.out.println(Response.notEnoughCardForTribute);
                return;
            }
            int toBeTributed = GameProgramController.scanner.nextInt();
            if (this.getMonsterCardZone().get(toBeTributed) == null) {
                System.out.println(Response.noMonsterOnThisAddress);
                return;
            }
            System.out.println(Response.summonedSuccessfully);
            this.getMonsterCardZone().remove(toBeTributed);
            this.getMonsterCardZone().add((MonsterCard) selectedCard);
            selectedCard = null;
            summonedCard = null;
            setCard = null;
            return;
        }
        if (this.getMonsterCardZone().size() < 2) {
            System.out.println(Response.notEnoughCardForTribute);
            return;
        }
        int toBeTributedFirst = GameProgramController.scanner.nextInt();
        int toBeTributedSecond = GameProgramController.scanner.nextInt();

        if (this.getMonsterCardZone().get(toBeTributedFirst) == null
                || this.getMonsterCardZone().get(toBeTributedSecond) == null) {
            System.out.println(Response.noMonsterOnOneOfAddress);
            return;
        }
        System.out.println(Response.summonedSuccessfully);
        this.getMonsterCardZone().remove(toBeTributedFirst);
        this.getMonsterCardZone().remove(toBeTributedSecond);
        this.getMonsterCardZone().add((MonsterCard) selectedCard);
        selectedCard = null;
        summonedCard = null;
        setCard = null;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void flipSummon() {
        if (this.selectedCard == null) {
            System.out.println(Response.notCardSelected);
            return;
        }
        if (!monsterCardZone.contains(selectedCard)) {
            System.out.println(Response.cantChangeCardPosition);
            return;
        }
        if (this.getPhase() != Phase.MAIN_PHASE1
                && this.getPhase() != Phase.MAIN_PHASE2) {
            System.out.println(Response.cantDoActionInThisPhase);
            return;
        }
        if (state != State.DH) {
            System.out.println(Response.cantFlipSummonCard);
            return;
        }
        setState(State.OO);
        System.out.println(Response.flipSummonCardSuccessfully);
    }

    public void ritualSummon() {
        int toCheck = 0;
        ArrayList<Cards> ritualMonsterCards = new ArrayList<>();
        for (int i = 0; i < this.getCardsInHand().size(); i++) {
            if (this.getCardsInHand().get(i).getRitual()) {
                toCheck++;
                ritualMonsterCards.add(this.getCardsInHand().get(i));
            }
        }
        int n = this.getCardsInHand().size();
        boolean canRitualSummon = false;
        for (int i = 0; i < (1 << n); i++) {
            ArrayList<Cards> cardsInHandSubsets = new ArrayList<>();
            for (int j = 0; j < n; j++)


                if ((i & (1 << j)) > 0)
                    cardsInHandSubsets.add(getCardsInHand().get(j));

            int levelSub = 0;
            for (Cards card : cardsInHandSubsets) {
                levelSub += card.getLevel();
            }
            for (Cards card : ritualMonsterCards) {
                if (card.getLevel() == levelSub) {
                    canRitualSummon = true;
                    break;
                }
            }
        }
        if (toCheck == 0
                || !canRitualSummon) {
            System.out.println(Response.noWayCouldRitualSummonMonster);
            return;
        }
        int sum=0;
        for (Integer i : toRemoveForRitual
        ) {
            sum+=this.getMonsterCardZone().get(i).getLevel();
        }
        if (sum!=selectedCard.getLevel()){
            System.out.println(Response.dontMatchLevels);
            setToRemoveForRitual();
            return;
        }



    }

    public void setToRemoveForRitual(int i) {
        toRemoveForRitual.add(i);
    }

    public Phase getPhase() {
        return phase;
    }

    public void setCardsInHand(Players players) {
        Random random = new Random();
        MainDeck playersMainDeck = (MainDeck) players.getActiveDeck().get(0);
        for (int i = 0; i < 6; i++) {
            Cards toBeAdded = playersMainDeck.getMainDeckCards().get(random.nextInt(playersMainDeck.getMainDeckCards().size()));
            cardsInHand.add(toBeAdded);
            playersMainDeck.getMainDeckCards().remove(toBeAdded);
        }
    }

    public Cards getCardZoneSelected() {
        return cardZoneSelected;
    }

    public void setCardZoneSelected(Cards cardZoneSelected) {
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
        for (int z = 0; z < Players.getPlayerByUsername(username).getMonsterCardZoneArray().size(); ++z) {
            System.out.print(Players.getPlayerByUsername(username).getMonsterCardZone(z));
            System.out.println("    ");
        }
        for (int j = 0; j < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++j) {
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
    public void gameBoardPrintOpponent(String username){
        String fieldZone;
        if (Players.getPlayerByUsername(username).getFieldZone().size() == 0) {
            fieldZone = "E";
        } else {
            fieldZone = "O";
        }
        System.out.println(Players.getPlayerByUsername(username).getCardsInGraveyard().size() + "                      " + fieldZone);
        for (int j = 0; j < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++j) {
            System.out.print(Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(j));
            System.out.println("    ");
        }
        for (int z = 0; z < Players.getPlayerByUsername(username).getMonsterCardZoneArray().size(); ++z) {
            System.out.print(Players.getPlayerByUsername(username).getMonsterCardZone(z));
            System.out.println("    ");
        }
        System.out.println("    ");
        System.out.println(Deck.getDeckByOwner(username).getAllCardsNumber());
        System.out.println();
        for (int i = 0; i < Players.getPlayerByUsername(username).getAllCardsInHandsArray().size(); ++i) {
            System.out.print(Players.getPlayerByUsername(username).getCardsInHand(i));
            System.out.println("    ");
        }
        System.out.println("    ");
        System.out.println(Players.getPlayerByUsername(username).getNickname() + ":" + Players.
                getPlayerByUsername(username).getLifePoint());

    public void gameBoard(String firstPlayer, String secondPlayer) {
        gameBoardPrint(firstPlayer);
        System.out.println("--------------------------");
        gameBoardPrint(secondPlayer);
    }

    static class commandChecker {
        static void nextPhase(Matcher matcher) {
            if (matcher.find()) {
                for (int i = 0; i < DuelMenu.phaseChanger.size(); i++) {
                    if (duelMenu.getPhase().equals(DuelMenu.phaseChanger.get(i)) && i != DuelMenu.phaseChanger.size() - 1)
                        duelMenu.setPhase(DuelMenu.phaseChanger.get(i + 1));
                    else if (duelMenu.getPhase().equals(DuelMenu.phaseChanger.get(i)) && i == DuelMenu.phaseChanger.size() - 1)
                        duelMenu.setPhase(DuelMenu.phaseChanger.get(0));
                }
            }
        }

        static void selectMonster(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (Players.getPlayerByUsername(duelMenu.getShowTurn()).getMonsterCardZone(selectedCoordinates) != null) {
                    processSelect(Players.getPlayerByUsername(duelMenu.getShowTurn()).getMonsterCardZone
                            (selectedCoordinates), selectedCoordinates);
                } else {
                    System.out.println(Response.notFoundCardinPosition);
                }
            } else System.out.println(Response.invalidSelection);
        }

        static void selectSpellAndTrap(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (Players.getPlayerByUsername(duelMenu.getShowTurn()).getSpellCardZoneByCoordinate(selectedCoordinates) != null) {
                    processSelect(Players.getPlayerByUsername(duelMenu.getShowTurn()).getSpellCardZoneByCoordinate
                            (selectedCoordinates), selectedCoordinates);
                } else System.out.println(Response.notFoundCardinPosition);
            } else System.out.println(Response.invalidSelection);
        }

        static void selectOpponentMonster(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (Players.getPlayerByUsername(duelMenu.getShowOpponent()).getMonsterCardZone(selectedCoordinates) != null) {
                    processSelect(Players.getPlayerByUsername(duelMenu.getShowOpponent()).getMonsterCardZone
                            (selectedCoordinates), selectedCoordinates);
                } else {
                    System.out.println(Response.notFoundCardinPosition);
                }
            } else System.out.println(Response.invalidSelection);
        }

        static void selectOpponentSpell(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (Players.getPlayerByUsername(duelMenu.getShowOpponent()).getSpellCardZoneByCoordinate(selectedCoordinates) != null) {
                    processSelect(Players.getPlayerByUsername(duelMenu.getShowOpponent()).getSpellCardZoneByCoordinate
                            (selectedCoordinates), selectedCoordinates);
                } else System.out.println(Response.notFoundCardinPosition);
            } else System.out.println(Response.invalidSelection);
        }

        static void selectField(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (Players.getPlayerByUsername(duelMenu.getShowTurn()).getFieldZoneByCoordinates(selectedCoordinates) != null) {
                    processSelect(Players.getPlayerByUsername(duelMenu.getShowTurn()).getFieldZoneByCoordinates
                            (selectedCoordinates), selectedCoordinates);
                } else System.out.println(Response.notFoundCardinPosition);
            } else System.out.println(Response.invalidSelection);
        }

        static void selectHand(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (Players.getPlayerByUsername(duelMenu.getShowTurn()).getCardsInHand(selectedCoordinates) != null) {
                    processSelect(Players.getPlayerByUsername(duelMenu.getShowTurn()).getCardsInHand
                            (selectedCoordinates), selectedCoordinates);
                } else System.out.println(Response.notFoundCardinPosition);
            } else System.out.println(Response.invalidSelection);
        }

        static void directAttack(Matcher matcher) {
            if (matcher.find()) {
                if (duelMenu.getCardZoneSelected() != null)
                    System.out.println(Response.notCardSelected);
                else if (!(duelMenu.getCardZoneSelected() instanceof MonsterCard))
                    System.out.println(Response.cantAttackWithThisCard);
                else if (!duelMenu.getPhase().equals(Phase.BATTLE_PHASE))
                    System.out.println(Response.cantDoActionInThisPhase);
                else if (duelMenu.getAttackedThisTurn().contains(duelMenu.getCardZoneSelected()))
                    System.out.println(Response.alreadyAttacked);
                else {
                    Players.getPlayerByUsername(duelMenu.showOpponent).decreaseLifePoint
                            (duelMenu.getCardZoneSelected().getATK());
                    System.out.println("you opponent receives "+duelMenu.getCardZoneSelected()
                            .getATK()+" battale damage");
                }
            }
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

    private static void processSelect(Cards cards, int selectedCoordinates) {
        duelMenu.setCardZoneSelected(cards);
        duelMenu.setCardAddressNumberSelected(selectedCoordinates);
        System.out.println(Response.cardSelected);
    }
}