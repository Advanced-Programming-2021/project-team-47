package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController extends Application {
    public static LoginController loginController;
    private static Stage stage;
    public static LoginController getInstance() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }
    @Override
    public void start(Stage stage) throws Exception {
        LoginController.stage=stage;
        stage.resizableProperty().setValue(false);
        stage.setTitle("Yu-Gi-Oh");
        URL welcomeUrl = getClass().getResource("/fxml/MainMenu.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/welcomeStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
