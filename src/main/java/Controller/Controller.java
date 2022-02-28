package Controller;

import Contract.Contract;
import Model.Game;
import Model.Position;
import Model.Pieces.Piece;
import View.MainView;
import View.PositionView;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class Controller implements Contract.Listener, Contract.Observer {

    Game game;
    MainView mainview;
    Piece clickedPiece = null;

    public void startApplication(Game game, MainView mainview) {
        this.game = game;
        this.mainview = mainview;

        update();
        showBoard();
    }

    private void showBoard() {
        mainview.showBoard();
    }

    /**
     * Convert the Position to PositionView for the View component
     * 
     * @param moves
     * @return
     */
    private ArrayList<PositionView> convertMovesFromPositiontoPositionView(ArrayList<Position> moves) {
        ArrayList<PositionView> movesForMainView = new ArrayList<>();
        for (Position position: moves) {
            movesForMainView.add(new PositionView(position.getFile(), position.getRank()));
        }
        return movesForMainView;
    }

    @Override
    public void update() {
        mainview.updateView(game.getCompleteFEN());
    }

    @Override
    public void handlePieceClicked(PositionView positionView) {
        // Check if the piece clicked was just clicked, and remove the possible moves from the board
        Position position = new Position(positionView.getFile(), positionView.getRank());
        if (nonNull(clickedPiece) && clickedPiece.getPosition().getFile() == position.getFile() && clickedPiece.getPosition().getRank() == position.getRank()) {
            mainview.removeMoveOptionsCircles();
            this.clickedPiece = null;
        } else {
            // Check if another piece has been previously clicked and remove that piece's possible moves
            if(nonNull(clickedPiece)) {
                mainview.removeMoveOptionsCircles();
            }
            // Add the Clicked piece's possible moves
            this.clickedPiece = game.getPiece(position);
            boolean isWhite = this.clickedPiece.getIsWhite();
            if (game.getWhitesTurn() == isWhite) {
                ArrayList<Position> moves = game.getLegalMoves(position);
                mainview.addMoveOptionsCircles(convertMovesFromPositiontoPositionView(moves));
            }
        }
    }

    @Override
    public void handleCircleClicked(PositionView positionView) {
        Position newPosition = new Position(positionView.getFile(), positionView.getRank());
        Position previousPosition = clickedPiece.getPosition();
        game.moveAMove(previousPosition,newPosition);
        this.clickedPiece = null;
    }
}
