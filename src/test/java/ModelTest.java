import Model.*;
import View.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ModelTest {
    @BeforeAll
    public static void newSideDeck() {
        Players players = new Players("abol", "abol", "1234");
        new SideDeck("abol", players);
    }

    @BeforeAll
    public static void newMainDeck() {
        Players players = new Players("abol", "abol", "1234");
        new MainDeck("abol", players);
    }

    @BeforeAll
    public static void newTrapCard() {
        new TrapCard("test", 2, "", 1000, 500, "", 157, CardTypes.TRAP_CARD, "", State.NULL);
    }

    @BeforeAll
    public static void newMonsterCard() {
        new MonsterCard("test", 2, "", 1000, 500, "", 157, CardTypes.TRAP_CARD, "", State.NULL);
    }

    @BeforeAll
    public static void newSpellCard() {
        new SpellCard("test", 2, "", 1000, 500, "", 157, CardTypes.TRAP_CARD, "", State.NULL);
    }

    @Test
    public void isSideDeckValid() {
        Assertions.assertFalse(SideDeck.isDeckValid((SideDeck) SideDeck.getDeckByName("abol")));
    }

    @Test
    public void isActiveDeckValid() {
        Assertions.assertFalse(Players.isActiveDeckValid(Players.getPlayerByUsername("abol")));
    }

    @Test
    public void checkSeterAndGeterOfPlayer() {
        Players players = new Players("test", "test", "password");
        Assertions.assertNull(players.getPlayerCards());
        Assertions.assertNull(players.getActiveDeck());
        Assertions.assertNull(players.getCardsInGraveyard());
        Assertions.assertNull(players.getSpellCardZone());
        players.increaseScore(2000);
        Assertions.assertEquals(players.getScore(), 2000);
    }

    @Test
    public void getNumberOfCards() {
        Assertions.assertEquals(0, MainDeck.getNumberOfCards(Deck.getDeckByName("test")));
    }

    @Test
    public void isMainDeckValid() {
        Assertions.assertFalse(MainDeck.isDeckValid((MainDeck) SideDeck.getDeckByName("abol")));
    }
}
