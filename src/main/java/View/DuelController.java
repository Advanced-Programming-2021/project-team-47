package View;

import Controller.GameProgramController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;


public class DuelController extends Application {
    private static Stage stage;
    public Label label1;
    public Label label2;
    public Label phase;

    @Override
    public void start(Stage stage) throws Exception {
        URL duel = getClass().getResource("/fxml/DuelMenu.fxml");
        DuelController.stage = stage;
        Parent root = FXMLLoader.load(duel);
        Scene scene = new Scene(root);
        stage.resizableProperty().setValue(false);
        root.setId("duelMenu");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        MenuController.getInstance().start(stage);
    }

    @FXML
    public void initialize() {
        phase.setText(GameProgramController.getInstance().getPhase().getLabel());
        label1.setText("LP: " + GameProgramController.getInstance().getFirstPlayer().getLifePoint() + "Username: " + GameProgramController.getInstance().getFirstPlayer().getUsername() + "Nickname: " + GameProgramController.getInstance().getFirstPlayer().getNickname());
        label2.setText("LP: " + GameProgramController.getInstance().getSecondPlayer().getLifePoint() + "Username: " + GameProgramController.getInstance().getSecondPlayer().getUsername() + "Nickname: " + GameProgramController.getInstance().getSecondPlayer().getNickname());
    }

    public void nextPhase(MouseEvent mouseEvent) {
        GameProgramController.getInstance().nextPhase();
    }
}
