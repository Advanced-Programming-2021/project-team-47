package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

public class ShopPanelController extends Application {
    public static ShopPanelController shopPanelController;
    private static Stage stage;
    public Label label;

    public static ShopPanelController getInstance() {
        if (shopPanelController == null)
            return shopPanelController = new ShopPanelController();
        return shopPanelController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL shopPanel = getClass().getResource("/fxml/ShopPanel.fxml");
        Parent root = FXMLLoader.load(shopPanel);
        Scene scene = new Scene(root);
        root.setId("shopPanel");
        ShopPanelController.stage = stage;
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        ShopController.getInstance().start(stage);
    }

    public void submit(MouseEvent mouseEvent) {
        label.setText("Submitted");
    }
}
