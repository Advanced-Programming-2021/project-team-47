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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ProfileController extends Application {
    private Players player;
    private Parent root;
    private Scene scene;
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

    public void changePassword(ActionEvent event) throws IOException {
        swithToChangePasswordScene(event);
    }

    private void swithToChangePasswordScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("changePassword.ProfileController.fxml"));
        stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    public void setNewPassword(ActionEvent event){
        String currentPassword = currentPasswordTextField.getText();
        String newPassword = newPasswordTextField.getText();
        if (!player.getPassword().equals(currentPassword)) responseLabel.setText(Response.wrongCurrentPassword);
        else {
            player.changePassword(newPassword);
            responseLabel.setText(Response.changePasswordSuccessfully);
        }
    }
    public void toProfile(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ProfileController.fxml"));
        stage =(Stage) ((Node) event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
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
        String username = player.getUsername();
        usernameLabel.setText(username);
        String alias = player.getNickname();
        aliasTextField.setText(alias);
        Image playerImage = player.getProfileImage();
        profileImageView.setImage(playerImage);
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
    public void setNickName(ActionEvent event){
        String nickName = aliasTextField.getText();
        player.setNickName(nickName);
    }

//    public void setUsernameLabel(Players player) {
//        String username = player.getUsername();
//        usernameLabel.setText(username);
//    }

}
