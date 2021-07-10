import Controller.LoginProgramController;
import Model.Players;
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
        LoginProgramController.setLoginUsername(Players.getPlayerByUsername("finalTest"));
        Assertions.assertEquals(LoginProgramController.getLoginUsername(), Players.getPlayerByUsername("finalTest"));
    }
}
