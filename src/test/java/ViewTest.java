import Model.Players;
import View.LoginMenu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ViewTest {
    @Test
    public void signUp() {
        LoginMenu.getInstance().takeCommand("user create --username finalTest --nickname test --password 12345");
        Assertions.assertNotNull(Players.getPlayerByUsername("finalTest"));
    }

    @Test
    public void setAndGetLoginUser() {
        LoginMenu.getInstance().setLoginUsername(Players.getPlayerByUsername("finalTest"));
        Assertions.assertEquals(LoginMenu.getInstance().getLoginUsername(), Players.getPlayerByUsername("finalTest"));
    }
}
