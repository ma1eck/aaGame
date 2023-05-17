module aa {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports view;
    exports Model;
    exports Controller;
    opens view to javafx.fxml;
    opens Model to com.google.gson;
}