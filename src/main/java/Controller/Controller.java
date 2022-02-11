package Controller;


import Model.*;
import Model.pieces.Piece;
import Model.pieces.PieceType;
import Observer.Observer;
import View.BoardSquaresView;
import View.MainView;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Controller {

    Board board;
    MainView mainview;
    Piece clickedPiece = null;

    public Controller(Board board, Observer mainview){
        this.board = board;
        this.mainview = (MainView) mainview;
        this.mainview.setUpMainView(this.board.getWhitePieces(), this.board.getBlackPieces());

        addOnClick(this.mainview.getBoardSquaresView());
    }


    //add the onclick function to both pieces (ImageView) and potential moves (Circle)
    private void addOnClick(BoardSquaresView boardSquaresView){
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++){
                int finalFile = file;
                int finalRank = rank;


                ImageView imageView = boardSquaresView.getSquareView(file,rank).getImageview();
                imageView.setPickOnBounds(true);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            pieceClicked(finalFile, finalRank);
                        }
                    }
                );


                Circle circle = boardSquaresView.getSquareView(file,rank).getCircle();
                circle.setPickOnBounds(true);
                circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                         @Override
                         public void handle(MouseEvent mouseEvent) {
                             circleClicked(finalFile, finalRank );
                         }
                     }
                );

            }
        }
    }

    //
    public void pieceClicked(Integer file, Integer rank){
        // Check if the piece clicked was just clicked, and remove the possible moves from the board
        if (clickedPiece == board.getBoardSquares().getSquare(file, rank).getPiece()) {
            mainview.removeMoveOptionsCircles();
            this.clickedPiece = null;
        }
        else{
            //Check if another piece has been previously clicked and remove that piece's possible moves
            if(!isNull(clickedPiece)){
                mainview.removeMoveOptionsCircles();
            }
            //Add the Clicked piece's possible moves
            this.clickedPiece = board.getBoardSquares().getSquare(file, rank).getPiece();
            Boolean isWhite = this.clickedPiece.getIsWhite();
            if (board.getWhitesTurn() == isWhite) {
                PieceType pieceType = board.getBoardSquares().getSquare(file, rank).getPiece().getPieceType();
                ArrayList<Integer[]> moves = Moves.chooseMove(file, rank, isWhite, board, pieceType);

                mainview.addMoveOptionsCircles(moves);
            }
        }
    }

    //Move the previously clicked piece to the clicked circle position and remove the
    public void circleClicked(Integer file, Integer rank){
        board.movePieces(clickedPiece.getFile(), clickedPiece.getRank(),file,rank);
        this.clickedPiece = null;
    }


}
