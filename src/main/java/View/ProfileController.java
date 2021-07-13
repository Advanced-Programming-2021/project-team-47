package View;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

public class ProfileController extends Application {
    private static Stage stage;
    @FXML
    private ImageView profileImageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private TextField aliasTextField;
    @FXML
    private Button passwordChangeButton;
    @FXML
    private Button profilePhotoChange;

    public void changePassword(ActionEvent event) {

    }

    public void changePhoto(ActionEvent event) {

    }

    public static ProfileController profileController;

    public static ProfileController getInstance() {
        if (profileController == null) profileController = new ProfileController();
        return profileController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL profileController = getClass().getResource("/fxml/ProfileController.fxml");
        ProfileController.stage = stage;
        Parent root = FXMLLoader.load(profileController);
        Scene scene = new Scene(root);
        stage.resizableProperty().setValue(false);
        root.setId("profileController");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        MenuController.getInstance().start(stage);
    }
}
