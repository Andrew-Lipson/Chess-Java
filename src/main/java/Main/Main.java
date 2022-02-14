package Main;

import Controller.Controller;
import Model.Board;
import Observer.Observer;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {



        Observer ObserverMainView = new MainView(); // Start up View


        Board board = new Board(ObserverMainView); // Start up Model

        MainView mainView = (MainView) ObserverMainView;

        Controller controller = new Controller(board, mainView); // Start up Controller
        controller.showBoard(stage);
    }

    public static void main(String[] args) {
        launch();
    }


}