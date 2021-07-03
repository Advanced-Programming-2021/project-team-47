package View.JavaFXController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;

public class IOEController extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import File");
        fileChooser.showOpenDialog(stage);
        URL IOEController = getClass().getResource("/fxml/ImportOrExportMenu.fxml");
        Parent root = FXMLLoader.load(IOEController);
        Scene scene = new Scene(root);
        root.setId("IOEController");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
