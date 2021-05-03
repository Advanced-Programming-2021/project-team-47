package Controller;

import Model.*;
import View.DuelMenu;

import java.util.*;


public class GameProgramController {
    public static Scanner scanner = new Scanner(System.in);
    private static GameProgramController gameProgramController;

    public static GameProgramController getInstance() {
        if (gameProgramController == null) {
            gameProgramController = new GameProgramController();
        }
        return gameProgramController;
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

    public void addCardByType(String cardName, int level, String type, int ATK, int DEF, String description, int price, Style style) {
        if (type.equals("Monster"))
            new MonsterCard(cardName, level, type, ATK, DEF, description, price, style);
        else if (type.equals("Trap"))
            new TrapCard(cardName, level, type, ATK, DEF, description, price, style);
        else
            new SpellCard(cardName, level, type, ATK, DEF, description, price, style);

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
        Players.getPlayerByUsername(username).setCardsInHand("OO", addressNumber);
    }

    public void attackMonster(String username, int addressNumber) {
        Players playerUsername = Players.getPlayerByUsername(username);
        if (playerUsername.getMonsterCardZone(addressNumber).equals("OO") &&) {
            playerUsername.decreaseLifePoint();
            playerUsername.setCardsInGraveyard();
        } else if (playerUsername.getMonsterCardZone(addressNumber).equals("OO") &&)
            playerUsername.setMonsterCardZone("E", addressNumber);
        else if (playerUsername.getMonsterCardZone(addressNumber).equals("OO") &&) {
            playerUsername.decreaseLifePoint();
            playerUsername.setCardsInGraveyard();
        } else if (playerUsername.getMonsterCardZone(addressNumber).equals("DO") &&)
            playerUsername.setMonsterCardZone("E", addressNumber);
        else if (playerUsername.getMonsterCardZone(addressNumber).equals("DO") &&) {
            playerUsername.decreaseLifePoint();
            playerUsername.setCardsInGraveyard();
        } else if (playerUsername.getMonsterCardZone(addressNumber).equals("DH") &&)
            playerUsername.setMonsterCardZone("E", addressNumber);
        else if (playerUsername.getMonsterCardZone(addressNumber).equals("DH") &&) {
            playerUsername.decreaseLifePoint();
            playerUsername.setCardsInGraveyard();
        }

    }

    public void directAttack(String username, String cardName) {
        Players.getPlayerByUsername(username).decreaseLifePoint(Cards.getCardByName(cardName).getATK());
    }

    public void activateEffect(String username, String cardName) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().length; ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).setSpellCardZone("O", i);
        }
        if (Players.getPlayerByUsername(username).getFieldZone().contains(cardName))
            Players.getPlayerByUsername(username).setFieldZone(cardName);
    }

    public void setSpell(String username) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().length; ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).setSpellCardZone("H", i);
        }
    }

    public void setTrap(String username) {
        for (int i = 1; i < Players.getPlayerByUsername(username).getSpellCardZone().length; ++i) {
            if (!Players.getPlayerByUsername(username).getSpellCardZoneByCoordinate(i).equals("E"))
                Players.getPlayerByUsername(username).setSpellCardZone("H", i);
        }
    }

    public void swapTurn(String username) {
        DuelMenu game = DuelMenu.getInstance();
        String turn = game.getShowTurn();
        if (turn.equals(game.getFirstPlayer()))
            DuelMenu.getInstance().setShowTurn(game.getSecondPlayer());
        else
            DuelMenu.getInstance().setShowTurn(game.getFirstPlayer());
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
