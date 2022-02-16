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
        Board board = new Board(controller, "r3k1nr/pppq2p1/2n5/4p1B1/1bB3p1/4P3/PP1N1PP1/R2QNRK1 b kq - 2 2"); // Start up Model

        controller.startApplication(board,mainView);
    }

    public static void main(String[] args) {
        launch();
    }
}