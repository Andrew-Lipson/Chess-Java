package Controller;

import Contract.Contract;
import Model.Game;
import Model.Pieces.PieceType;
import Model.Position;
import Model.Pieces.Piece;
import View.MainView;
import View.PositionView;

import java.util.ArrayList;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Controller implements Contract.Listener, Contract.Observer {

    private Game game;
    private MainView mainview;
    private Piece clickedPiece = null;

    public void startApplication(Game game, MainView mainview) {
        this.game = game;
        this.mainview = mainview;

        updateView();
        updateView();
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

    public void stockFish(){
        Stockfish stockfish = new Stockfish();
        StockFishOutput stockFishOutput = stockfish.getStockfishMove(game.getCompleteFEN());
        if(isNull(stockFishOutput.getPieceType())){
            game.makeAMove(stockFishOutput.getPreviousPosition(),stockFishOutput.getNewPosition(), true);
        } else{
            game.makeAMove(stockFishOutput.getPreviousPosition(),stockFishOutput.getNewPosition(), true);
            game.promotionPieceDecision(stockFishOutput.getPieceType());
        }
    }

    @Override
    public void updateView(){
        mainview.updateView(game.getCompleteFEN());
    }




    @Override
    public void displayPromotionPopup(){
        updateView();
        mainview.promotionPopup(game.getWhitesTurn());
    }

    @Override
    public void gameOver(boolean isStaleMate, boolean isWhite){
        updateView();
        mainview.gameOverPopup(isStaleMate,isWhite);
    }

    @Override
    public void handlePieceClicked(PositionView positionView) {
        // Check if the piece clicked was just clicked, and remove the possible moves from the board
        Position position = new Position(positionView.getFile(), positionView.getRank());
        if (nonNull(clickedPiece) && clickedPiece.getPosition().getFile() == position.getFile() && clickedPiece.getPosition().getRank() == position.getRank()) {
            mainview.removeMoveOptionsCircles(positionView);
            this.clickedPiece = null;
        } else {
            // Check if another piece has been previously clicked and remove that piece's possible moves
            if(nonNull(clickedPiece)) {
                mainview.removeMoveOptionsCircles(new PositionView(clickedPiece.getPosition().getFile(), clickedPiece.getPosition().getRank()));
            }
            // Add the Clicked piece's possible moves
            this.clickedPiece = game.getPiece(position);
            boolean isWhite = this.clickedPiece.getIsWhite();
            if (game.getWhitesTurn() == isWhite) {
                ArrayList<Position> moves = game.getLegalMoves(position);
                mainview.addMoveOptionsCircles(positionView, convertMovesFromPositiontoPositionView(moves));
            }
        }
    }

    @Override
    public void handleCircleClicked(PositionView positionView) {
        Position newPosition = new Position(positionView.getFile(), positionView.getRank());
        Position previousPosition = clickedPiece.getPosition();
        game.makeAMove(previousPosition,newPosition,false);
        this.clickedPiece = null;
    }

    @Override
    public void onPromotionPieceDecided(String string) {
        game.promotionPieceDecision(PieceType.getPieceType(string));
    }

    @Override
    public void newGame(){
        this.game = new Game(this);
        updateView();

    }
}
