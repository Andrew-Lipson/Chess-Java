package Model;

import Model.pieces.*;
import Observer.Observer;

public class Board{

    private BoardSquares boardSquares;
    private Piece[] whitePieces = new Piece[16];
    private Piece[] blackPieces = new Piece[16];
    private Boolean whitesTurn;
    private Observer _observer;

    public Board(Observer injected) {
        boardSquares = new BoardSquares();
        createPieces(whitePieces, true);
        createPieces(blackPieces, false);
        this._observer = injected;
        whitesTurn = true;
    }

    private void createPieces( Piece[] pieces, Boolean isWhite){
        int rank = isWhite?6:1;
        for(int i = 0;i<8;i++){
            pieces[i] = new Piece(isWhite, PieceType.Pawn);
            addPiece(pieces[i], i, rank);
        }

        rank = isWhite?7:0;

        for(int i = 8;i<16;i+=7) {
            pieces[i] = new Piece(isWhite, PieceType.Rook);
        }
        for(int i = 9;i<16;i+=5) {
            pieces[i] = new Piece(isWhite, PieceType.Knight);
        }
        for(int i = 10;i<16;i+=3) {
            pieces[i] = new Piece(isWhite, PieceType.Bishop);
        }
        pieces[11] = new Piece(isWhite, PieceType.Queen);
        pieces[12] = new Piece(isWhite, PieceType.King);

        for(int i = 8;i<16;i++) {
            addPiece(pieces[i],i-8, rank);
        }

    }

    public void addPiece(Piece piece, Integer file, Integer rank){
        boardSquares.getSquare(file, rank).addPiece(piece);
    }

    public BoardSquares getBoardSquares(){
        return boardSquares;
    }

    public Piece[] getBlackPieces() {
        return blackPieces;
    }

    public Piece[] getWhitePieces() {
        return whitePieces;
    }

    public Boolean getWhitesTurn() {
        return whitesTurn;
    }

    public void nextTurn(){
        whitesTurn = !whitesTurn;
    }

    public void movePieces(Integer fileMoveFrom, Integer rankMoveFrom, Integer fileMoveTo, Integer rankMoveTo){
        Piece piece = boardSquares.getSquare(fileMoveFrom,rankMoveFrom).getPiece();
        boardSquares.getSquare(fileMoveFrom,rankMoveFrom).removePiece();
        boardSquares.getSquare(fileMoveTo,rankMoveTo).addPiece(piece);
        nextTurn();
        _observer.update(fileMoveFrom, rankMoveFrom, piece);
    }
}
