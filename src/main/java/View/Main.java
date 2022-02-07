package View;

import Controller.Controller;
import Model.Board;
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

        Board board = new Board();

        Group root = new Group();
        MainView mainView = new MainView(board, root);
        Scene scene = new Scene(root, Color.BROWN);
        Image icon = new Image("chess-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Window");
        stage.setWidth(680);
        stage.setHeight(700);
        stage.setResizable(true);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}