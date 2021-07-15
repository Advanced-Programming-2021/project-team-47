package View;

import Controller.LoginProgramController;
import Model.Cards;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;

public class ShopController extends Application {
    private static Stage stage;
    public GridPane gridPane;
    public Button back;
    public static ShopController shopController;
    public static File mediaFile = new File("src/main/resources/audio files/button hit sound effect.mp3");
    public static Media media;

    static {
        try {
            media = new Media(mediaFile.toURI().toURL().toString());
        } catch (MalformedURLException ignored) {
        }
    }

    public static MediaPlayer mediaPlayer = new MediaPlayer(media);

    public static ShopController getInstance() {
        if (shopController == null) shopController = new ShopController();
        return shopController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        ShopController.stage = stage;
        URL shop = getClass().getResource("/fxml/ShopMenu.fxml");
        Parent root = FXMLLoader.load(shop);
        stage.resizableProperty().setValue(false);
        Scene scene = new Scene(root);
        root.setId("shop");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    public void initialize() {
        showCardsList();
    }

    public void showCardsList() {
        int i = 0;
        for (Cards card : Cards.allCards) {
            Rectangle rectangle = new Rectangle();
            rectangle.setWidth(20);
            rectangle.setHeight(30);
            try {
                rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/images/Cards/" + card.getCardName() + ".jpg"))));
            } catch (FileNotFoundException ignored) {
            }
            rectangle.setId(card.getCardName());
            int finalI = i;
            rectangle.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (LoginProgramController.loginUsername.getMoney() > card.getPrice()) {
                        LoginProgramController.loginUsername.decreaseMoney(card.getPrice());
                        Label label = new Label();
                        label.setText("Card added successfully");
                        gridPane.add(label, finalI, 2);
                    } else {
                        Label label = new Label();
                        label.setText("Your money is lower than card price");
                        gridPane.add(label, finalI, 2);
                    }

                }
            });
            Label label = new Label();
            Label label1 = new Label();
            label.setText(card.getCardName());
            label1.setText(String.valueOf(card.getPrice()));
            gridPane.add(rectangle, i, 0);
            gridPane.add(label, i, 1);
            gridPane.add(label1, i, 2);
            ++i;
        }
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
