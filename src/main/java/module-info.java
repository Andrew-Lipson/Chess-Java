module Model {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens Main to javafx.graphics;
    
    exports Model;
    exports Model.pieces;
    exports Controller;
    exports View;
    exports Observer;
}