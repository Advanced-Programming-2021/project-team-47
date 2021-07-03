package View.JavaFXController;

import Model.Cards;
import View.ImportOrExportMenu;
import View.LoginMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class ShopController extends Application {
    public GridPane gridPane;

    @Override
    public void start(Stage stage) throws Exception {
        URL shop = getClass().getResource("/fxml/ShopMenu");
        Parent root = FXMLLoader.load(shop);
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
                    LoginMenu.loginUsername.decreaseMoney(card.getPrice());
                    Label label = new Label();
                    label.setText("Card added successfully");
                    gridPane.add(label, finalI, 2);
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
}
