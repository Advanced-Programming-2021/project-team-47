package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MenuController extends Application {
    public Label showCurrentMenu;
    public Label logout;
    private static Stage stage;
    public static MenuController menuController;
    public static File mediaFileButton = new File("src/main/resources/audio files/button hit sound effect.mp3");
    public static Media mediaButton;

    static {
        try {
            mediaButton = new Media(mediaFileButton.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayerButton = new MediaPlayer(mediaButton);
    public static File mediaFile = new File("src/main/resources/audio files/Further background sound.mp3");
    public static Media media;

    static {
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer(media);

    public static MenuController getInstance() {
        if (menuController == null) menuController = new MenuController();
        return menuController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        MenuController.stage = stage;
        stage.resizableProperty().setValue(false);
        stage.setTitle("Yu-Gi-Oh");
        URL welcomeUrl = getClass().getResource("/fxml/MenuController.fxml");
        Parent root = FXMLLoader.load(welcomeUrl);
        Scene scene = new Scene(root);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setAutoPlay(true);
        scene.getStylesheets().add(getClass().getResource("/css/welcomeStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void importOrExportMenu() throws Exception {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        IOEController.getInstance().start(stage);
    }
    public void duelMenu() throws Exception{
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        DuelController.getInstance().start(stage);
    }
    public void loginMenu() throws Exception {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        LoginController.getInstance().start(stage);
    }

    public void deckMenu() throws Exception {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        DeckController.getInstance().start(stage);
    }
    public void profileMenu() throws Exception {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        ProfileController.getInstance().start(stage);
    }

    public void scoreboardMenu() throws Exception {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        ScoreBoardController.getInstance().start(stage);
    }

    public void shopMenu() throws Exception {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        ShopController.getInstance().start(stage);
    }

    public void exit() {
        mediaPlayerButton.play();
        mediaPlayerButton.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayerButton.stop();
            }
        });
        System.exit(0);
    }

    public void logout() {
        logout.setText("Logged out successfully");
    }
}
