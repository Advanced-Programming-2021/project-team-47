package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ProfileController extends Application {
    public static ProfileController profileController;
    public static ProfileController getInstance() {
        if (profileController == null) profileController = new ProfileController();
        return profileController;
    }
    @Override
    public void start(Stage stage) throws Exception {
        URL profileController = getClass().getResource("/fxml/ProfileController.fxml");
        Parent root = FXMLLoader.load(profileController);
        Scene scene = new Scene(root);
        root.setId("profileController");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
