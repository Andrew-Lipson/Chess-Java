package Code;

import Code.pieces.*;
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
        Boolean white = true;

        Piece pawn1W = new Pawn(true);
        board.addPiece(pawn1W,6,0);
        Piece pawn2W = new Pawn(true);
        board.addPiece(pawn2W,6,1);
        Piece pawn3W = new Pawn(true);
        board.addPiece(pawn3W,6,2);
        Piece pawn4W = new Pawn(true);
        board.addPiece(pawn4W,6,3);
        Piece pawn5W = new Pawn(true);
        board.addPiece(pawn5W,6,4);
        Piece pawn6W = new Pawn(true);
        board.addPiece(pawn6W,6,5);
        Piece pawn7W = new Pawn(true);
        board.addPiece(pawn7W,6,6);
        Piece pawn8W = new Pawn(true);
        board.addPiece(pawn8W,6,7);

        Piece pawn1B = new Pawn(false);
        board.addPiece(pawn1B,1,0);
        Piece pawn2B = new Pawn(false);
        board.addPiece(pawn2B,1,1);
        Piece pawn3B = new Pawn(false);
        board.addPiece(pawn3B,1,2);
        Piece pawn4B = new Pawn(false);
        board.addPiece(pawn4B,1,3);
        Piece pawn5B = new Pawn(false);
        board.addPiece(pawn5B,1,4);
        Piece pawn6B = new Pawn(false);
        board.addPiece(pawn6B,1,5);
        Piece pawn7B = new Pawn(false);
        board.addPiece(pawn7B,1,6);
        Piece pawn8B = new Pawn(false);
        board.addPiece(pawn8B,1,7);

        Piece Rook1B = new Rook(false);
        board.addPiece(Rook1B,0,0);
        Piece knight1B = new Knight(false);
        board.addPiece(knight1B,0,1);
        Piece bishop1B = new Bishop(false);
        board.addPiece(bishop1B,0,2);
        Piece queenB = new Queen(false);
        board.addPiece(queenB,0,3);
        Piece kingB = new King(false);
        board.addPiece(kingB,0,4);
        Piece bishop2B = new Bishop(false);
        board.addPiece(bishop2B,0,5);
        Piece knight2B = new Knight(false);
        board.addPiece(knight2B,0,6);
        Piece Rook2B = new Rook(false);
        board.addPiece(Rook2B,0,7);

        Piece Rook1W = new Rook(true);
        board.addPiece(Rook1W,7,0);
        Piece knight1W = new Knight(true);
        board.addPiece(knight1W,7,1);
        Piece bishop1W = new Bishop(true);
        board.addPiece(bishop1W,7,2);
        Piece queenW = new Queen(true);
        board.addPiece(queenW,7,3);
        Piece kingW = new King(true);
        board.addPiece(kingW,7,4);
        Piece bishop2W = new Bishop(true);
        board.addPiece(bishop2W,7,5);
        Piece knight2W = new Knight(true);
        board.addPiece(knight2W,7,6);
        Piece Rook2W = new Rook(true);
        board.addPiece(Rook2W,7,7);


        Group root = board.getRoot();
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