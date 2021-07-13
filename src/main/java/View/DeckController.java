package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

public class DeckController extends Application {
    public static DeckController deckController;
    private static Stage stage;

    public static DeckController getInstance() {
        if (deckController == null) deckController = new DeckController();
        return deckController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        DeckController.stage = stage;
        stage.resizableProperty().setValue(false);
        stage.setTitle("Yu-Gi-Oh");
        URL welcomeUrl = getClass().getResource("/fxml/DeckMenu.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/welcomeStyle.css").toExternalForm());
        stage.setScene(scene);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        MenuController.getInstance().start(stage);
    }
}
