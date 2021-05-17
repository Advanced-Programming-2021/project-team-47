package Controller;

import Model.*;
import View.DuelMenu;
import View.LoginMenu;

import java.util.*;
import java.util.regex.Matcher;

public class GameProgramController {
    public static Scanner scanner = new Scanner(System.in);
    private static GameProgramController gameProgramController;

    public static GameProgramController getInstance() {
        if (gameProgramController == null) {
            gameProgramController = new GameProgramController();
        }
        return gameProgramController;
    }

    public void startMultiplePlayerGame(Matcher matcher) {
        String username2 = null;
        String rounds = null;
        for (int i = 1; i < 4; ++i) {
            if (matcher.find()) {
                if (matcher.group(i).contains("--rounds")) {
                    rounds = matcher.group(i).replaceAll("--rounds", "").trim();
                } else if (matcher.group(i).contains("--second-player") || matcher.group(i).contains("--ai")) {
                    username2 = matcher.group(i).replaceAll("--second-player", "").trim();
                }
            }
        }
        DuelMenu.getInstance().setFirstPlayer(LoginMenu.loginUsername);
        DuelMenu.getInstance().setSecondPlayer(username2);
        DuelMenu.getInstance().setRound(Integer.parseInt(rounds));
    }

    public Players getPlayer(String userName) {
        return Players.getPlayerByUsername(userName);
    }

    public boolean isAnyCardSelected() {
        return DuelMenu.getInstance().getCardAddressNumberSelected() == 0 || DuelMenu.getInstance().getCardZoneSelected() == null;
    }

    public void changePhase(Phase phase) {
        DuelMenu.getInstance().setPhase(phase);
    }

    public boolean isMonsterCardZoneFull(String username) {
        int full = 0;
        for (Cards card : Players.getPlayerByUsername(username).getMonsterCardZoneArray()) {
            if (!card.equals(null))
                ++full;
        }
        return full == 5;
    }

    public ArrayList<String> scoreboardShow() {
        ArrayList<Players> scoreSorting = Players.allPlayers;
        scoreSorting.sort(Comparator.comparing(Players::getScore)
                .thenComparing(Players::getUsername));
        ArrayList<String> scoreSortingUsernames = new ArrayList<>();
        for (Players players : scoreSorting) {
            scoreSortingUsernames.add(players.getUsername());
        }
        return scoreSortingUsernames;
    }

    public boolean checkWrongPosition(int position) {
        if (position > 5 || position < 1)
            return false;
        return true;
    }

    public boolean checkCardExistInThisPosition(String username, int position) {
        if (Players.getPlayerByUsername(username).getCardsInHand(position) != null)
            return true;
        return false;
    }

    public boolean isAnyCardSelected(String username, int position) {
        if (DuelMenu.getInstance().getCardAddressNumberSelected() == position || DuelMenu.getInstance().getCardZoneSelected() != null)
            return true;
        return false;
    }


    public void summon(String username) {
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getCardsInHand(i).equals("E")) {
                Players.getPlayerByUsername(username).setCardsInHand("OO", i);
                break;
            }
        }
    }

    public boolean isAnyCard(String username, int addressNumber) {
        if (!Players.getPlayerByUsername(username).getMonsterCardZone(addressNumber).equals("E"))
            return true;
        return false;
    }

    public boolean isTwoMonsterCard(String username) {
        int counter = 0;
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getMonsterCardZone(i).equals("E"))
                ++counter;
            if (counter == 2)
                return true;
        }
        return false;
    }

    public boolean userExist(String username) {
        return Players.getPlayerByUsername(username) != null;
    }

    public boolean userDeckIsActive(String username) {
        return Players.getPlayerByUsername(username).getActiveDeck().size() != 0;
    }

    public void addCardByType(String cardName, int level, String type, int ATK, int DEF, String description, int price, CardTypes cardTypes,String kind) {
        if (type.equals("Monster"))
            new MonsterCard(cardName, level, type, ATK, DEF, description, price, cardTypes,kind);
        else if (type.equals("Trap"))
            new TrapCard(cardName, level, type, ATK, DEF, description, price, cardTypes,kind);
        else
            new SpellCard(cardName, level, type, ATK, DEF, description, price, cardTypes,kind);

    }

    public void normalSet(String username) {
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getCardsInHand(i).equals("E")) {
                Players.getPlayerByUsername(username).setCardsInHand("DH", i);
                break;
            }
        }
    }

    public void flipSummon(String username, int addressNumber) {
        Cards cards = Players.getPlayerByUsername(username).getCardsInHand(addressNumber);
        Players.getPlayerByUsername(username).putInCardsInHandZone(cards, "OO");
    }

    public void attackMonster(String username, int addressNumber) {
        Players playerOpponent = Players.getPlayerByUsername(username);
        Players playerCurrent = Players.getPlayerByUsername(DuelMenu.getInstance().getShowTurn());
        if (playerOpponent.getMonsterCardZone(addressNumber).equals("OO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() > playerOpponent.getMonsterCardZone(addressNumber).getATK()) {
            playerOpponent.decreaseLifePoint(DuelMenu.getInstance().getCardZoneSelected()
                    .getATK() - playerOpponent.getMonsterCardZone(addressNumber).getATK());
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber).getCardName());
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("OO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() == playerOpponent.getMonsterCardZone(addressNumber).getATK()) {
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber).getCardName());
            playerCurrent.setCardsInGraveyard(DuelMenu.getInstance().getCardZoneSelected().getCardName());
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));

        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("OO") && playerOpponent.getMonsterCardZone(addressNumber).
                equals("OO") && DuelMenu.getInstance().getCardZoneSelected().getATK() < playerOpponent.getMonsterCardZone(addressNumber).getATK()) {
            playerCurrent.decreaseLifePoint(playerOpponent.getMonsterCardZone(addressNumber).getATK() - DuelMenu.getInstance().
                    getCardZoneSelected().getATK());
            playerCurrent.setCardsInGraveyard(playerCurrent.getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected())
                    .getCardName());
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() > playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerOpponent.decreaseLifePoint(DuelMenu.getInstance().getCardZoneSelected()
                    .getATK() - playerOpponent.getMonsterCardZone(addressNumber).getATK());
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber).getCardName());
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DO") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() < playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerCurrent.decreaseLifePoint(playerOpponent.getMonsterCardZone(addressNumber).getATK() - DuelMenu.getInstance().
                    getCardZoneSelected().getATK());
            playerCurrent.setCardsInGraveyard(playerCurrent.getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected())
                    .getCardName());
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DH") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() > playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerOpponent.decreaseLifePoint(DuelMenu.getInstance().getCardZoneSelected()
                    .getATK() - playerOpponent.getMonsterCardZone(addressNumber).getATK());
            playerOpponent.setCardsInGraveyard(playerOpponent.getMonsterCardZone(addressNumber).getCardName());
            playerOpponent.removeFromArrayList(playerOpponent.getMonsterCardZoneArray(), playerOpponent.getMonsterZone(),
                    playerOpponent.getMonsterCardZone(addressNumber));
        } else if (playerOpponent.getMonsterCardZone(addressNumber).equals("DH") && DuelMenu.getInstance().getCardZoneSelected()
                .getATK() < playerOpponent.getMonsterCardZone(addressNumber).getDEF()) {
            playerCurrent.decreaseLifePoint(playerOpponent.getMonsterCardZone(addressNumber).getATK() - DuelMenu.getInstance().
                    getCardZoneSelected().getATK());
            playerCurrent.setCardsInGraveyard(playerCurrent.getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected())
                    .getCardName());
            playerCurrent.removeFromArrayList(playerCurrent.getMonsterCardZoneArray(), playerCurrent.getMonsterZone(), playerCurrent
                    .getMonsterCardZone(DuelMenu.duelMenu.getCardAddressNumberSelected()));
        }

    }

    public void directAttack(String username, String cardName) {
        Players.getPlayerByUsername(username).decreaseLifePoint(Cards.getCardByName(cardName).getATK());
    }

    public void activateEffect(String username, Cards cards) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).setSpellCardZone("O", i);
        }
        if (Players.getPlayerByUsername(username).getFieldZone().contains(cards.getCardName()))
            Players.getPlayerByUsername(username).setFieldZone(cards);
    }

    public void setSpell(String username) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).setSpellCardZone("H", i);
        }
    }

    public void setTrap(String username) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().size(); ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).setSpellCardZone("H", i);
        }
    }

    public void swapTurn(String username) {
        DuelMenu game = DuelMenu.getInstance();
        String turn = game.getShowTurn();
        if (turn.equals(game.getFirstPlayer())) {
            DuelMenu.getInstance().setShowTurn(game.getSecondPlayer());
            DuelMenu.getInstance().setShowOpponent(game.getFirstPlayer());
        } else {
            DuelMenu.getInstance().setShowTurn(game.getFirstPlayer());
            DuelMenu.getInstance().setShowOpponent(game.getSecondPlayer());
        }
    }

    public void setPosition(String username, String position) {
        if (position.equals("attack"))
            Players.getPlayerByUsername(username).setMonsterCardZone("OO", DuelMenu.getInstance().getCardAddressNumberSelected());
        else
            Players.getPlayerByUsername(username).setMonsterCardZone("DO", DuelMenu.getInstance().getCardAddressNumberSelected());
    }

    public void ritualSummon(String username, String position) {
        for (int i = 1; i < 10; ++i) {
            if (!Players.getPlayerByUsername(username).getCardsInHand(i).equals("E")) {
                Players.getPlayerByUsername(username).setCardsInHand(position, i);
                break;
            }
        }
    }

}
