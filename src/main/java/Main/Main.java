package Main;

import Controller.Controller;
import Model.Board;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        Controller controller = new Controller(); // Start up Controller
        MainView mainView = new MainView(stage, controller);// Start up View
        Board board = new Board(controller); // Start up Model



        controller.setUpController(board,mainView);

        controller.showBoard();
    }

    public static void main(String[] args) {
        launch();
    }


}