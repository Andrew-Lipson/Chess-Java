package Controller;



import Model.Board;
import Model.Position;
import Model.pieces.Piece;
import Model.pieces.PieceType;
import View.MainView;
import View.PositionView;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Controller {

    Board board;
    MainView mainview;
    Piece clickedPiece = null;

    public Controller(Board board, MainView mainview){
        this.board = board;
        this.mainview = mainview;

        addOnClick();
    }


    //add the onclick function to both pieces (ImageView) and potential moves (Circle)
    private void addOnClick(){
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++){
                int finalFile = file;
                int finalRank = rank;

                Position position = new Position(finalFile,finalRank);
                PositionView positionView = new PositionView(finalFile,finalRank);
                ImageView imageView = mainview.getSquareView(positionView).getImageview();
                imageView.setPickOnBounds(true);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            pieceClicked(position);
                        }
                    }
                );


                Circle circle = mainview.getSquareView(positionView).getCircle();
                circle.setPickOnBounds(true);
                circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                         @Override
                         public void handle(MouseEvent mouseEvent) {
                             circleClicked(position);
                         }
                     }
                );

            }
        }
    }

    public void showBoard(Stage stage){
        mainview.showBoard(stage);
    }

    //Once a piece is clicked and displays possible moves or removes the possibles moves
    private void pieceClicked(Position position){
        // Check if the piece clicked was just clicked, and remove the possible moves from the board
        if (clickedPiece == board.getPiece(position)) {
            mainview.removeMoveOptionsCircles();
            this.clickedPiece = null;
        }
        else{
            //Check if another piece has been previously clicked and remove that piece's possible moves
            if(!isNull(clickedPiece)){
                mainview.removeMoveOptionsCircles();
            }
            //Add the Clicked piece's possible moves
            this.clickedPiece = board.getPiece(position);
            Boolean isWhite = this.clickedPiece.getIsWhite();
            if (board.getWhitesTurn() == isWhite) {
                PieceType pieceType = board.getPiece(position).getPieceType();
                ArrayList<Position> moves = board.chooseMove(position, isWhite, board, pieceType);
                mainview.addMoveOptionsCircles(convertMovesFromPositiontoPositionView(moves));
            }
        }
    }

    //Convert the Position to PositionView for the View component
    private ArrayList<PositionView> convertMovesFromPositiontoPositionView(ArrayList<Position> moves){
        ArrayList<PositionView> movesForMainView = new ArrayList<>();
        for (Position position:moves) {
            movesForMainView.add(new PositionView(position.getFile(), position.getRank()));
        }
        return movesForMainView;
    }

    //Move the previously clicked piece to the clicked circle position and remove the
    private void circleClicked(Position newPosition){
        Position previousPosition = clickedPiece.getPosition();
        board.movePieces(previousPosition,newPosition);
        this.clickedPiece = null;
    }


}
