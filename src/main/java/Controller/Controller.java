package Controller;

import Contract.Contract;
import Controller.Engine.Stockfish;
import Model.Fen;
import Model.Game;
import Model.Pieces.PieceType;
import Model.Utilities.Position;
import Model.Pieces.Piece;
import View.MainStage;
import View.Board.PositionView;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

public class Controller implements Contract.Listener, Contract.Observer {

    private Game game;
    private MainStage mainStage;
    private Piece clickedPiece;
    private Boolean computerIsWhite;
    private Stockfish stockfish;

    /**
     * Starting running the application by creating the View and showing the main menu
     *
     * @param mainStage The View component
     */
    public void startApplication(MainStage mainStage) {
        this.mainStage = mainStage;
        this.mainStage.createStage();
        showMainMenu();

    }

    /**
     * Get the View to show the Main Menu
     */
    public void showMainMenu() {
        this.mainStage.showMainMenu();
    }

    /**
     * Convert the Position to PositionView for the View component
     * 
     * @param moves An ArrayList of Position
     * @return An ArrayList of PositionView
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

    /**
     * Update the View component
     */
    public void updateView() {
        this.mainStage.updateView(game.getFullFen());
    }

// region Observer Interface

    @Override
    public void update() {
        updateView();
        if (this.game.getWhitesTurn().equals(this.computerIsWhite)) {
            new Thread(this::computerToMakeAMove).start();
        }
    }

    @Override
    public void requiresPromotionOptions() {
        updateView();
        if (!this.game.getWhitesTurn().equals(this.computerIsWhite)) {
            this.mainStage.promotionPopup(game.getWhitesTurn());
        }
    }

    @Override
    public void gameOver(boolean isADraw, String string) {
        updateView();
        this.mainStage.gameOverPopup(isADraw,string);
    }

//endregion

//region Listener Interface

    @Override
    public void handlePieceClicked(PositionView positionView) {
        // Check if the piece clicked was just clicked, and remove the possible moves from the board
        Position position = new Position(positionView.getFile(), positionView.getRank());
        if (nonNull(this.clickedPiece) && this.clickedPiece.getPosition().getFile() == position.getFile() && this.clickedPiece.getPosition().getRank() == position.getRank()) {
            this.mainStage.removeMoveOptionsCircles(positionView);
            this.clickedPiece = null;
        } else {
            // Check if another piece has been previously clicked and remove that piece's possible moves
            if(nonNull(this.clickedPiece)) {
                this.mainStage.removeMoveOptionsCircles(new PositionView(this.clickedPiece.getPosition().getFile(), this.clickedPiece.getPosition().getRank()));
            }
            // Add the Clicked piece's possible moves
            this.clickedPiece = this.game.getPiece(position);
            boolean isWhite = this.clickedPiece.getIsWhite();
            if (this.game.getWhitesTurn() == isWhite) {
                ArrayList<Position> moves = this.game.getLegalMoves(position);
                this.mainStage.addMoveOptionsCircles(positionView, convertMovesFromPositiontoPositionView(moves));
            }
        }
    }

    @Override
    public void handleCircleClicked(PositionView positionView) {
        Position newPosition = new Position(positionView.getFile(), positionView.getRank());
        Position previousPosition = this.clickedPiece.getPosition();
        this.game.makeAMove(previousPosition,newPosition);
        this.clickedPiece = null;
    }

    @Override
    public void handlePromotionDecision(String string) {
        this.game.promotionPieceDecision(PieceType.getPieceType(string));
    }

    @Override
    public void newGame(boolean singlePlayer, Boolean computerIsWhite) {
        if (singlePlayer) {
            this.computerIsWhite = computerIsWhite;
            this.mainStage.showBoard(computerIsWhite);
        } else {
            this.computerIsWhite = null;
            this.mainStage.showBoard(false);
        }
        this.game = new Game(this);
        this.stockfish = new Stockfish(this.game);
        updateView();
        this.game.gameOver();
        update();
    }
//endregion

}
