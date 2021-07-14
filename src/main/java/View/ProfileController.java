package View;

import Model.Players;
import Model.Response;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.io.IOException;
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

    public void changePassword(ActionEvent event) throws IOException {
        swithToChangePasswordScene(event);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
    }

    private void swithToChangePasswordScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("changePassword.ProfileController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void toProfile(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ProfileController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void setNewPassword(ActionEvent event) {
        String currentPassword = currentPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        if (!player.getPassword().equals(currentPassword)) responseLabel.setText(Response.wrongUsernameOrPassword);
        else {
            player.changePassword(newPassword);
            responseLabel.setText(Response.changePasswordSuccessfully);
        }
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

    private Players player;
    private Parent root;
    private Scene scene;
    @FXML
    private Button backButton;
    @FXML
    private Button submitButton;
    @FXML
    private TextField currentPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private Label responseLabel;

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

    public void setNickName(ActionEvent event) {
        String nickName = aliasTextField.getText();
        player.setNickname(nickName);
    }
}
