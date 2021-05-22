import Controller.DeckProgramController;
import Model.CardTypes;
import Model.Cards;
import Model.Deck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ControllerTest {
    @BeforeAll
    public static void newDeck() {
        new Deck("testDeck", "Hamid");
    }

    @BeforeAll
    public static void newCards() {
        CardTypes cardTypes = CardTypes.MONSTER_CARD;
        new Cards("testCard", 4, "Monster", 200, 400, "for test", 2000, cardTypes, "");
    }

    @Test
    public void existDeck() {
        boolean isExist = DeckProgramController.getInstance().checkDeckNameExist("Hamid");
        Assertions.assertTrue(isExist);
    }

    @Test
    public void existCard() {
        boolean isExist = DeckProgramController.getInstance().checkDeckNameExist("test");
        Assertions.assertFalse(isExist);
    }
}
