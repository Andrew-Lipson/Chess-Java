package Model;

import Model.Contract.Observer;
import Model.pieces.*;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.util.Objects.nonNull;

public class Game {

    private final Board board;
    private Board tempBoard;
    private ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    private ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    private boolean[] whiteCastling;
    private boolean[] blackCastling;
    private boolean whitesTurn;
    private final Observer _observer;
    private ArrayList<Piece> enPassantAvailablePieces = new ArrayList<Piece>();
    private Position enPassantPosition;
    private int halfmove;
    private int fullmove;

    public Game(Observer observer) {
        board = new Board();
        createPieces(whitePieces, true);
        createPieces(blackPieces, false);
        whiteCastling = new boolean[]{true, true};
        blackCastling = new boolean[]{true, true};
        this._observer = observer;
        whitesTurn = true;
        halfmove = 0;
        fullmove = 1;

    }

    public Game(Observer observer, Board boardSquares, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces, boolean[] whiteCastling, boolean[] blackCastling, boolean whitesTurn, Position enPassantPosition, int halfmove, int fullmove){
        this._observer = observer;
        this.board = boardSquares;
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        this.whiteCastling = whiteCastling;
        this.blackCastling = blackCastling;
        this.whitesTurn = whitesTurn;
        this.enPassantPosition = enPassantPosition;
        this.halfmove = halfmove;
        this.fullmove = fullmove;
        if (nonNull(enPassantPosition)) {
            int rank = enPassantPosition.getRank() + (whitesTurn?1:-1);
            Position position = new Position(enPassantPosition.getFile(),rank);
            enableEnPassant(position, !whitesTurn);
        }
    }


    /**
     * Create all the piece objects and put them on the board in the correct position
     * 
     * @param pieces
     * @param isWhite
     */
    private void createPieces(ArrayList<Piece> pieces, boolean isWhite) {
        int rank = isWhite ? 6 : 1;
        for(int i = 0; i < 8; i++){
            pieces.add(new Piece(isWhite, PieceType.Pawn));
            addPiece(new Position(i,rank), pieces.get(i));
        }

        rank = isWhite ? 7 : 0;

        pieces.add(new Piece(isWhite, PieceType.Rook));
        pieces.add(new Piece(isWhite, PieceType.Knight));
        pieces.add(new Piece(isWhite, PieceType.Bishop));
        pieces.add(new Piece(isWhite, PieceType.Queen));
        pieces.add(new Piece(isWhite, PieceType.King));
        pieces.add(new Piece(isWhite, PieceType.Bishop));
        pieces.add(new Piece(isWhite, PieceType.Knight));
        pieces.add(new Piece(isWhite, PieceType.Rook));

        for(int i = 8; i < 16; i++) {
            addPiece(new Position(i-8, rank), pieces.get(i));
        }
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

    /**
     * Add the piece to the specific Square on the file and rank provided
     * 
     * @param position
     * @param piece
     */
    private void addPiece(Position position, Piece piece) {
        board.addPiece(position, piece);
    }

    /**
     *
     * @param previousPosition
     * @param newPosition
     * @param enPassant
     */
    private void movePieceOnBoard(Position previousPosition, Position newPosition, Position enPassant) {
        Piece removedPiece = board.getPiece(newPosition);
        Piece movedPiece = board.getPiece(previousPosition);
        if (nonNull(removedPiece)){
            getColouredPieces(removedPiece.getIsWhite()).remove(removedPiece);
        }
        board.removePiece(previousPosition);
        board.addPiece(newPosition,movedPiece);
        if(nonNull(enPassant)){
            getColouredPieces(board.getPiece(enPassant).getIsWhite()).remove(board.getPiece(enPassant));
            board.removePiece(enPassant);
        }
    }

    private ArrayList<Piece> getColouredPieces(boolean isWhite){
        return isWhite?whitePieces:blackPieces;
    }

    /**
     * Change the whitesTurn
     */
    private void nextTurn() {
        whitesTurn = !whitesTurn;
        fullmove+=whitesTurn?1:0;
    }

    /**
     * Move the piece from the previousPosition to the newPosition.
     * Also checking for en Passant and promotion. Then update the view
     * 
     * @param previousPosition
     * @param newPosition
     */
    public void movePieces(Position previousPosition, Position newPosition) {
        Piece piece = board.getPiece(previousPosition);

        // if en Passant occured, return as the checkForEnPassant function did everything necessary
        if(!checkForEnPassant(previousPosition, newPosition, piece)) {
            disableEnPassant();
            this.movePieceOnBoard(previousPosition, newPosition, null);
            // check if a pawn double moved and do what is needed
            if(piece.getPieceType() == PieceType.Pawn && abs(previousPosition.getRank() - newPosition.getRank()) == 2) {
                this.enPassantPosition = new Position(newPosition.getFile(),(newPosition.getRank()+previousPosition.getRank())/2);
                enableEnPassant(newPosition, piece.getIsWhite());
            }

            checkForPromotion(newPosition.getRank(), piece);
        }

        updateView();
        checkForCheck();
        nextTurn();




    }

    /**
     * Update any pawn that can en Passant next turn and then add those pieces that
     * are able to en passant on the next turn to the enPassantAvailablePieces list
     * 
     * @param position
     * @param isWhite
     */
    
    private void enableEnPassant(Position position, boolean isWhite) {
        int tempFile;
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempFile = position.getFile() + iFile;
            if (tempFile >= 0 && tempFile < 8){
                Piece piece = board.getPiece(new Position(tempFile,position.getRank()));
                if(nonNull(piece) && piece.getPieceType() == PieceType.Pawn && piece.getIsWhite() != isWhite){
                    piece.setEnPassantAvailableToTakeFile(position.getFile());
                    enPassantAvailablePieces.add(piece);
                }
            }
        }
    }

    /**
     * check to see if the piece being moved did En Passant
     * 
     * @param previousPosition
     * @param newPosition
     * @param piece
     * @return boolean indicating if enPassant available
     */
    private boolean checkForEnPassant(Position previousPosition, Position newPosition, Piece piece) {
        if (piece.getPieceType() == PieceType.Pawn) {
            if(enPassantAvailablePieces.contains(piece)) {
                if(Objects.equals(piece.getEnPassantAvailableToTakeFile(), newPosition.getFile())) {
                    this.movePieceOnBoard(previousPosition, newPosition, new Position(newPosition.getFile(), previousPosition.getRank()));
                    disableEnPassant();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove the availability to en Passant from all the necessary pieces
     */
    private void disableEnPassant() {
        for (Piece piece:enPassantAvailablePieces) {
            piece.setEnPassantAvailableToTakeFile(null);
        }
        this.enPassantPosition = null;
        enPassantAvailablePieces.clear();
    }

    /**
     * Check to see if a pawn has made it to the other side of the board. If so call the promotion function
     * 
     * @param newRank
     * @param piece
     */
    private void checkForPromotion(int newRank, Piece piece) {
        if(piece.getPieceType() != PieceType.Pawn) {
            return;
        }
        int rank = piece.getIsWhite() ? 0 : 7;
        if(newRank != rank){
            return;
        }
        piece.pawnPromotion(PieceType.Queen);
    }


    /**
     * Update the FEN and then update the view
     *
     */
    public void updateView() {
        _observer.update();
    }


    /**
     * Call chooseMove in Moves class
     * 
     * @param position
     * @param board
     * @return list of possible moves
     */
    public ArrayList<Position> chooseMove(Position position, Game board) {
        return Moves.chooseMove(position, board, true);
    }

    public boolean checkForCheck() {
        Piece kingPiece = !whitesTurn ? whitePieces.stream().filter(piece3 -> piece3.getPieceType() == PieceType.King).findFirst().orElse(null) : blackPieces.stream().filter(piece3 -> piece3.getPieceType() == PieceType.King).findFirst().orElse(null);
        ArrayList<Piece> checkingPieces = !whitesTurn ? blackPieces : whitePieces;
        setTempGame();
        if (Moves.checkForCheck(kingPiece, checkingPieces, this, false)){
            System.out.println("CHECK!!");
            return true;
        }
        return false;

    }


    public String getCompleteFEN() {
        return Fen.convertBoardToFen(exportBoardPositionForFEN(), whitesTurn, whiteCastling, blackCastling, enPassantPosition, halfmove, fullmove);
    }

    public Piece getPiece(Position position) {
        return board.getPieceClone(position);
    }

    public ArrayList<Piece> getCloneBlackPieces(){
        ArrayList<Piece> cloneBlackPieces = new ArrayList<>();
        for (Piece piece:blackPieces) {
            cloneBlackPieces.add(new Piece(piece));
        }
        return cloneBlackPieces;
    }

    public ArrayList<Piece> getCloneWhitePieces(){
        ArrayList<Piece> cloneWhitePieces = new ArrayList<>();
        for (Piece piece:whitePieces) {
            cloneWhitePieces.add(new Piece(piece));
        }
        return cloneWhitePieces;
    }

    public boolean getWhitesTurn() {
        return whitesTurn;
    }




    public void setTempGame(){
        this.tempBoard = new Board(board);
    }

    public Board getTempBoard(){
        setTempGame();
        return tempBoard;
    }

    public Piece getPieceFromTempBoard(Position position){
        return tempBoard.getPieceClone(position);
    }
}