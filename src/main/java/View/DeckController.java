package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class DeckController extends Application {
    public static DeckController deckController;
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

    public static DeckController getInstance() {
        if (deckController == null) deckController = new DeckController();
        return deckController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        DeckController.stage = stage;
        stage.resizableProperty().setValue(false);
        stage.setTitle("Yu-Gi-Oh");
        URL welcomeUrl = getClass().getResource("/fxml/DeckMenu.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/welcomeStyle.css").toExternalForm());
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
