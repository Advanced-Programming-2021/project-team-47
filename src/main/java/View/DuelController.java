package View;

import Controller.GameProgramController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;


public class DuelController extends Application {
    private static Stage stage;
    public Label label1;
    public Label label2;
    public GridPane gridPane;
    public Label phase;
    public static File mediaFileButton = new File("src/main/resources/audio files/button hit sound effect.mp3");
    public static Media mediaButton;
    private static DuelController duelController;

    public static DuelController getInstance() {
        if (duelController == null) duelController = new DuelController();
        return duelController;
    }

    static {
        try {
            mediaButton = new Media(mediaFileButton.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayerButton = new MediaPlayer(mediaButton);
    public static File mediaFile = new File("src/main/resources/audio files/Nothing else matters-duelMenu use.mp3");
    public static Media media;

    static {
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer(media);

    @Override
    public void start(Stage stage) throws Exception {
        URL duel = getClass().getResource("/fxml/DuelMenu.fxml");
        DuelController.stage = stage;
        Parent root = FXMLLoader.load(duel);
        Scene scene = new Scene(root);
        stage.resizableProperty().setValue(false);
        MenuController.getInstance().mediaPlayer.stop();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setAutoPlay(true);
        root.setId("duelMenu");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        MenuController.getInstance().start(stage);
    }

    @FXML
    public void initialize() {
        phase.setText(GameProgramController.getInstance().getPhase().getLabel());
        label1.setText("LP: " + GameProgramController.getInstance().getFirstPlayer().getLifePoint() + "Username: " + GameProgramController.getInstance().getFirstPlayer().getUsername() + "Nickname: " + GameProgramController.getInstance().getFirstPlayer().getNickname());
        label2.setText("LP: " + GameProgramController.getInstance().getSecondPlayer().getLifePoint() + "Username: " + GameProgramController.getInstance().getSecondPlayer().getUsername() + "Nickname: " + GameProgramController.getInstance().getSecondPlayer().getNickname());
        for (int i = 0; i < 9; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (i != 1 && i != 7 && i != 4) {
                    Rectangle rectangle = new Rectangle();
                    rectangle.setFill(Color.GRAY);
                    rectangle.setHeight(53);
                    rectangle.setWidth(40);
                    gridPane.add(rectangle, j, i);
                }
            }
        }
        for (int i = 0; i < 4; ++i) {
            Rectangle rectangle = new Rectangle();
            rectangle.setFill(Color.GRAY);
            rectangle.setHeight(30);
            rectangle.setWidth(20);
        }
    }

    public void nextPhase(MouseEvent mouseEvent) {
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
            }
        });
        GameProgramController.getInstance().nextPhase();
    }
}
