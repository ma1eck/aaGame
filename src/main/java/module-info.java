module aa {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.desktop;


    exports view;
    exports Model;

    opens view to javafx.fxml, javafx.graphics;
    opens Model to com.google.gson;
    exports view.Login;
    opens view.Login to javafx.fxml;
    exports view.ProfileMenu.Avatar;
    opens view.ProfileMenu.Avatar to javafx.fxml;
    exports view.ProfileMenu;
    opens view.ProfileMenu to javafx.fxml;
}