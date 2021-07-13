package View;

import Controller.LoginProgramController;
import Model.Players;
import Model.Response;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;

public class LoginController extends Application {
    public static LoginController loginController;
    public TextField username = new TextField();
    public PasswordField password = new PasswordField();
    public TextField nickName = new TextField();
    private static Stage stage;

    public void showPassword() {
        String passwordText = password.getText();
    }

    public void showUsername() {
        String usernameText = username.getText();
    }

    public void showNickName() {
        String nickname = nickName.getText();
    }

    public static LoginController getInstance() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        LoginController.stage = stage;
        stage.resizableProperty().setValue(false);
        stage.setTitle("Yu-Gi-Oh");
        URL welcomeUrl = getClass().getResource("/fxml/LoginMenu.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/welcomeStyle.css").toExternalForm());
        stage.setScene(scene);
    }

    public void loginResponse() throws Exception {
        String password = LoginController.getInstance().password.getText();
        String username = LoginController.getInstance().username.getText();
        if (!LoginProgramController.getInstance().checkUsernameExist(username)) {
            Button btn = new Button();
            btn.setText("Error");
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text(Response.wrongUsernameOrPassword));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        } else if (LoginProgramController.getInstance().checkInvalidPassword(Players.getPlayerByUsername(username), password)) {
            Button btn = new Button();
            btn.setText("Error");
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text(Response.wrongUsernameOrPassword));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        } else {
            Button btn = new Button();
            btn.setText("Error");
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text(Response.userLoginSuccessfully));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
            LoginProgramController.setLoginUsername(Players.getPlayerByUsername(username));
            MenuController.getInstance().start(stage);
        }
    }

    public void signUpResponse() {
        String password = LoginController.getInstance().password.getText();
        String username = LoginController.getInstance().username.getText();
        String nickName = LoginController.getInstance().nickName.getText();
        if (LoginProgramController.getInstance().checkUsernameExist(username)) {
            Button btn = new Button();
            btn.setText("Error");
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text("user with username " + username + " already exists"));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        } else if (LoginProgramController.getInstance().checkNicknameExist(nickName)) {
            Button btn = new Button();
            btn.setText("Error");
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text("user with nickname " + nickName + " already exists"));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
        } else {
            Button btn = new Button();
            btn.setText("Error");
            btn.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            final Stage dialog = new Stage();
                            dialog.initModality(Modality.APPLICATION_MODAL);
                            dialog.initOwner(stage);
                            VBox dialogVbox = new VBox(20);
                            dialogVbox.getChildren().add(new Text(Response.userCreateSuccessfully));
                            Scene dialogScene = new Scene(dialogVbox, 300, 200);
                            dialog.setScene(dialogScene);
                            dialog.show();
                        }
                    });
            new Players(username, nickName, password);
        }
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        MenuController.getInstance().start(stage);
    }
}
