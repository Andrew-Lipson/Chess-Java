package Controller;

import Contract.Contract;
import Controller.Engine.Stockfish;
import Model.Game;
import Model.Pieces.PieceType;
import Model.Position;
import Model.Pieces.Piece;
import Model.Fen;
import View.MainStage;
import View.Board.PositionView;
import javafx.application.Platform;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class Controller implements Contract.Listener, Contract.Observer {

    private Game game;
    private MainStage mainview;
    private Piece clickedPiece = null;
    private Boolean computerIsWhite = null;

    public void startApplication(MainStage mainview) {
        this.mainview = mainview;
        mainview.createStage();
        showMainMenu();

    }

    public void showMainMenu() {
        mainview.showMainMenu();
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

    /**
     * Get the computer to make a move
     */
    public void computerToMakeAMove() {
        this.stockfish.stockfishsTurn();
    }

    public void updateView(){
        mainview.updateView(game.getFullFen());
    }

// region Observer Interface

    @Override
    public void update(){
        updateView();
        if (this.game.getWhitesTurn().equals(this.computerIsWhite)) {
            new Thread(this::computerToMakeAMove).start();
        }
    }

    @Override
    public void displayPromotionPopup(){
        updateView();
        if (!this.game.getWhitesTurn().equals(this.computerIsWhite)) {
            this.mainStage.promotionPopup(game.getWhitesTurn());
        }
    }

    @Override
    public void gameOver(boolean isADraw, String string){
        updateView();
        mainview.gameOverPopup(isADraw,string);
    }

//endregion

//region Listener Interface

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
    public void newGame(boolean singlePlayer, Boolean computerIsWhite){
        if (singlePlayer){
            this.computerIsWhite = computerIsWhite;
            mainview.showBoard(computerIsWhite);
        } else{
            this.computerIsWhite = null;
            mainview.showBoard(false);
        }
        this.game = new Game(this);
        this.stockfish = new Stockfish(this.game);
        updateView();
        this.game.gameOver();
        update();
    }
//endregion

}
