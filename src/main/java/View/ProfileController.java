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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ProfileController extends Application {
    private static Stage stage;
    public static File mediaFile = new File("src/main/resources/audio files/button hit sound effect.mp3");
    public static Media media;

    static {
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer(media);
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
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
    }

    public void changePhoto(ActionEvent event) {
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
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
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
        MenuController.getInstance().start(stage);
    }
}
