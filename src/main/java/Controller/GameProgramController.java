package Controller;

import Model.Players;
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

    public boolean isAnyCardSelected() {

    }


    public boolean isSummonAllowed(String cardName) {

    }

    public boolean isAnyCard(int addressNumber) {

    }

    public boolean isTwoMonsterCard(int addressNumber) {

    }

    public void addCardByType(String cardName, int level, String type, int ATK, int DEF, String description, int price) {

    }

    public void normalSet(int addressNumber) {

    }

    public void flipSummon(int addressNumber) {

    }

    public void attackMonster(int addressNumber) {

    }

    public void directAttack(int addressNumber) {

    }

    public void activateEffect(int addressNumber) {

    }

    public void setSpell(int addressNumber) {

    }

    public void specialSummon(int addressNumber) {

    }

    public void setTrap(int addressNumber) {

    }

    public void setTrapOrSpellOpponentTurn(int addressNumber) {

    }

    public void swapTurn(int addressNumber) {

    }

    public void setPosition(int addressNumber) {

    }

    public void ritualSummon(int addressNumber) {

    }

}
