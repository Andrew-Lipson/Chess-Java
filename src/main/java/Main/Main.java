package Main;

import Controller.Controller;
import Model.Game;
import Model.Fen;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

import static java.util.Objects.isNull;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        Controller controller = new Controller(); // Start up Controller
        MainView mainView = new MainView(stage, controller);// Start up View
        Game board = startGame(controller, "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq e3 0 1"); // Start up Model
        controller.startApplication(board,mainView);
    }

    public static void main(String[] args) {
        launch();
    }

    private Game startGame(Controller controller, String fen){
        if(isNull(fen)){
            return new Game(controller);
        }
        else{
            return Fen.convertFenToBoard(fen, controller);
        }
    }
}