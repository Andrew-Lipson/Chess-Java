module Code {
    requires javafx.controls;
    requires javafx.fxml;


    opens Code to javafx.fxml;
    exports Code;

}