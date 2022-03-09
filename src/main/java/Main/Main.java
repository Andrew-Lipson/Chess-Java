package Main;

import Controller.Controller;
import Model.Game;
import Model.Utilities.Fen;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.isNull;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        Controller controller = new Controller(); // Start up Controller
        MainView mainView = new MainView(stage, controller);// Start up View
        Game game = startGame(controller, "k7/8/8/8/8/6q1/2p5/7K b - - 0 1"); // Start up Model
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