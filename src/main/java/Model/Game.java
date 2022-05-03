package Model;

import Contract.Contract;
import Model.Utilities.Check;
import Model.Utilities.Moves;
import Model.Pieces.*;
import Model.Utilities.Position;

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
    private Piece promotionPiece;
    private final Fen fen = new Fen();
    private final Draws draws = new Draws();

    public Game(Contract.Observer observer) {
        this.board = new Board();
        createPieces(this.whitePieces, true);
        createPieces(this.blackPieces, false);
        this.whiteCastling = new boolean[]{true, true};
        this.blackCastling = new boolean[]{true, true};
        this._observer = observer;
        this.whitesTurn = true;
        this.halfMove = 0;
        this.fullMove = 1;
        this.fen.updateFen(exportBoardPositionForFEN(), this.whitesTurn, this.whiteCastling, this.blackCastling, this.enPassantPositionForFen, this.halfMove, this.fullMove);
    }

    public Game(Contract.Observer observer, Board board, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces, boolean[] whiteCastling, boolean[] blackCastling, boolean whitesTurn, Position enPassantPosition, int halfmove, int fullmove) {
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
        this.fen.updateFen(exportBoardPositionForFEN(), whitesTurn, whiteCastling, blackCastling, this.enPassantPositionForFen, this.halfMove, this.fullMove);
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
        for(int i = 0; i < 8; i++) {
            colouredPiecesArray.add(new Piece(isWhite, PieceType.Pawn));
            this.board.addPiece(new Position(i,rank), colouredPiecesArray.get(i));
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
            this.board.addPiece(new Position(i-8, rank), colouredPiecesArray.get(i));
        }
    }

//region Make A Move

    /**
     * Move the piece from the previousPosition to the newPosition.
     * Also checking for en Passant and promotion. Then update the view and endTurn
     *
     * @param previousPosition current position of the piece
     * @param newPosition where the piece will be moving to
     */
    public void makeAMove(Position previousPosition, Position newPosition) {
        if (isNull(getPiece(previousPosition)) || getPiece(previousPosition).getIsWhite() != this.whitesTurn) {
            return;
        }

        if (getLegalMoves(previousPosition).stream().noneMatch(position -> position.getFile()== newPosition.getFile() && position.getRank()== newPosition.getRank())) { // making sure the newPosition is a legal move
            return;
        }
        if (nonNull(promotionPiece)) { // making sure the newPosition is a legal move
            return;
        }
        this.movePieceOnBoard(previousPosition, newPosition);
        disableEnPassant();

        // check if a pawn double moved and do what is needed
        Piece piece = this.board.getPiece(newPosition);
        if(piece.getPieceType() == PieceType.Pawn && abs(previousPosition.getRank() - newPosition.getRank()) == 2) {
            this.enPassantPositionForFen = new Position(newPosition.getFile(),(newPosition.getRank()+previousPosition.getRank())/2);
            enableEnPassant(newPosition, piece.getIsWhite());
        }

        disableCastlingIfRequired(piece, previousPosition);
        if(canPromote(newPosition, piece)) {
            this.promotionPiece = piece;
            this._observer.requiresPromotionOptions();
        } else {
            endTurn();
        }
    }

    /**
     * Remove the piece from the old position on the board, and add it to the new position.
     * Remove any capture pieces from whitePieces or blackPieces even an en Passant.
     *
     *
     * @param previousPosition current position of the piece
     * @param newPosition where the piece will be moving to
     */
    private void movePieceOnBoard(Position previousPosition, Position newPosition) {
        Piece capturedPiece = this.board.getPiece(newPosition);
        Piece movedPiece = this.board.getPiece(previousPosition);
        if (nonNull(capturedPiece)) {
            getColouredPieces(capturedPiece.getIsWhite()).remove(capturedPiece);
            this.halfMove = 0;
        } else if(movedPiece.getPieceType() == PieceType.Pawn) {
            if(newPosition.getFile() != previousPosition.getFile()) {
                Position enPassantPosition = new Position(newPosition.getFile(), previousPosition.getRank());
                getColouredPieces(this.board.getPiece(enPassantPosition).getIsWhite()).remove(this.board.getPiece(enPassantPosition));
                this.board.removePiece(enPassantPosition);
            }
            this.halfMove = 0;
        } else if(movedPiece.getPieceType() == PieceType.King) {
            if (abs(previousPosition.getFile() - newPosition.getFile()) == 2) {
                if (newPosition.getFile()>4) {
                    Piece rookCastlingPiece = this.board.getPiece(new Position(7, previousPosition.getRank()));
                    this.board.removePiece(rookCastlingPiece.getPosition());
                    this.board.addPiece(new Position(5, previousPosition.getRank()),rookCastlingPiece);
                } else {
                    Piece rookCastlingPiece = this.board.getPiece(new Position(0, previousPosition.getRank()));
                    this.board.removePiece(rookCastlingPiece.getPosition());
                    this.board.addPiece(new Position(3, previousPosition.getRank()),rookCastlingPiece);
                }
            }
            this.halfMove += 1;
        } else {
            this.halfMove += 1;
        }

        this.board.removePiece(previousPosition);
        this.board.addPiece(newPosition,movedPiece);

    }

    /**
     * get the correct ArrayList of pieces
     *
     * @param isWhite white pieces or black pieces
     * @return whitePieces or blackPieces
     */
    private ArrayList<Piece> getColouredPieces(boolean isWhite) {
        return isWhite?this.whitePieces:this.blackPieces;
    }



    /**
     * Remove the availability to en Passant from all the necessary pieces
     */
    private void disableEnPassant() {
        for (Piece piece:this.enPassantAvailablePieces) {
            piece.setEnPassantAvailableToTakeFile(null);
        }
        this.enPassantPositionForFen = null;
        this.enPassantAvailablePieces.clear();
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
            if (tempFile >= 0 && tempFile < 8) {
                Piece piece = this.board.getPiece(new Position(tempFile,newPosition.getRank()));
                if(nonNull(piece) && piece.getPieceType() == PieceType.Pawn && piece.getIsWhite() != isWhite) {
                    piece.setEnPassantAvailableToTakeFile(newPosition.getFile());
                    this.enPassantAvailablePieces.add(piece);
                }
            }
        }
    }

    /**
     * If either the king or rook moves, change the necessary castling to false
     *
     * @param piece that just moved
     * @param previousPosition that the piece was on
     */
    private void disableCastlingIfRequired(Piece piece, Position previousPosition) {
        boolean[] castling = this.whitesTurn?this.whiteCastling:this.blackCastling;
        if (!castling[0] && !castling[1]) {
            return;
        }
        if (piece.getPieceType()==PieceType.King) {
            castling[0]=false;
            castling[1]=false;
        } else if (piece.getPieceType()==PieceType.Rook) {
            if (previousPosition.getFile()==7) {
                castling[0]=false;
            } else if (previousPosition.getFile()==0) {
                castling[1]=false;
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
    private boolean canPromote(Position newPosition, Piece piece) {
        if(piece.getPieceType() != PieceType.Pawn) {
            return false;
        }
        int rank = piece.getIsWhite() ? 0 : 7;
        return newPosition.getRank() == rank;
    }

    public void promotionPieceDecision(PieceType pieceType) {
        this.promotionPiece.pawnPromotion(pieceType);
        this.promotionPiece = null;

        endTurn();
    }

    /**
     * Change whose turn it is
     * update the fullMove if required
     * check to see if it's a checkmate or stalemate
     */
    private void endTurn() {
        this.whitesTurn = !this.whitesTurn;
        this.fullMove +=this.whitesTurn?1:0;
        this.fen.updateFen(exportBoardPositionForFEN(), this.whitesTurn, this.whiteCastling, this.blackCastling, this.enPassantPositionForFen, this.halfMove, this.fullMove);
        if (!gameOver()){
            this._observer.update();
        }
    }

    /**
     * If there is no legal moves, call gameOver() in Observer to end the game
     */
    public boolean gameOver() {
        if (Check.isThereALegalMove(this, this.whitesTurn)) {
            if (Check.isTheKingUnderAttack(this, this.whitesTurn)) {
                this._observer.gameOver(false,this.whitesTurn?"BLACK":"WHITE");
            } else {
                this._observer.gameOver(true,"STALEMATE");
            }
            return true;
        }
        if (this.halfMove>=50) {
            this._observer.gameOver(true,"50 Rule Move!");
            return true;
        } else if (this.draws.isThreefoldRepetition(this.fen.getFenForRepetitionCheck(), this.halfMove)) {
            this._observer.gameOver(true,"Repetition!");
            return true;
        } else if (this.draws.isThereInsufficientMaterial(getClonedColouredPieces(true), getClonedColouredPieces(false))) {
            this._observer.gameOver(true,"Insufficient Material!");
            return true;
        } else {
            return false;
        }
    }

//endregion

//region Getting Possible Moves

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
            if(!Check.isTheKingUnderAttack(tempGame,this.whitesTurn)) {
                onlyLegalMoves.add(movePosition);
            }
        }
        Piece piece = getPiece(position);
        if (nonNull(piece) && piece.getPieceType()==PieceType.King) {
            checkIfCastlingIsLegal(onlyLegalMoves, piece);
        }
        return onlyLegalMoves;
    }

    /**
     * This should have all variables identicle, but changing the cloned Game should not change the real Game
     *
     * @return a Game that has been deep cloned
     */
    private Game cloneGame() {
        ArrayList<Piece> clonedWhitePieces = getClonedColouredPieces(true);
        ArrayList<Piece> clonedBlackPieces = getClonedColouredPieces(false);
        Position tempEnPassantPosition = isNull(this.enPassantPositionForFen)?null:new Position(this.enPassantPositionForFen.getFile(), this.enPassantPositionForFen.getRank());
        return new Game(null, createCloneBoard(clonedWhitePieces, clonedBlackPieces), clonedWhitePieces, clonedBlackPieces, new boolean[]{this.whiteCastling[0], this.whiteCastling[1]}, new boolean[] {this.blackCastling[0],this.blackCastling[1]}, this.whitesTurn, tempEnPassantPosition, this.halfMove, this.fullMove);
    }

    public ArrayList<Piece> getClonedColouredPieces(boolean isWhite) {
        ArrayList<Piece> originalPieces = isWhite?this.whitePieces:this.blackPieces;
        ArrayList<Piece> clonedPieces = new ArrayList<>();
        for (Piece piece:originalPieces) {
            clonedPieces.add(new Piece(piece));
        }
        return clonedPieces;
    }

    /**
     * Create a new board and using the @param add the pieces to the board. This needs to be done
     *
     * @param clonedWhitePieces an ArrayList<Pieces> of all the white pieces still in the game
     * @param clonedBlackPieces an ArrayList<Pieces> of all the black pieces still in the game
     * @return clone of the board
     */
    private Board createCloneBoard(ArrayList<Piece> clonedWhitePieces, ArrayList<Piece> clonedBlackPieces) {
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
     * First checks to see if the final king position is a legal move.
     * Then if either:
     *      castling is available,
     *      king is in check,
     *      the position the king moves through is illegal
     *      
     * It will remove the castling position out of onlyLegalMoves
     *
     * @param onlyLegalMoves all the legal moves the piece can make
     * @param piece king piece that is being moved
     */
    private void checkIfCastlingIsLegal(ArrayList<Position> onlyLegalMoves, Piece piece) {
        boolean[] castling = getColouredCastling(piece.getIsWhite());
        Position position = onlyLegalMoves.stream().filter(castlingPosition -> castlingPosition.getFile()==(piece.getPosition().getFile()+2)).findAny().orElse(null);
        if (nonNull(position)) {
            if (!castling[0] || Check.isTheKingUnderAttack(this, this.whitesTurn) || onlyLegalMoves.stream().noneMatch(position1 -> position1.getFile()==5 && position1.getRank()==piece.getPosition().getRank())) {
                onlyLegalMoves.remove(onlyLegalMoves.stream().filter(position1 -> position1.getFile()==6).findAny().orElse(null));
            }
        }
        position = onlyLegalMoves.stream().filter(castlingPosition -> castlingPosition.getFile()==(piece.getPosition().getFile()-2)).findAny().orElse(null);
        if (nonNull(position)) {
            if (!castling[1] || Check.isTheKingUnderAttack(this, this.whitesTurn) || onlyLegalMoves.stream().noneMatch(position1 -> position1.getFile()==3 && position1.getRank()==piece.getPosition().getRank())) {
                onlyLegalMoves.remove(onlyLegalMoves.stream().filter(position1 -> position1.getFile()==2).findAny().orElse(null));
            }
        }
    }

//endregion
    
//region Getters

    /**
     * @return Full Fen string
     */
    public String getFullFen() {
        return this.fen.getFullFen();
    }

    /**
     * basically create a copy of boardSquares to help create the first FEN
     *
     * @return 2d array of the first fen position
     */
    private Piece[][] exportBoardPositionForFEN() {
        Piece[][] piece2DArray = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++) {
            piece2DArray[rank] = this.board.getRankPiece(rank);
        }
        return piece2DArray;
    }

    /**
     * Get a clone of the piece at the position
     *
     * @param position of the piece
     * @return a clone of the required piece
     */
    public Piece getPiece(Position position) {
        return this.board.getPieceClone(position);
    }

    /**
     * Get whose turn it is
     *
     * @return whitesTurn
     */
    public Boolean getWhitesTurn() {
        return this.whitesTurn;
    }

    /**
     * Get the boolean[] of intended colour for castling
     *
     * @param isWhite what colour is required
     * @return boolean[]
     */
    public boolean[] getColouredCastling(boolean isWhite) {
        boolean[] castling = isWhite?this.whiteCastling:this.blackCastling;
        return new boolean[]{castling[0],castling[1]};
    }

    //endregion

}