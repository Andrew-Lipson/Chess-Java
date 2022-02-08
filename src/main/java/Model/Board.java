package Model;

import Model.pieces.*;
import Observer.Observer;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.util.Objects.isNull;

public class Board{

    private BoardSquares boardSquares;
    private Piece[] whitePieces = new Piece[16];
    private Piece[] blackPieces = new Piece[16];
    private Boolean whitesTurn;
    private Observer _observer;
    private ArrayList<Pawn> enPassantAvailablePieces = new ArrayList<>();

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
            pieces[i] = new Pawn(isWhite, PieceType.Pawn);
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
        checkForEnPassant(fileMoveFrom, rankMoveFrom, fileMoveTo, rankMoveTo, piece);
        disableEnPassant();

        if(piece.getPieceType() == PieceType.Pawn && abs(rankMoveFrom-rankMoveTo)==2){
            enableEnPassant(fileMoveTo, rankMoveTo, piece.getIsWhite());
        }
        boardSquares.getSquare(fileMoveFrom,rankMoveFrom).removePiece();
        boardSquares.getSquare(fileMoveTo,rankMoveTo).addPiece(piece);
        nextTurn();
        _observer.update(fileMoveFrom, rankMoveFrom, piece);
    }

    public void enableEnPassant(Integer file, Integer rank, Boolean isWhite){
        Integer tempFile;
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempFile = file+iFile;
            if (tempFile >= 0 && tempFile < 8){
                Piece piece = this.boardSquares.getSquare(tempFile,rank).getPiece();
                if(!isNull(piece) && piece.getPieceType()==PieceType.Pawn && piece.getIsWhite() != isWhite){
                    Pawn pawn = (Pawn) piece;
                    pawn.setEnPassantAvailableToTakeFile(file);
                    enPassantAvailablePieces.add(pawn);
                }
            }
        }
    }

    private Boolean checkForEnPassant(Integer fileMoveFrom, Integer rankMoveFrom, Integer fileMoveTo, Integer rankMoveTo, Piece piece){
        if (piece.getPieceType() == PieceType.Pawn){
            Pawn pawn = (Pawn) piece;
            if(enPassantAvailablePieces.contains(pawn)){
                if(pawn.getEnPassantAvailableToTakeFile()==fileMoveTo){
                    boardSquares.getSquare(fileMoveFrom,rankMoveFrom).removePiece();
                    boardSquares.getSquare(fileMoveTo,rankMoveFrom).removePiece();
                    boardSquares.getSquare(fileMoveTo,rankMoveTo).addPiece(piece);
                    nextTurn();
                    _observer.update(fileMoveFrom, rankMoveFrom, piece);
                    _observer.update(fileMoveTo,rankMoveFrom, piece);
                    disableEnPassant();
                    return true;
                }
            }
        }
        return false;
    }

    private void disableEnPassant(){
        for (Pawn pawn:enPassantAvailablePieces) {
            pawn.setEnPassantAvailableToTakeFile(null);
        }
        enPassantAvailablePieces.clear();
    }
}
