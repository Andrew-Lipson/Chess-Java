package Controller;



import Model.Board;
import Model.Observer;
import Model.Position;
import Model.pieces.Piece;
import Model.pieces.PieceType;
import View.MainView;
import View.OnClick;
import View.PositionView;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Controller implements Observer, OnClick {

    Board board;
    MainView mainview;
    Piece clickedPiece = null;

    public Controller(){

    }

    public void startApplication(Board board, MainView mainview){
        this.board = board;
        this.mainview = mainview;

        update();
        showBoard();
    }

    private void showBoard(){
        mainview.showBoard();
    }



    //Convert the Position to PositionView for the View component
    private ArrayList<PositionView> convertMovesFromPositiontoPositionView(ArrayList<Position> moves){
        ArrayList<PositionView> movesForMainView = new ArrayList<>();
        for (Position position:moves) {
            movesForMainView.add(new PositionView(position.getFile(), position.getRank()));
        }
        return movesForMainView;
    }

    //Once a piece is clicked and displays possible moves or removes the possibles moves
    @Override
    public void pieceClicked(PositionView positionView){
        // Check if the piece clicked was just clicked, and remove the possible moves from the board
        Position position = new Position(positionView.getFile(), positionView.getRank());
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
            boolean isWhite = this.clickedPiece.getIsWhite();
            if (board.getWhitesTurn() == isWhite) {
                PieceType pieceType = board.getPiece(position).getPieceType();
                ArrayList<Position> moves = board.chooseMove(position, isWhite, board, pieceType);
                mainview.addMoveOptionsCircles(convertMovesFromPositiontoPositionView(moves));
            }
        }
    }

    //Move the previously clicked piece to the clicked circle position and remove the
    @Override
    public void circleClicked(PositionView position){
        Position newPosition = new Position(position.getFile(), position.getRank());
        Position previousPosition = clickedPiece.getPosition();
        board.movePieces(previousPosition,newPosition);
        this.clickedPiece = null;
    }


    @Override
    public void update() {
        mainview.updateView(board.getCompleteFEN());
    }
}
