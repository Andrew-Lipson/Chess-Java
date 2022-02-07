package Controller;

import Controller.Controller;
import Model.Board;
import View.MainView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

        Board board = new Board(); // Start up Model

        MainView mainView = new MainView(board, stage); // Start up View

        Controller controller = new Controller(board, mainView); // Start up Controller

    }

    public static void main(String[] args) {
        launch();
    }


}