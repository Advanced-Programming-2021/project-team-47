package View.JavaFXController;

import Controller.GameProgramController;
import Model.Players;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.invoke.LambdaConversionException;
import java.util.ArrayList;

public class ScoreBoardController extends Application {
    public GridPane gridPane;

    public void showScoreBoard() {
        int rank = 1;
        ArrayList<String> sortWithArrayList = GameProgramController.getInstance().scoreboardShow();
        StringBuilder scoreBoard = new StringBuilder();
        for (int j = 0; j < sortWithArrayList.size(); ++j) {
            scoreBoard.append(rank + "-" + sortWithArrayList.get(j) + ":" + " " + Players.getPlayerByNickName(sortWithArrayList.get(j)).getScore());
            ++rank;
        }
        Label label = new Label();
        label.setText(scoreBoard.toString());
        gridPane.add(label, 1, 2);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
