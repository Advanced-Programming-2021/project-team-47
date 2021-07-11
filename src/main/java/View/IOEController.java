package View;

import Controller.GameProgramController;
import Model.Cards;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class IOEController extends Application {
    public static IOEController ioeController;
    public static IOEController getInstance() {
        if (ioeController == null) ioeController = new IOEController();
        return ioeController;
    }
    public static FileChooser fileChooser = new FileChooser();
    public GridPane gridPane;
    public Rectangle rect;
    public static Stage staticStage;

    @Override
    public void start(Stage stage) throws Exception {
        URL IOEController = getClass().getResource("/fxml/ImportOrExportMenu.fxml");
        Parent root = FXMLLoader.load(IOEController);
        Scene scene = new Scene(root);
        root.setId("IOEController");
        scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        staticStage = stage;
        stage.show();
    }

    public void importMethod(MouseEvent mouseEvent) {
        try {
            fileChooser.setTitle("Import File");
            File file = fileChooser.showOpenDialog(staticStage);
            rect.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/images/Cards/" + file.getName().replaceAll(" ", "").replaceAll("json", "") + "jpg"))));
            GameProgramController.getInstance().importCards("import card " + file.getName());
        } catch (FileNotFoundException ignored) {
        }
    }

    public void exportMethod(MouseEvent mouseEvent) {
        int i = 0, j = 0;
        for (Cards card : Cards.allCards) {
            Rectangle rectangle = new Rectangle();
            try {
                rectangle.setWidth(20);
                rectangle.setHeight(25);
                rectangle.setFill(new ImagePattern(new Image(new FileInputStream("src/main/resources/images/Cards/" + card.getCardName() + ".jpg"))));
            } catch (FileNotFoundException ignored) {
            }
            rectangle.setId(card.getCardName());
            rectangle.setOnMouseClicked(new EventHandler<>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    GameProgramController.getInstance().exportCards("export card " + rectangle.getId());
                }
            });
            gridPane.add(rectangle, i, j);
            ++i;
            ++j;
        }
    }
}
