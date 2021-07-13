package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;

public class MenuController extends Application {
    public Label showCurrentMenu;
    private static Stage stage;
    public static MenuController menuController;

    public static MenuController getInstance() {
        if (menuController == null) menuController = new MenuController();
        return menuController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        MenuController.stage = stage;
        stage.resizableProperty().setValue(false);
        stage.setTitle("Yu-Gi-Oh");
        URL welcomeUrl = getClass().getResource("/fxml/MenuController.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/welcomeStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void importOrExportMenu() throws Exception {
        IOEController.getInstance().start(stage);
    }

    public void loginMenu() throws Exception {
        LoginController.getInstance().start(stage);
    }

    public void profileMenu() throws Exception {
        ProfileController.getInstance().start(stage);
    }

    public void scoreboardMenu() throws Exception {
        ScoreBoardController.getInstance().start(stage);
    }

    public void shopMenu() throws Exception {
        ShopController.getInstance().start(stage);
    }

    public void exit() {
        System.exit(0);
    }

}
