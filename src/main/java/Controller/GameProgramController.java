package Controller;

import Model.Players;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


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
        HashMap<String, Integer> scoreSorting = new HashMap<>();
        for (int i = 0; i < Players.allPlayers.size(); ++i) {
            if (i < Players.allPlayers.size() - 1) {
                if (Players.allPlayers.get(i).getScore() > Players.allPlayers.get(i + 1).getScore()) {
                    scoreSorting.put(Players.allPlayers.get(i).getNickname(), Players.allPlayers.get(i).getScore());
                } else if (Players.allPlayers.get(i).getScore() == Players.allPlayers.get(i + 1).getScore()) {
                    if (Players.allPlayers.get(i).getUsername().compareTo(Players.allPlayers.get(i + 1).getUsername()) < 0) {
                        scoreSorting.put(Players.allPlayers.get(i).getNickname(), Players.allPlayers.get(i).getScore());
                    } else {
                        scoreSorting.put(Players.allPlayers.get(i + 1).getNickname(), Players.allPlayers.get(i + 1).getScore());
                    }
                } else {
                    scoreSorting.put(Players.allPlayers.get(i + 1).getNickname(), Players.allPlayers.get(i + 1).getScore());
                }
            } else {
                if (Players.allPlayers.get(i).getScore() > Players.allPlayers.get(0).getScore()) {
                    scoreSorting.put(Players.allPlayers.get(i).getNickname(), Players.allPlayers.get(i).getScore());
                } else if (Players.allPlayers.get(i).getScore() == Players.allPlayers.get(0).getScore()) {
                    if (Players.allPlayers.get(i).getUsername().compareTo(Players.allPlayers.get(0).getUsername()) < 0) {
                        scoreSorting.put(Players.allPlayers.get(i).getNickname(), Players.allPlayers.get(i).getScore());
                    } else {
                        scoreSorting.put(Players.allPlayers.get(0).getNickname(), Players.allPlayers.get(0).getScore());
                    }
                } else {
                    scoreSorting.put(Players.allPlayers.get(0).getNickname(), Players.allPlayers.get(0).getScore());
                }
            }

        }
        ArrayList<String> sortWithArrayList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : scoreSorting.entrySet()) {
            sortWithArrayList.add(entry.getKey());
        }
        return sortWithArrayList;
    }

    public boolean checkWrongPosition(int position) {

    }

    public boolean checkCardExistInThisPosition(int position) {

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
