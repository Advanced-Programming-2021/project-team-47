package View;

import javafx.stage.Stage;

public class Main {
    private static Stage stage;
    public static void main(String[] args) throws Exception {
        MenuController.getInstance().start(stage);
    }
}
