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
        Game board = startGame(controller, "r3kQ1r/ppp1pp1p/8/1b6/3B4/2N5/PPP2P1P/R3Kn1R b KQkq - 2 7"); // Start up Model
//        Game board = startGame(controller,null);
        controller.startApplication(board,mainView);
    }

    public static void main(String[] args) {
        launch();
    }

    private Game startGame(Controller controller, String fen) {
        if(isNull(fen)){
            return new Game(controller);
        }
        else{
            return Fen.convertFenToBoard(fen, controller);
        }
    }
}