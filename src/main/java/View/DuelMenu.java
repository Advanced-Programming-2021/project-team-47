package View;

import Controller.EffectController;
import Controller.GameProgramController;
import Controller.MenuProgramController;
import Controller.Regex;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DuelMenu implements Runnable {
    public static HashMap<Pattern, Consumer<Matcher>> commandMap = new HashMap<>();
    public static DuelMenu duelMenu;
    private ArrayList<Cards> directAttackedThisTurn = new ArrayList<>();
    private ArrayList<Cards> monsterAttackedThisTurn = new ArrayList<>();
    private ArrayList<Cards> activatedThisTurn = new ArrayList<>();
    private ArrayList<Cards> setPositionThisTurn = new ArrayList<>();

    public ArrayList<Cards> getSetPositionThisTurn() {
        return setPositionThisTurn;
    }

    public void setSetPositionThisTurn(Cards setPositionThisTurn) {
        this.setPositionThisTurn.add(setPositionThisTurn);
    }

    private Players firstPlayer;
    private Players secondPlayer;
    private ArrayList<Cards> cardsInHand = new ArrayList<>();
    private Cards selectedCard;
    private Cards summonedCard;
    private Cards setCard;
    private Players showTurn;

    public void setAttackedThisTurn(Cards attackedThisTurn) {
        this.directAttackedThisTurn.add(attackedThisTurn);
    }

    public ArrayList<Cards> getActivatedThisTurn() {
        return activatedThisTurn;
    }

    public void setActivatedThisTurn(Cards activatedThisTurn) {
        this.activatedThisTurn.add(activatedThisTurn);
    }

    private Players showOpponent;
    private Players thisPlayer;
    private int round;
    private static int thisRound = 0;
    private Cards cardZoneSelected;
    private int cardAddressNumberSelected;
    private static ArrayList<Phase> phaseChanger = new ArrayList<>();
    private ArrayList<MonsterCard> monsterCardZone;
    private ArrayList<MonsterCard> toRemoveForRitual = new ArrayList<>();

    public ArrayList<Cards> getMonsterAttackedThisTurn() {
        return monsterAttackedThisTurn;
    }

    public void setMonsterAttackedThisTurn(Cards monsterAttackedThisTurn) {
        this.monsterAttackedThisTurn.add(monsterAttackedThisTurn);
    }

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

    private static Phase phase = Phase.MAIN_PHASE1;

    public void run(String command) {
        commandMap.put(Regex.SELECT_MONSTER.label, DuelMenu.commandChecker::selectMonster);
        commandMap.put(Regex.SUMMON.label, DuelMenu.commandChecker::summonCard);
        commandMap.put(Regex.DESELECT.label, DuelMenu.commandChecker::deselect);
        commandMap.put(Regex.ATTACK.label, DuelMenu.commandChecker::attackMonster);
        commandMap.put(Regex.SURRENDER.label, DuelMenu.commandChecker::surrender);
        commandMap.put(Regex.CANCEL.label, DuelMenu.commandChecker::cancel);
        commandMap.put(Regex.FLIP_SUMMON.label, DuelMenu.commandChecker::flipSummon);
        commandMap.put(Regex.RITUAL_SUMMON.label, DuelMenu.commandChecker::ritualSummon);
        commandMap.put(Regex.SPECIAL_SUMMON.label, DuelMenu.commandChecker::specialSummon);
        commandMap.put(Regex.SELECT_SPELL_AND_TRAP.label, DuelMenu.commandChecker::selectSpellAndTrap);
        commandMap.put(Regex.SELECT_OPPONENT_MONSTER.label, DuelMenu.commandChecker::selectOpponentMonster);
        commandMap.put(Regex.SELECT_OPPONENT_SPELL.label, DuelMenu.commandChecker::selectOpponentSpell);
        commandMap.put(Regex.SELECT_OPPONENT_FIELD.label, DuelMenu.commandChecker::selectField);
        commandMap.put(Regex.SELECT_OPPONENT_SPELL.label, DuelMenu.commandChecker::selectOpponentSpell);
        commandMap.put(Regex.SELECT_HAND.label, DuelMenu.commandChecker::selectHand);
        commandMap.put(Regex.DIRECT_ATTACK.label, DuelMenu.commandChecker::directAttack);
        commandMap.put(Regex.ACTIVE_EFFECT.label, DuelMenu.commandChecker::activeEffect);
        commandMap.put(Regex.SET.label, DuelMenu.commandChecker::set);
        commandMap.put(Regex.DUEL_WIN.label, DuelMenu.commandChecker::setWinner);
        commandMap.put(Regex.SHOW_SELECT_CARDS.label, DuelMenu.commandChecker::showSelectedCard);
        commandMap.put(Regex.SET_POSITION.label, DuelMenu.commandChecker::setPosition);
        commandMap.put(Regex.SHOW_GRAVEYARD.label, DuelMenu.commandChecker::showGraveyard);
        commandMap.put(Regex.DUEL_PLAYER.label, DuelMenu.commandChecker::startTwoPlayerDuel);
        commandMap.put(Regex.DUEL_AI.label, DuelMenu.commandChecker::startTwoPlayerDuel);
        commandMap.put(Regex.NEXT_PHASE.label, DuelMenu.commandChecker::nextPhase);
        commandMap.put(Regex.INCREASE_MONEY.label, DuelMenu.commandChecker::increaseMoney);
        commandMap.put(Regex.INCREASE_LP.label, DuelMenu.commandChecker::increaseLP);
    }

    public void takeCommand(String command) {
        if (thisRound == 1 || thisRound == 3) {
            if (showTurn.getLifePoint() <= 0) {
                ++thisRound;
                System.out.println("Game Ended");
                GameProgramController.getInstance().rewards(showOpponent, showTurn, round);
            } else if (showOpponent.getLifePoint() <= 0) {
                ++thisRound;
                System.out.println("Game Ended");
                GameProgramController.getInstance().rewards(showTurn, showOpponent, round);
            }
        }
        for (Pattern commandReg : commandMap.keySet())
            if (command.matches(commandReg.pattern())) {
                commandMap.get(commandReg).accept(commandReg.matcher(command));
                gameBoard(firstPlayer, secondPlayer);
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

    public Players getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Players firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Players getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Players secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Players getShowOpponent() {
        return showOpponent;
    }

    public void setShowOpponent(Players showOpponent) {
        this.showOpponent = showOpponent;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public ArrayList<Cards> getAttackedThisTurn() {
        return directAttackedThisTurn;
    }

    public void setSelectCard(Cards cardAddressSelected, int cardAddressNumberSelected) {
        setCardZoneSelected(cardAddressSelected);
        setCardAddressNumberSelected(cardAddressNumberSelected);
    }

    public void deselectCard() {
        setCardZoneSelected(null);
        setCardAddressNumberSelected(0);
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

    public Players getShowTurn() {
        return showTurn;
    }

    public void setShowTurn(Players showTurn) {
        this.showTurn = showTurn;
    }

    public void setPhaseByKey(int key) {
        if (key == 3) {
            this.phase = Phase.MAIN_PHASE1;
            return;
        }
        if (key == 5) {
            this.phase = Phase.MAIN_PHASE2;
            return;
        }
        if (key == 1) {
            this.phase = Phase.DRAW_PHASE;
            return;
        }
        if (key == 2) {
            this.phase = Phase.STANDBY_PHASE;
            return;
        }
        if (key == 4) {
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


    public void setToRemoveForRitual(MonsterCard cards) {
        toRemoveForRitual.add(cards);
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

    public void gameBoardPrint(Players username) {
        System.out.println(Players.getPlayerByUsername(username.getUsername()).getNickname() + ":" + Players.getPlayerByUsername(username.getUsername()).getLifePoint());
        System.out.println("    ");
        for (int i = 0; i < Players.getPlayerByUsername(username.getUsername()).getAllCardsInHandsArray().size(); ++i) {
            System.out.print(Players.getPlayerByUsername(username.getUsername()).getCardsInHand(i));
            System.out.println("    ");
        }
        System.out.println();
        System.out.println(Deck.getDeckByOwner(username).getAllCardsNumber());
        System.out.println("    ");
        for (int z = 0; z < Players.getPlayerByUsername(username.getUsername()).getMonsterCardZoneArray().size(); ++z) {
            System.out.print(Players.getPlayerByUsername(username.getUsername()).getMonsterCardZone(z));
            System.out.println("    ");
        }
        for (int j = 0; j < Players.getPlayerByUsername(username.getUsername()).getSpellCardZone().size(); ++j) {
            System.out.print(Players.getPlayerByUsername(username.getUsername()).getSpellCardZoneByCoordinate(j));
            System.out.println("    ");
        }
        String fieldZone;
        if (Players.getPlayerByUsername(username.getUsername()).getFieldZone().size() == 0) {
            fieldZone = "E";
        } else {
            fieldZone = "O";
        }
        System.out.println(Players.getPlayerByUsername(username.getUsername()).getCardsInGraveyard().size() + "                      " + fieldZone);
    }

    public void gameBoardPrintOpponent(Players username) {
        String fieldZone;
        if (Players.getPlayerByUsername(username.getUsername()).getFieldZone().size() == 0) {
            fieldZone = "E";
        } else {
            fieldZone = "O";
        }
        System.out.println(Players.getPlayerByUsername(username.getUsername()).getCardsInGraveyard().size() + "                      " + fieldZone);
        for (int j = 0; j < Players.getPlayerByUsername(username.getUsername()).getSpellCardZone().size(); ++j) {
            System.out.print(Players.getPlayerByUsername(username.getUsername()).getSpellCardZoneByCoordinate(j));
            System.out.println("    ");
        }
        for (int z = 0; z < Players.getPlayerByUsername(username.getUsername()).getMonsterCardZoneArray().size(); ++z) {
            System.out.print(Players.getPlayerByUsername(username.getUsername()).getMonsterCardZone(z));
            System.out.println("    ");
        }
        System.out.println("    ");
        System.out.println(Deck.getDeckByOwner(username).getAllCardsNumber());
        System.out.println();
        for (int i = 0; i < Players.getPlayerByUsername(username.getUsername()).getAllCardsInHandsArray().size(); ++i) {
            System.out.print(Players.getPlayerByUsername(username.getUsername()).getCardsInHand(i));
            System.out.println("    ");
        }
        System.out.println("    ");
        System.out.println(Players.getPlayerByUsername(username.getUsername()).getNickname() + ":" + Players.
                getPlayerByUsername(username.getUsername()).getLifePoint());
    }

    public void gameBoard(Players firstPlayer, Players secondPlayer) {
        gameBoardPrint(firstPlayer);
        System.out.println("--------------------------");
        gameBoardPrintOpponent(secondPlayer);
    }

    public void aiPlay() {
        if (phase.equals(Phase.MAIN_PHASE1) || phase.equals(Phase.MAIN_PHASE2) && !GameProgramController.getInstance().isSpellZoneFull("--ai")) {
            for (int i = 0; i < Players.getPlayerByUsername("--ai").getPlayerCards().size(); ++i) {
                if (Players.getPlayerByUsername("--ai").getCardsInHand(i).getType().equals(CardTypes.SPELL_CARD)) {
                    Players.getPlayerByUsername("--ai").setSpellCardZone(Players.getPlayerByUsername("--ai").getCardsInHand(i), i);
                    break;
                }
            }
            for (int i = 0; i < Players.getPlayerByUsername("--ai").getPlayerCards().size(); ++i) {
                if (Players.getPlayerByUsername("--ai").getCardsInHand(i).getType().equals(CardTypes.TRAP_CARD)) {
                    Players.getPlayerByUsername("--ai").setSpellCardZone(Players.getPlayerByUsername("--ai").getCardsInHand(i), i);
                    break;
                }
            }
        }
        if (GameProgramController.getInstance().isNormalSummonValid() && !GameProgramController.getInstance().isMonsterCardZoneFull("--ai") && phase.equals(Phase.MAIN_PHASE1) || phase.equals(Phase.MAIN_PHASE2))
            GameProgramController.getInstance().summon("--ai");
        if (phase.equals(Phase.BATTLE_PHASE)) {
            int max = 0;
            for (int i = 0; i < Players.getPlayerByUsername("--ai").getMonsterCardZoneArray().size(); ++i) {
                if (Players.getPlayerByUsername("--ai").getMonsterCardZoneArray().get(i).getATK() >= Players.getPlayerByUsername("--ai").getMonsterCardZoneArray().get(i + 1).getATK()) {
                    max = Players.getPlayerByUsername("--ai").getMonsterCardZoneArray().get(i).getATK();
                }
                if (Players.getPlayerByUsername("--ai").getMonsterCardZoneArray().get(i).getATK() >= max) {
                    GameProgramController.getInstance().attackMonster(duelMenu.showOpponent.getUsername(), i);
                    break;
                }
            }
        }
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
                if (duelMenu.getShowTurn().getMonsterCardZone(selectedCoordinates) != null) {
                    processSelect(duelMenu.getShowTurn().getMonsterCardZone
                            (selectedCoordinates), selectedCoordinates);
                } else {
                    System.out.println(Response.notFoundCardinPosition);
                }
            } else System.out.println(Response.invalidSelection);
        }

        static void selectSpellAndTrap(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (duelMenu.getShowTurn().getSpellCardZoneByCoordinate(selectedCoordinates) != null) {
                    processSelect(duelMenu.getShowTurn().getSpellCardZoneByCoordinate
                            (selectedCoordinates), selectedCoordinates);
                } else System.out.println(Response.notFoundCardinPosition);
            } else System.out.println(Response.invalidSelection);
        }

        static void selectOpponentMonster(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (duelMenu.getShowOpponent().getMonsterCardZone(selectedCoordinates) != null) {
                    processSelect(duelMenu.getShowOpponent().getMonsterCardZone
                            (selectedCoordinates), selectedCoordinates);
                } else {
                    System.out.println(Response.notFoundCardinPosition);
                }
            } else System.out.println(Response.invalidSelection);
        }

        static void selectOpponentSpell(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (duelMenu.getShowOpponent().getSpellCardZoneByCoordinate(selectedCoordinates) != null) {
                    processSelect(duelMenu.getShowOpponent().getSpellCardZoneByCoordinate
                            (selectedCoordinates), selectedCoordinates);
                } else System.out.println(Response.notFoundCardinPosition);
            } else System.out.println(Response.invalidSelection);
        }

        static void selectField(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (duelMenu.getShowTurn().getFieldZoneByCoordinates(selectedCoordinates) != null) {
                    processSelect(duelMenu.getShowTurn().getFieldZoneByCoordinates
                            (selectedCoordinates), selectedCoordinates);
                } else System.out.println(Response.notFoundCardinPosition);
            } else System.out.println(Response.invalidSelection);
        }

        static void selectHand(Matcher matcher) {
            if (matcher.find()) {
                int selectedCoordinates = Integer.parseInt(matcher.group(1));
                if (duelMenu.getShowTurn().getCardsInHand(selectedCoordinates) != null) {
                    processSelect(duelMenu.getShowTurn().getCardsInHand
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
                    GameProgramController.getInstance().directAttack(duelMenu.showOpponent.getUsername(), duelMenu.getCardZoneSelected().getCardName());
                    DuelMenu.getInstance().setAttackedThisTurn(DuelMenu.getInstance().selectedCard);
                    System.out.println("you opponent receives " + duelMenu.getCardZoneSelected()
                            .getATK() + " battale damage");
                }
            }
        }

        static void activeEffect(Matcher matcher) {
            if (matcher.find()) {
                if (!GameProgramController.getInstance().isAnyCardSelected()) {
                    System.out.println(Response.notCardSelected);
                } else if (!(DuelMenu.getInstance().getSelectedCard() instanceof SpellCard)) {
                    System.out.println(Response.activeEffectErrorForSpellCard);
                } else if (!phase.equals(Phase.MAIN_PHASE2) && !phase.equals(Phase.MAIN_PHASE1)) {
                    System.out.println(Response.cantDoActionInThisPhase);
                } else if (DuelMenu.getInstance().getActivatedThisTurn().contains(DuelMenu.getInstance().selectedCard)) {
                    System.out.println(Response.alreadyActive);
                } else if (GameProgramController.getInstance().isSpellZoneFull(DuelMenu.getInstance().showTurn.getUsername())) {
                    System.out.println(Response.spellZoneFull);
                } else if (!EffectController.getInstance().takeCommand(duelMenu.getCardZoneSelected().getCardName())) {
                    System.out.println(Response.notDonePreparationsOfSpell);
                } else {
                    GameProgramController.getInstance().activateEffect(DuelMenu.getInstance().showTurn.getUsername(), DuelMenu.getInstance().cardZoneSelected);
                    DuelMenu.getInstance().setActivatedThisTurn(DuelMenu.getInstance().selectedCard);
                    System.out.println(Response.spellActivated);
                }
            }
        }

        static void set(Matcher matcher) {
            if (matcher.find()) {
                if (!GameProgramController.getInstance().isAnyCardSelected()) {
                    System.out.println(Response.notCardSelected);
                } else if (!DuelMenu.getInstance().getCardsInHand().contains(DuelMenu.getInstance().selectedCard)) {
                    System.out.println(Response.cantSetThisCard);
                } else if (!phase.equals(Phase.MAIN_PHASE2) && !phase.equals(Phase.MAIN_PHASE1)) {
                    System.out.println(Response.cantDoActionInThisPhase);
                }
                if ((DuelMenu.getInstance().getSelectedCard() instanceof MonsterCard)) {
                    if (GameProgramController.getInstance().isMonsterCardZoneFull(DuelMenu.getInstance().showTurn.getUsername())) {
                        System.out.println(Response.monsterCardZoneFull);
                    } else if (!GameProgramController.getInstance().isNormalSummonValid()) {
                        System.out.println(Response.alreadySummonedOrSet);
                    } else {
                        GameProgramController.getInstance().normalSet(DuelMenu.getInstance().showTurn.getUsername());
                        System.out.println(Response.setSuccessfully);
                    }
                } else if ((DuelMenu.getInstance().getSelectedCard() instanceof SpellCard)) {
                    if (GameProgramController.getInstance().isSpellZoneFull(DuelMenu.getInstance().showTurn.getUsername())) {
                        System.out.println(Response.spellZoneFull);
                    } else {
                        GameProgramController.getInstance().setSpell(DuelMenu.getInstance().showTurn.getUsername());
                        System.out.println(Response.setSuccessfully);
                    }
                } else if ((DuelMenu.getInstance().getSelectedCard() instanceof TrapCard)) {
                    if (GameProgramController.getInstance().isSpellZoneFull(DuelMenu.getInstance().showTurn.getUsername())) {
                        System.out.println(Response.spellZoneFull);
                    } else {
                        GameProgramController.getInstance().setTrap(DuelMenu.getInstance().showTurn.getUsername());
                        System.out.println(Response.setSuccessfully);
                    }
                }
            }
        }

        static void showGraveyard(Matcher matcher) {
            if (matcher.find()) {
                if (Players.getPlayerByUsername(DuelMenu.getInstance().showTurn.getUsername()).getCardsInGraveyard().size() == 0) {
                    System.out.println(Response.graveyardEmpty);
                } else {
                    int counter = 1;
                    for (Cards card : Players.getPlayerByUsername(DuelMenu.getInstance().showTurn.getUsername()).getCardsInGraveyard()) {
                        System.out.println(counter + ". " + card.getCardName() + ":" + card.getDescription());
                        ++counter;
                    }
                }
            }
        }

        static void startTwoPlayerDuel(Matcher matcher) {
            if (matcher.find()) {
                String firstPlayerUsername = DuelMenu.getInstance().firstPlayer.getUsername();
                String secondPlayerUsername = DuelMenu.getInstance().secondPlayer.getUsername();
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

        public static void deselect(Matcher matcher) {
            if (matcher.find()) {
                if (!GameProgramController.getInstance().isAnyCardSelected()) {
                    System.out.println(Response.notCardSelected);
                } else {
                    GameProgramController.getInstance().deSelected();
                    System.out.println(Response.cardDeselected);
                }
            }
        }

        public static void summonCard(Matcher matcher) {
            if (matcher.find()) {
                if (DuelMenu.getInstance().getSelectedCard() == null) {
                    System.out.println(Response.notCardSelected);
                    return;
                } else if (!(DuelMenu.getInstance().getSelectedCard() instanceof MonsterCard)) {
                    System.out.println(Response.cantSummon);
                    return;
                }
                Cards hold = DuelMenu.getInstance().getSelectedCard();
                DuelMenu.getInstance().setSelectedCard((MonsterCard) hold);
                if (!DuelMenu.getInstance().getCardsInHand().contains(DuelMenu.getInstance().selectedCard)
                        || !GameProgramController.getInstance().isNormalSummonValid()) {
                    System.out.println(Response.cantSummon);
                    return;
                }
                if (DuelMenu.getInstance().getPhase() != Phase.MAIN_PHASE1 && DuelMenu.getInstance().getPhase() != Phase.MAIN_PHASE2) {
                    System.out.println(Response.cantDoActionInThisPhase);
                    return;
                }
                if (DuelMenu.getInstance().getMonsterCardZone().size() > 4) {
                    System.out.println(Response.monsterCardZoneFull);
                    return;
                }
                if (DuelMenu.getInstance().summonedCard != null
                        || DuelMenu.getInstance().setCard != null) {
                    System.out.println(Response.alreadySummonedOrSet);
                    return;
                }

                if (DuelMenu.getInstance().selectedCard.getLevel() <= 4) {
                    DuelMenu.getInstance().setSummonedCard(DuelMenu.getInstance().selectedCard);
                    DuelMenu.getInstance().monsterCardZone.add((MonsterCard) DuelMenu.getInstance().selectedCard);
                    System.out.println(Response.summonedSuccessfully);
                    DuelMenu.getInstance().selectedCard = null;
                    DuelMenu.getInstance().summonedCard = null;
                    DuelMenu.getInstance().setCard = null;
                    return;
                }
                if (DuelMenu.getInstance().selectedCard.getLevel() < 7) {
                    if (DuelMenu.getInstance().getMonsterCardZone().size() == 0) {
                        System.out.println(Response.notEnoughCardForTribute);
                        return;
                    }
                    int toBeTributed = GameProgramController.scanner.nextInt();
                    if (DuelMenu.getInstance().getMonsterCardZone().get(toBeTributed) == null) {
                        System.out.println(Response.noMonsterOnThisAddress);
                        return;
                    }
                    System.out.println(Response.summonedSuccessfully);
                    DuelMenu.getInstance().getMonsterCardZone().remove(toBeTributed);
                    DuelMenu.getInstance().getMonsterCardZone().add((MonsterCard) DuelMenu.getInstance().selectedCard);
                    DuelMenu.getInstance().selectedCard = null;
                    DuelMenu.getInstance().summonedCard = null;
                    DuelMenu.getInstance().setCard = null;
                    return;
                }
                if (DuelMenu.getInstance().getMonsterCardZone().size() < 2) {
                    System.out.println(Response.notEnoughCardForTribute);
                    return;
                }
                int toBeTributedFirst = GameProgramController.scanner.nextInt();
                int toBeTributedSecond = GameProgramController.scanner.nextInt();

                if (DuelMenu.getInstance().getMonsterCardZone().get(toBeTributedFirst) == null
                        || DuelMenu.getInstance().getMonsterCardZone().get(toBeTributedSecond) == null) {
                    System.out.println(Response.noMonsterOnOneOfAddress);
                    return;
                }
                System.out.println(Response.summonedSuccessfully);
                DuelMenu.getInstance().getMonsterCardZone().remove(toBeTributedFirst);
                DuelMenu.getInstance().getMonsterCardZone().remove(toBeTributedSecond);
                DuelMenu.getInstance().getMonsterCardZone().add((MonsterCard) DuelMenu.getInstance().selectedCard);
                DuelMenu.getInstance().selectedCard = null;
                DuelMenu.getInstance().summonedCard = null;
                DuelMenu.getInstance().setCard = null;
            }
        }


        public static void setPosition(Matcher matcher) {
            if (matcher.find()) {
                if (!GameProgramController.getInstance().isAnyCardSelected()) {
                    System.out.println(Response.notCardSelected);
                } else if (!(DuelMenu.getInstance().getSelectedCard() instanceof MonsterCard)) {
                    System.out.println(Response.cantChangeCardPosition);
                } else if (phase != Phase.MAIN_PHASE1 && phase != Phase.MAIN_PHASE2) {
                    System.out.println(Response.cantDoActionInThisPhase);
                } else if (GameProgramController.getInstance().canSetPosition(DuelMenu.getInstance().showTurn.getUsername(), matcher.group(1))) {
                    System.out.println(Response.alreadyInWantedPosition);
                } else if (DuelMenu.getInstance().getSetPositionThisTurn().contains(DuelMenu.getInstance().selectedCard)) {
                    System.out.println(Response.alreadyChangedPosition);
                } else {
                    GameProgramController.getInstance().setPosition(DuelMenu.getInstance().showTurn.getUsername(), matcher.group(1));
                    DuelMenu.getInstance().setSetPositionThisTurn(DuelMenu.getInstance().selectedCard);
                    System.out.println(Response.monsterCardPositionChangedSuccessfully);
                }
            }
        }


        public static void attackMonster(Matcher matcher) {
            if (matcher.find()) {
                if (!GameProgramController.getInstance().isAnyCardSelected()) {
                    System.out.println(Response.notCardSelected);
                } else if (!(DuelMenu.getInstance().getSelectedCard() instanceof MonsterCard)) {
                    System.out.println(Response.cantChangeCardPosition);
                } else if (phase != Phase.BATTLE_PHASE) {
                    System.out.println(Response.cantDoActionInThisPhase);
                } else if (DuelMenu.getInstance().getMonsterAttackedThisTurn().contains(DuelMenu.getInstance().selectedCard)) {
                    System.out.println(Response.alreadyAttacked);
                } else if (!GameProgramController.getInstance().isAnyCard(DuelMenu.getInstance().showOpponent.getUsername(), Integer.parseInt(matcher.group(1)))) {
                    System.out.println(Response.noCardToAttack);
                } else {
                    GameProgramController.getInstance().attackMonster(DuelMenu.getInstance().showOpponent.getUsername(), Integer.parseInt(matcher.group(1)));
                    DuelMenu.getInstance().setMonsterAttackedThisTurn(DuelMenu.getInstance().selectedCard);
                }
            }
        }

        public static void showSelectedCard(Matcher matcher) {
            if (matcher.find()) {
                if (!GameProgramController.getInstance().isAnyCardSelected()) {
                    System.out.println(Response.notCardSelected);
                } else if (duelMenu.getCardZoneSelected() == null) {
                    System.out.println(Response.cardNotVisible);
                } else {
                    System.out.println(DuelMenu.getInstance().cardZoneSelected.getCardName() + ":" + DuelMenu.getInstance().cardZoneSelected.getDescription());
                }
            }
        }

        public static void surrender(Matcher matcher) {
            if (matcher.find())
                System.out.println(DuelMenu.getInstance().showOpponent.getUsername() + " won the game and the score is: " + duelMenu.getShowTurn().getScore() + duelMenu.getShowOpponent().getScore());
        }

        public static void cancel(Matcher matcher) {
            if (matcher.find())
                MenuProgramController.getInstance().run();
        }

        public static void setWinner(Matcher matcher) {
            if (matcher.find())
                GameProgramController.getInstance().rewards(Players.getPlayerByNickName(matcher.group(1)), DuelMenu.duelMenu.getShowOpponent(), DuelMenu.getInstance().round);
        }

        public static void increaseMoney(Matcher matcher) {
            if (matcher.find()) {
                Players.getPlayerByUsername(DuelMenu.getInstance().showTurn.getUsername()).increaseMoney(Integer.parseInt(matcher.group(1)));
            }
        }

        public static void increaseLP(Matcher matcher) {
            if (matcher.find()) {
                Players.getPlayerByUsername(DuelMenu.getInstance().showTurn.getUsername()).increaseLifePoint(Integer.parseInt(matcher.group(1)));
            }
        }

        public static void flipSummon(Matcher matcher) {
            if (DuelMenu.getInstance().selectedCard == null) {
                System.out.println(Response.notCardSelected);
                return;
            }
            if (!DuelMenu.getInstance().monsterCardZone.contains(DuelMenu.getInstance().selectedCard)) {
                System.out.println(Response.cantChangeCardPosition);
                return;
            }
            if (DuelMenu.getInstance().getPhase() != Phase.MAIN_PHASE1
                    && DuelMenu.getInstance().getPhase() != Phase.MAIN_PHASE2) {
                System.out.println(Response.cantDoActionInThisPhase);
                return;
            }
            if (DuelMenu.getInstance().selectedCard.getState() != State.DH) {
                System.out.println(Response.cantFlipSummonCard);
                return;
            }
            DuelMenu.getInstance().selectedCard.setState(State.OO);
            System.out.println(Response.flipSummonCardSuccessfully);

        }

        public static void ritualSummon(Matcher matcher) {
            int toCheck = 0;
            ArrayList<Cards> ritualMonsterCards = new ArrayList<>();
            for (int i = 0; i < DuelMenu.getInstance().getCardsInHand().size(); i++) {
                if (DuelMenu.getInstance().getCardsInHand().get(i).getRitual()) {
                    toCheck++;
                    ritualMonsterCards.add(DuelMenu.getInstance().getCardsInHand().get(i));
                }
            }
            int n = DuelMenu.getInstance().getCardsInHand().size();
            boolean canRitualSummon = false;
            for (int i = 0; i < (1 << n); i++) {
                ArrayList<Cards> cardsInHandSubsets = new ArrayList<>();
                for (int j = 0; j < n; j++)


                    if ((i & (1 << j)) > 0)
                        cardsInHandSubsets.add(DuelMenu.getInstance().getCardsInHand().get(j));

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
            int sum = 0;
            for (int i = 0; i < DuelMenu.getInstance().toRemoveForRitual.size(); ++i) {
                sum += DuelMenu.getInstance().getMonsterCardZone().get(i).getLevel();
            }
            if (sum != DuelMenu.getInstance().selectedCard.getLevel()) {
                System.out.println(Response.dontMatchLevels);
                DuelMenu.getInstance().setToRemoveForRitual((MonsterCard) DuelMenu.getInstance().selectedCard);
            }
            if (!DuelMenu.getInstance().thisPlayer.getAllCardsInHandsArray().contains(Model.Cards.getCardByName("Advanced Ritual Art"))) {
                System.out.println(Response.cantRitualSummon);
            }
            int level = 0;
            ArrayList<Cards> toBeSacrificed = new ArrayList<>();
            while (level != DuelMenu.getInstance().selectedCard.getLevel()) {
                System.out.println(Response.sacrificeAddress);

                String remove = GameProgramController.scanner.nextLine();
                String[] toBeSacrificedAddress = remove.split(" ");
                for (String s : toBeSacrificedAddress) {
                    toBeSacrificed.add(DuelMenu.getInstance().cardsInHand.get(Integer.parseInt(s)));
                }
                for (Cards c : toBeSacrificed) {
                    level += c.getLevel();
                }
                if (DuelMenu.getInstance().selectedCard.getLevel() != level) {
                    System.out.println(Response.dontMatchLevels);
                }
                System.out.println("Input back if you want to cancel :");
                if (GameProgramController.scanner.nextLine().toLowerCase(Locale.ROOT).equals("cancel")) {
                    return;
                }
                level = 0;
            }
            for (Cards c : toBeSacrificed) {
                DuelMenu.getInstance().cardsInHand.remove(c);
                DuelMenu.getInstance().thisPlayer.getCardsInGraveyard().add(c);
            }
            DuelMenu.getInstance().cardsInHand.remove(Model.Cards.getCardByName("Advanced Ritual Art"));
            System.out.println("Do you wanna add this card in attacking state or defensing? :");
            if (GameProgramController.scanner.nextLine().toLowerCase(Locale.ROOT).equals("attacking")) {
                DuelMenu.getInstance().selectedCard.setState(State.OO);
                DuelMenu.getInstance().thisPlayer.getMonsterCardZoneArray().add(DuelMenu.getInstance().selectedCard);
            } else if (GameProgramController.scanner.nextLine().toLowerCase(Locale.ROOT).equals("defensing")) {
                DuelMenu.getInstance().selectedCard.setState(State.DH);
                DuelMenu.getInstance().thisPlayer.getMonsterCardZoneArray().add(DuelMenu.getInstance().selectedCard);
            }
            DuelMenu.getInstance().selectedCard = null;
        }

        public static void specialSummon(Matcher matcher) {
            if (DuelMenu.getInstance().selectedCard.getCanSpecialSummon()) {
                int checker = 0;
                for (Cards c : DuelMenu.getInstance().thisPlayer.getAllCardsInHandsArray()
                ) {
                    if (c instanceof MonsterCard) checker++;
                }
                if (checker == 0) {
                    System.out.println(Response.noWayCouldSpecialSummonMonster);
                    return;
                }
                System.out.println("please select the card you wanna sacrifice :");
                DuelMenu.getInstance().getCardsInHand().remove(DuelMenu.getInstance().selectedCard);
                DuelMenu.getInstance().thisPlayer.getCardsInGraveyard().add(DuelMenu.getInstance().selectedCard);
                DuelMenu.getInstance().selectedCard = null;
            }
        }
    }
}