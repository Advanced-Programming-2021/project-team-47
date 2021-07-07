package View;

import javafx.application.Application;
import javafx.stage.Stage;

public class LoginController extends Application {
    public static LoginController loginController;
    public static LoginController getInstance() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }
    @Override
    public void start(Stage stage) throws Exception {

    }
}
