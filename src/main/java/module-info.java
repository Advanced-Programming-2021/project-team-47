module AP.Project {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.fasterxml.jackson.dataformat.csv;
    requires com.fasterxml.jackson.databind;
    requires javafx.media;
    exports View;
    opens View to javafx.fxml;
}