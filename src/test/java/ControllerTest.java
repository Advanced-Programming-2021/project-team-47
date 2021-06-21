import Controller.*;
import Model.*;
import View.DuelMenu;
import View.State;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerTest {
    @BeforeAll
    public static void newDeck() {
        Players players = new Players("ali", "a", "12345");
        new Deck("testDeck", players);
    }

    @BeforeAll
    public static void newCards() {
        CardTypes cardTypes = CardTypes.MONSTER_CARD;
        State state = State.DH;
        new Cards("testCard", 4, "Monster", 200, 400, "for test", 2000, cardTypes, "", state);
    }

    @Test
    public void existDeck() {
        boolean isExist = DeckProgramController.getInstance().checkDeckNameExist("Hamid");
        Assertions.assertTrue(isExist);
    }

    @Test
    public void existCard() {
        boolean isExist = DeckProgramController.getInstance().checkCardNameExist("test");
        Assertions.assertFalse(isExist);
    }

    @Test
    public void checkEnoughMoney() {
        Players player = Players.getPlayerByUsername("ali");
        player.setMoney(200);
        Assertions.assertTrue(ShopProgramController.getInstance().checkEnoughMoney(player, "testCard"));
    }

    @Test
    public void sortCard() {
        TreeMap<String, Integer> sortedCard = new TreeMap<>();
        CardTypes cardTypes = CardTypes.MONSTER_CARD;
        State state = State.DH;
        Cards a = new Cards("a", 4, "Monster", 200, 400, "for test", 2000, cardTypes, "", state);
        Cards b = new Cards("b", 4, "Monster", 200, 400, "for test", 2000, cardTypes, "", state);
        sortedCard.put(a.getCardName(), a.getPrice());
        sortedCard.put(b.getCardName(), b.getPrice());
        Assertions.assertEquals(sortedCard, ShopProgramController.getInstance().sortCard());
    }

    @Test
    public void checkUsernameExist() {
        Assertions.assertTrue(LoginProgramController.getInstance().checkUsernameExist("ali"));
    }

    @Test
    public void checkNicknameExist() {
        Assertions.assertTrue(LoginProgramController.getInstance().checkNicknameExist("a"));
    }

    @Test
    public void checkInvalidPassword() {
        Players player = Players.getPlayerByUsername("ali");
        Assertions.assertFalse(LoginProgramController.getInstance().checkInvalidPassword(player, "12"));
    }

    @Test
    public void checkSamePassword() {
        Players player = Players.getPlayerByUsername("ali");
        Assertions.assertTrue(LoginProgramController.getInstance().checkSamePassword(player, "12345"));
    }

    @Test
    public void signUpUser() {
        ArrayList<String> user = LoginProgramController.getInstance().signUpUser(Pattern.compile("^user create (--password (?:[\\s\\S]+)|--nickname (?:[\\s\\S]+)|--username (?:[\\s\\S]+)) (--password (?:[\\s\\S]+)|--nickname (?:[\\s\\S]+)|--username (?:[\\s\\S]+)) (--password (?:[\\s\\S]+)|--nickname (?:[\\s\\S]+)|--username (?:[\\s\\S]+))$").matcher("user create --username hamidreza --nickname hamid --password 12345"));
        Assertions.assertEquals(user.get(0), "12345");
        Assertions.assertEquals(user.get(1), "hamidreza");
        Assertions.assertEquals(user.get(2), "hamid");
    }

    @Test
    public void loginUser() {
        ArrayList<String> user = LoginProgramController.getInstance().loginUser(Pattern.compile("^user login (--password (?:[\\s\\S]+)|--username (?:[\\s\\S]+)) (--password (?:[\\s\\S]+)|--username (?:[\\s\\S]+))$").matcher("user login --username hamidreza --password 12345"));
        Assertions.assertEquals(user.get(0), "12345");
        Assertions.assertEquals(user.get(1), "hamidreza");
    }

    @Test
    public void changePassword() {
        ArrayList<String> user = LoginProgramController.getInstance().loginUser(Pattern.compile("^profile change (--new (?:[\\s\\S]+)|--password|--current (?:[\\s\\S]+)) (--new (?:[\\s\\S]+)|--password|--current (?:[\\s\\S]+)) (--new (?:[\\s\\S]+)|--password|--current (?:[\\s\\S]+))$").matcher("profile change --password --current 12345 --new 1234"));
        Assertions.assertEquals(user.get(0), "1234");
        Assertions.assertEquals(user.get(1), "12345");
    }

    @Test
    public void isAnyCardSelected() {
        Assertions.assertNull(DuelMenu.getInstance().getCardZoneSelected());
        Assertions.assertFalse(GameProgramController.getInstance().isAnyCardSelected());
    }

    @Test
    public void changePhase() {
        Phase phase = Phase.STANDBY_PHASE;
        GameProgramController.getInstance().changePhase(Phase.BATTLE_PHASE);
        Assertions.assertNotEquals(phase, DuelMenu.getInstance().getPhase());
    }

    @Test
    public void isMonsterCardZoneFull() {
        Assertions.assertFalse(GameProgramController.getInstance().isMonsterCardZoneFull("hamidreza"));
    }

    @Test
    public void isSpellZoneFull() {
        Assertions.assertFalse(GameProgramController.getInstance().isSpellZoneFull("hamidreza"));
    }

    @Test
    public void scoreboardShow() {
        ArrayList<String> players = new ArrayList<>();
        players.add("ali");
        players.add("hamidreza");
        Assertions.assertEquals(GameProgramController.getInstance().scoreboardShow(), players);
    }

    @Test
    public void checkWrongPosition() {
        Assertions.assertFalse(GameProgramController.getInstance().checkWrongPosition(6));
    }

    @Test
    public void checkCardExistInThisPosition() {
        Assertions.assertFalse(GameProgramController.getInstance().checkCardExistInThisPosition("ali", 5));
    }

    @Test
    public void isAnyCard() {
        Assertions.assertFalse(GameProgramController.getInstance().isAnyCard("ali", 2));
    }

    @Test
    public void isTwoMonsterCard() {
        Assertions.assertFalse(GameProgramController.getInstance().isTwoMonsterCard("hamidreza"));
    }

    @Test
    public void userExist() {
        Assertions.assertFalse(GameProgramController.getInstance().userExist("masih"));
    }

    @Test
    public void userDeckIsActive() {
        Assertions.assertFalse(GameProgramController.getInstance().userDeckIsActive("ali"));
    }

    @Test
    public void addCardByType() {
        GameProgramController.getInstance().addCardByType("TestCardForTest", 50, "Trap", 100, 250, "Nothing", 2000, CardTypes.TRAP_CARD, "test", State.NULL);
        Assertions.assertNotNull(Cards.getCardByName("TestCardForTest"));
    }

    @Test
    public void normalSet() {
        Assertions.assertEquals(Players.getPlayerByUsername("ali").getCardsInHand(0), "DH");
    }

    @Test
    public void flipSummon() {
        GameProgramController.getInstance().flipSummon("ali", 2);
        Assertions.assertEquals(Players.getPlayerByUsername("ali").getCardsInHand(2), "OO");
    }

    @Test
    public void directAttack() {
        GameProgramController.getInstance().directAttack("ali", "testCard");
        Assertions.assertEquals(Players.getPlayerByUsername("ali").getLifePoint(), -200);
    }

    @Test
    public void activateEffect() {
        GameProgramController.getInstance().activateEffect("ali", Cards.getCardByName("testCard"));
        Assertions.assertEquals(Players.getPlayerByUsername("ali").getCardsInHand(1), "O");
    }

    @Test
    public void setSpell() {
        GameProgramController.getInstance().setSpell("ali");
        Assertions.assertEquals(Players.getPlayerByUsername("ali").getCardsInHand(3), "H");
    }

    @Test
    public void rewards() {
        GameProgramController.getInstance().rewards(Players.getPlayerByUsername("ali"), Players.getPlayerByUsername("hamidreza"), 3);
        Assertions.assertEquals(2400, Players.getPlayerByUsername("ali").getMoney());
        Assertions.assertEquals(3000, Players.getPlayerByUsername("ali").getScore());
        Assertions.assertEquals(300, Players.getPlayerByUsername("hamidreza").getMoney());
    }

    @Test
    public void setTrap() {
        GameProgramController.getInstance().setTrap("ali");
        Assertions.assertEquals(Players.getPlayerByUsername("ali").getCardsInHand(4), "H");
    }

    @Test
    public void swapTurn() {
        Assertions.assertNull(DuelMenu.getInstance().getShowTurn());
        Assertions.assertNull(DuelMenu.getInstance().getShowOpponent());
        DuelMenu.getInstance().setShowTurn(Players.getPlayerByUsername("ali"));
        DuelMenu.getInstance().setShowOpponent(Players.getPlayerByUsername("hamidreza"));
        GameProgramController.getInstance().swapTurn();
        Assertions.assertEquals(DuelMenu.getInstance().getShowTurn(), Players.getPlayerByUsername("hamidreza"));
        Assertions.assertEquals(DuelMenu.getInstance().getShowOpponent(), Players.getPlayerByUsername("ali"));

    }

    @Test
    public void setPosition() {
        DuelMenu.getInstance().setCardZoneSelected(Cards.getCardByName("testCard"));
        GameProgramController.getInstance().setPosition("ali", "attack");
        Assertions.assertEquals("OO", Players.getPlayerByUsername("ali").getMonsterCardZone(0).getState());
    }

    @Test
    public void canSetPosition() {
        Assertions.assertTrue(GameProgramController.getInstance().canSetPosition("ali", "attack"));
    }

    @Test
    public void ritualSummon() {
        GameProgramController.getInstance().ritualSummon("ali", "attack");
        Assertions.assertEquals("attack", Players.getPlayerByUsername("ali").getSpellCardZoneByCoordinate(6).getState());
    }
    @Test
    public void isDeckFull(){
        Assertions.assertFalse(DeckProgramController.getInstance().isDeckFull("Hamid","testCard"));
    }
    @Test
    public void deckAddCard(){
        ArrayList<String> deck = DeckProgramController.getInstance().deckAddCard(Pattern.compile(Regex.DECK_ADD_CARD.label.pattern()).matcher("deck add-card --card masih --deck Hamid"));
        Assertions.assertEquals(deck.get(0), "masih");
        Assertions.assertEquals(deck.get(1), "Hamid");
    }
    @Test
    public void deckRemoveCard(){
        ArrayList<String> deck =DeckProgramController.getInstance().deckRemoveCard(Pattern.compile(Regex.DECK_REMOVE_CARD.label.pattern()).matcher("deck rm-card --card masih --deck Hamid"));
        Assertions.assertEquals(deck.get(0), "masih");
        Assertions.assertEquals(deck.get(1), "Hamid");
    }
    @Test
    public void deckShow(){
        ArrayList<String> deck = DeckProgramController.getInstance().deckShow(Pattern.compile(Regex.SHOW_DECK.label.pattern()).matcher("deck show --deck-name Hamid"));
        Assertions.assertEquals(deck.get(0), "Hamid");
    }
}
