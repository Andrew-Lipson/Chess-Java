package Controller;

import Model.Board;
import Observer.Observer;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

//        Observer observer = new ViewObserver(); // Start up the observer
        Observer mainView = new MainView(stage); // Start up View

        Board board = new Board(mainView); // Start up Model

        Controller controller = new Controller(board, mainView); // Start up Controller

    }

    public static void main(String[] args) {
        launch();
    }


}