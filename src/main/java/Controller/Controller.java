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
    Piece piece = null;

    public Controller(Board board, Observer mainview){
        this.board = board;
        this.mainview = (MainView) mainview;
        this.mainview.setUpMainView(this.board.getBoardSquares(), this.board.getWhitePieces(), this.board.getBlackPieces());

        addOnClick(this.mainview.getBoardSquaresView());
    }

    private void addOnClick(BoardSquaresView boardSquaresView){
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++){
                int finalFile = file;
                int finalRank = rank;
                ImageView imageView = boardSquaresView.getSquareView(file,rank).getImageview();
                addOnClickPieces(finalFile,finalRank,imageView);
                Circle circle = boardSquaresView.getSquareView(file,rank).getCircle();
                addOnClickCircles(finalFile,finalRank,circle);

            }
        }
    }
    
    public void addOnClickPieces(Integer file, Integer rank, ImageView imageView){
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            pieceClicked(file, rank);
                                        }
                                    }
        );
    }

    public void addOnClickCircles(Integer file, Integer rank, Circle circle){
        circle.setPickOnBounds(true);
        circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            circleClicked(file, rank );
                                        }
                                    }
        );
    }


    public void pieceClicked(Integer file, Integer rank){
        if (piece == board.getBoardSquares().getSquare(file, rank).getPiece()) {
            mainview.removeCircles();
            this.piece = null;
        }
        else{
            if(!isNull(piece)){
                mainview.removeCircles();
            }
            this.piece = board.getBoardSquares().getSquare(file, rank).getPiece();
            Boolean isWhite = this.piece.getIsWhite();
            if (board.getWhitesTurn() == isWhite) {
                PieceType pieceType = board.getBoardSquares().getSquare(file, rank).getPiece().getPieceType();
                for (Integer[] moves : Moves.chooseMove(file, rank, isWhite, board, pieceType)) {
                    System.out.println("" + moves[0].toString() + " " + moves[1].toString());
                }
                ArrayList<Integer[]> moves = Moves.chooseMove(file, rank, isWhite, board, pieceType);

                mainview.addMoveOptions(Moves.chooseMove(file, rank, isWhite, board, pieceType));
                //
            }
        }
    }

    public void circleClicked(Integer file, Integer rank){
        System.out.println(file.toString() + " "  + rank.toString());
        board.movePieces(piece.getFile(),piece.getRank(),file,rank);
        this.piece = null;
    }


}
