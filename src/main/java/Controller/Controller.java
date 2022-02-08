package Controller;


import Model.*;
import Model.pieces.PieceType;
import Observer.Observer;
import View.BoardSquaresView;
import View.MainView;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class Controller {

    Board board;
    MainView mainview;

    public Controller(Board board, Observer mainview){
        this.board = board;
        this.mainview = (MainView) mainview;
        this.mainview.setUpMainView(this.board.getBoardSquares(), this.board.getWhitePieces(), this.board.getBlackPieces());

        addOnClick(this.mainview.getBoardSquaresView());
    }

    private void addOnClick(BoardSquaresView boardSquaresView){
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++){
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
        if (board.getWhitesTurn() == isWhite) {
            PieceType pieceType = board.getBoardSquares().getSquare(file, rank).getPiece().getPieceType();
            for (Integer[] moves:Moves.chooseMove(file, rank, isWhite, board, pieceType)) {
                System.out.println("" + moves[0].toString() +" "+ moves[1].toString());
            }
            ArrayList<Integer[]> moves = Moves.chooseMove(file, rank, isWhite, board, pieceType);

            board.movePieces(file,rank,moves.get(0)[0],moves.get(0)[1]);
        }
    }

}
