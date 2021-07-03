package View.JavaFXController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ShopController extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL shop = getClass().getResource("/fxml/ShopMenu");
        Parent root = FXMLLoader.load(shop);
        Scene scene = new Scene(root);
        root.setId("shop");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
