package Controller;

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
