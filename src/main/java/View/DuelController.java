package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class DuelController extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL duel = getClass().getResource("/fxml/DuelMenu.fxml");
        Parent root = FXMLLoader.load(duel);
        Scene scene = new Scene(root);
        stage.resizableProperty().setValue(false);
        root.setId("duelMenu");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
