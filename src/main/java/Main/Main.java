package Main;

import Controller.Controller;
import Model.Game;
import Model.Utilities.Fen;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Controller controller = new Controller(); // Start up Controller
        MainView mainView = new MainView(stage, controller);// Start up View
        Game game = startGame(controller, "8/k2N4/p7/1pP5/4B3/8/3Q4/K7 w - b6 0 1"); // Start up Model
//        Game game = startGame(controller);
        controller.startApplication(game,mainView);
    }

    public static void main(String[] args) {
        launch();
    }

    private Game startGame(Controller controller) {
            return new Game(controller);
    }

    private Game startGame(Controller controller, String fen) {
            return Fen.convertFenToBoard(fen, controller);

    }
}