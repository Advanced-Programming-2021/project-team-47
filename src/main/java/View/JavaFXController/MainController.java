package View.JavaFXController;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainController extends Application {
    public static MainController mainController;
    public static MainController getInstance() {
        if (mainController == null) mainController = new MainController();
        return mainController;
    }
    @Override
    public void start(Stage stage) throws Exception {

    }
}
