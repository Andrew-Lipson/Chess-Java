module Model {
    requires javafx.controls;
    requires javafx.fxml;


    opens Model to javafx.fxml;
    exports Model;
    exports Controller;
    opens Controller to javafx.fxml;
    exports View;
    opens View to javafx.fxml;

}