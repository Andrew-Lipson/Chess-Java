module Model {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens Main to javafx.graphics;
    
    exports Model;
    exports Model.Pieces;
    exports Controller;
    exports View;
    exports Model.Utilities;
    exports View.Menu;
    exports View.Modals;
    exports View.Board;
    exports Controller.Engine;
}