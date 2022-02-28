package Model;

import Contract.Contract;
import Model.Utilities.Check;
import Model.Utilities.Fen;
import Model.Utilities.Moves;
import Model.Pieces.*;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Game {

    private final Board board;
    private ArrayList<Piece> whitePieces = new ArrayList<>();
    private ArrayList<Piece> blackPieces = new ArrayList<>();
    private final boolean[] whiteCastling;
    private final boolean[] blackCastling;
    private boolean whitesTurn;
    private final Contract.Observer _observer;
    private final ArrayList<Piece> enPassantAvailablePieces = new ArrayList<>();
    private Position enPassantPositionForFen;
    private int halfMove;
    private int fullMove;

    public Game(Contract.Observer observer) {
        board = new Board();
        createPieces(whitePieces, true);
        createPieces(blackPieces, false);
        whiteCastling = new boolean[]{true, true};
        blackCastling = new boolean[]{true, true};
        this._observer = observer;
        whitesTurn = true;
        halfMove = 0;
        fullMove = 1;
    }

    public Game(Contract.Observer observer, Board board, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces, boolean[] whiteCastling, boolean[] blackCastling, boolean whitesTurn, Position enPassantPosition, int halfmove, int fullmove){
        this._observer = observer;
        this.board = board;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.whiteCastling = whiteCastling;
        this.blackCastling = blackCastling;
        this.whitesTurn = whitesTurn;
        this.enPassantPositionForFen = enPassantPosition;
        this.halfMove = halfmove;
        this.fullMove = fullmove;
        if (nonNull(enPassantPosition)) {
            int rank = enPassantPosition.getRank() + (whitesTurn?1:-1);
            Position position = new Position(enPassantPosition.getFile(),rank);
            enableEnPassant(position, !whitesTurn);
        }
    }


    /**
     * Create all the piece objects for a standard game
     * and add them to the board in the correct position
     *
     * @param colouredPiecesArray either whitePieces variable or blackPieces
     * @param isWhite white pieces or black pieces
     */
    private void createPieces(ArrayList<Piece> colouredPiecesArray, boolean isWhite) {
        int rank = isWhite ? 6 : 1;
        for(int i = 0; i < 8; i++){
            colouredPiecesArray.add(new Piece(isWhite, PieceType.Pawn));
            board.addPiece(new Position(i,rank), colouredPiecesArray.get(i));
        }

        rank = isWhite ? 7 : 0;

        colouredPiecesArray.add(new Piece(isWhite, PieceType.Rook));
        colouredPiecesArray.add(new Piece(isWhite, PieceType.Knight));
        colouredPiecesArray.add(new Piece(isWhite, PieceType.Bishop));
        colouredPiecesArray.add(new Piece(isWhite, PieceType.Queen));
        colouredPiecesArray.add(new Piece(isWhite, PieceType.King));
        colouredPiecesArray.add(new Piece(isWhite, PieceType.Bishop));
        colouredPiecesArray.add(new Piece(isWhite, PieceType.Knight));
        colouredPiecesArray.add(new Piece(isWhite, PieceType.Rook));

        for(int i = 8; i < 16; i++) {
            board.addPiece(new Position(i-8, rank), colouredPiecesArray.get(i));
        }
    }














    /**
     * Move the piece from the previousPosition to the newPosition.
     * Also checking for en Passant and promotion. Then update the view and endTurn
     *
     * @param previousPosition current position of the piece
     * @param newPosition where the piece will be moving to
     */
    public void moveAMove(Position previousPosition, Position newPosition) {
        if (isNull(getPiece(previousPosition)) || getPiece(previousPosition).getIsWhite()!=whitesTurn){
            return;
        }

        if (getLegalMoves(previousPosition).stream().noneMatch(position -> position.getFile()== newPosition.getFile() && position.getRank()== newPosition.getRank())){
            return;
        }
        this.movePieceOnBoard(previousPosition, newPosition);
        disableEnPassant();

        // check if a pawn double moved and do what is needed
        Piece piece = board.getPiece(newPosition);
        if(piece.getPieceType() == PieceType.Pawn && abs(previousPosition.getRank() - newPosition.getRank()) == 2) {
            this.enPassantPositionForFen = new Position(newPosition.getFile(),(newPosition.getRank()+previousPosition.getRank())/2);
            enableEnPassant(newPosition, piece.getIsWhite());
        }

        checkForPromotion(newPosition, piece);

        endTurn();
        updateView();

    }

    /** Remove the piece from the old position on the board, and add it to the new position.
     * Remove any capture pieces from whitePieces or blackPieces even an en Passant.
     *
     *
     * @param previousPosition current position of the piece
     * @param newPosition where the piece will be moving to
     */
    private void movePieceOnBoard(Position previousPosition, Position newPosition) {
        Piece capturedPiece = board.getPiece(newPosition);
        Piece movedPiece = board.getPiece(previousPosition);
        if (nonNull(capturedPiece)){
            getColouredPieces(capturedPiece.getIsWhite()).remove(capturedPiece);
            halfMove = 0;
        }
        else if(movedPiece.getPieceType()==PieceType.Pawn) {
            if(newPosition.getFile()!= previousPosition.getFile()) {
                Position enPassantPosition = new Position(newPosition.getFile(), previousPosition.getRank());
                getColouredPieces(board.getPiece(enPassantPosition).getIsWhite()).remove(board.getPiece(enPassantPosition));
                board.removePiece(enPassantPosition);
            }
            halfMove = 0;
        }
        else {
            halfMove += 1;
        }

        board.removePiece(previousPosition);
        board.addPiece(newPosition,movedPiece);

    }

    /** get the correct ArrayList of pieces
     *
     * @param isWhite white pieces or black pieces
     * @return whitePieces or blackPieces
     */
    private ArrayList<Piece> getColouredPieces(boolean isWhite){
        return isWhite?whitePieces:blackPieces;
    }



    /**
     * Remove the availability to en Passant from all the necessary pieces
     */
    private void disableEnPassant() {
        for (Piece piece:enPassantAvailablePieces) {
            piece.setEnPassantAvailableToTakeFile(null);
        }
        this.enPassantPositionForFen = null;
        enPassantAvailablePieces.clear();
    }


    /**
     * Update any pawn that can en Passant next turn and then add those pieces that
     * are able to en passant on the next turn to the enPassantAvailablePieces list
     * 
     * @param newPosition new position of the pawn
     * @param isWhite if the pawn is white or black
     */
    
    private void enableEnPassant(Position newPosition, boolean isWhite) {
        int tempFile;
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempFile = newPosition.getFile() + iFile;
            if (tempFile >= 0 && tempFile < 8){
                Piece piece = board.getPiece(new Position(tempFile,newPosition.getRank()));
                if(nonNull(piece) && piece.getPieceType() == PieceType.Pawn && piece.getIsWhite() != isWhite){
                    piece.setEnPassantAvailableToTakeFile(newPosition.getFile());
                    enPassantAvailablePieces.add(piece);
                }
            }
        }
    }


    /**
     * WIP!
     * Check to see if a pawn has made it to the other side of the board. If so call the promotion function
     * 
     * @param newPosition of the piece
     * @param piece that just moved
     */
    private void checkForPromotion(Position newPosition, Piece piece) {
        if(piece.getPieceType() != PieceType.Pawn) {
            return;
        }
        int rank = piece.getIsWhite() ? 0 : 7;
        if(newPosition.getRank() != rank){
            return;
        }
        piece.pawnPromotion(PieceType.Queen);
    }


    /**
     * Call updateView function in the observer
     *
     */
    private void updateView() {
        _observer.update();
    }


    /**
     * Change whose turn it is
     * update the fullMove if required
     * check to see if it's a checkmate or stalemate
     */
    private void endTurn() {
        whitesTurn = !whitesTurn;
        fullMove +=whitesTurn?1:0;

        gameOver();
    }

    private void gameOver(){
        if (Check.isThereALegalMove(this, whitesTurn)){
            if (Check.isTheKingUnderAttack(this, whitesTurn)){
                System.out.println("CHECKMATE BITCHES!!");
            }
            else {
                System.out.println("STALEMATE??");
            }
        }
    }













    /**
     * Get all move (including illegal moves) from the piece on @param position using the Moves function "getAllMovesIncludingIllegalMoves"
     * into an ArrayList. Traverse though that list and clone this Game object, on the cloned Game make one of the moves, then using the
     * Check function "isTheKingUnderAttack", check to see if the move is legal or not.     *
     * 
     * @param position of the piece that we want all the legal moves for
     * @return list of possible legal moves
     */
    public ArrayList<Position> getLegalMoves(Position position) {
        ArrayList<Position> movesIncludingIllegalMoves = Moves.getAllMovesIncludingIllegalMoves(position, this);
        ArrayList<Position> onlyLegalMoves = new ArrayList<>();
        for (Position movePosition:movesIncludingIllegalMoves) {
            Game tempGame = cloneGame();
            tempGame.movePieceOnBoard(position, movePosition);
            if(!Check.isTheKingUnderAttack(tempGame,whitesTurn)){
                onlyLegalMoves.add(movePosition);
            }
        }
        return onlyLegalMoves;
    }

    /** This should have all variables identicle, but changing the cloned Game should not change the real Game
     *
     * @return a Game that has been deep cloned
     */
    private Game cloneGame(){
        ArrayList<Piece> clonedWhitePieces = getClonedColouredPieces(true);
        ArrayList<Piece> clonedBlackPieces = getClonedColouredPieces(false);
        Position tempEnPassantPosition = isNull(enPassantPositionForFen)?null:new Position(enPassantPositionForFen.getFile(), enPassantPositionForFen.getRank());
        return new Game(null, createCloneBoard(clonedWhitePieces, clonedBlackPieces), clonedWhitePieces, clonedBlackPieces, new boolean[]{whiteCastling[0], whiteCastling[1]}, new boolean[] {blackCastling[0],blackCastling[1]}, whitesTurn, tempEnPassantPosition, halfMove, fullMove);
    }

    public ArrayList<Piece> getClonedColouredPieces(boolean isWhite){
        ArrayList<Piece> originalPieces = isWhite?whitePieces:blackPieces;
        ArrayList<Piece> clonedPieces = new ArrayList<>();
        for (Piece piece:originalPieces) {
            clonedPieces.add(new Piece(piece));
        }
        return clonedPieces;
    }

    /** Create a new board and using the @param add the pieces to the board. This needs to be done
     *
     * @param clonedWhitePieces an ArrayList<Pieces> of all the white pieces still in the game
     * @param clonedBlackPieces an ArrayList<Pieces> of all the black pieces still in the game
     * @return clone of the board
     */
    private Board createCloneBoard(ArrayList<Piece> clonedWhitePieces, ArrayList<Piece> clonedBlackPieces){
        Board tempBoard = new Board();
        for (Piece piece:clonedWhitePieces) {
            tempBoard.addPiece(piece.getPosition(),piece);
        }
        for (Piece piece:clonedBlackPieces) {
            tempBoard.addPiece(piece.getPosition(),piece);
        }
        return tempBoard;
    }


    /**
     *
     * @return Full Fen string
     */
    public String getCompleteFEN() {
        return Fen.convertBoardToFen(exportBoardPositionForFEN(), whitesTurn, whiteCastling, blackCastling, enPassantPositionForFen, halfMove, fullMove);
    }

    /**
     * basically create a copy of boardSquares to help create the first FEN
     *
     * @return 2d array of the first fen position
     */
    private Piece[][] exportBoardPositionForFEN() {
        Piece[][] piece2DArray = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++) {
            piece2DArray[rank] = board.getRankPiece(rank);
        }
        return piece2DArray;
    }









    /**Get a clone of the piece at the position
     *
     * @param position of the piece
     * @return a clone of the required piece
     */
    public Piece getPiece(Position position) {
        return board.getPieceClone(position);
    }

    /** Get whose turn it is
     *
     * @return whitesTurn
     */
    public boolean getWhitesTurn() {
        return whitesTurn;
    }




}