module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.csv;

    opens View to javafx.fxml;
    exports View;
}