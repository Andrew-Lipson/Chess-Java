package Main;

import Controller.Controller;
import Model.Board;
import Model.FEN;
import Observer.Observer;
import View.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {



        Observer ObserverMainView = new MainView(stage); // Start up View


        Board board = new Board(ObserverMainView); // Start up Model

        MainView mainView = (MainView) ObserverMainView;

        Controller controller = new Controller(board, mainView); // Start up Controller

    }

    public static void main(String[] args) {
        launch();
    }


}