package Controller;


import Model.*;
import View.BoardSquaresView;
import View.MainView;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {

    Board board;
    MainView mainview;

    public Controller(Board board, MainView mainview){
        this.board = board;
        this.mainview = mainview;
        addOnClick(this.mainview.getBoardSquaresView());
    }

    private void addOnClick(BoardSquaresView boardSquaresView){
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++){
                ImageView imageView = boardSquaresView.getSquareView(file,rank).getImageview();
                imageView.setPickOnBounds(true);
                int finalFile = file;
                int finalRank = rank;
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                     @Override
                     public void handle(MouseEvent mouseEvent) {
                         pieceClicked(finalFile, finalRank);
                     }
                 }
                );
            }
        }
    }

    public void pieceClicked(Integer file, Integer rank){
        Boolean isWhite = board.getBoardSquares().getSquare(file, rank).getPiece().getIsWhite();
        board.getBoardSquares().getSquare(file, rank).removePiece();
        mainview.getBoardSquaresView().getSquareView(file,rank).removePiece();
        for (Integer[] moves:Moves.knight(file, rank, isWhite)) {
            System.out.println("" + moves[0].toString() +" "+ moves[1].toString());
        }


    }

}
