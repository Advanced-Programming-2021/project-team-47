package View;

import Controller.GameProgramController;
import Controller.LoginProgramController;
import Model.Players;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;

public class ScoreBoardController extends Application {
    public GridPane gridPane;
    private static ScoreBoardController scoreBoardController;

    public static ScoreBoardController getInstance() {
        if (scoreBoardController == null)
            scoreBoardController = new ScoreBoardController();
        return scoreBoardController;
    }

    public void showScoreBoard() {
        int rank = 1;
        ArrayList<String> sortWithArrayList = GameProgramController.getInstance().scoreboardShow();
        StringBuilder scoreBoard = new StringBuilder();
        for (String s : sortWithArrayList) {
            if (LoginProgramController.loginUsername.equals(Players.getPlayerByNickName(s))) {
                scoreBoard.append("***");
            }
            scoreBoard.append(rank + "-" + s + ":" + " " + Players.getPlayerByNickName(s).getScore() + "\n");
            ++rank;
            if (rank == 20) {
                break;
            }
        }
        Label label = new Label();
        label.setText(scoreBoard.toString());
        gridPane.add(label, 1, 2);
    }

    @Override
    public void start(Stage stage) throws Exception {
        URL scoreBoard = getClass().getResource("/fxml/ScoreBoardMenu.fxml");
        Parent root = FXMLLoader.load(scoreBoard);
        stage.resizableProperty().setValue(false);
        Scene scene = new Scene(root);
        root.setId("scoreBoard");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize() {
        showScoreBoard();
    }
}
