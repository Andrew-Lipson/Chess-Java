package Model;

import Model.pieces.*;
import Observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.util.Objects.isNull;

public class Board{

    private final BoardSquares boardSquares;
    private final Piece[] whitePieces = new Piece[16];
    private final Piece[] blackPieces = new Piece[16];
    private Boolean whitesTurn;
    private final Observer _observer;
    private ArrayList<Piece> enPassantAvailablePieces = new ArrayList<Piece>();

    public Board(Observer observer) {
        boardSquares = new BoardSquares();
        createPieces(whitePieces, true);
        createPieces(blackPieces, false);
        this._observer = observer;
        whitesTurn = true;
    }

    //Create all the piece objects and put them on the board in the correct position
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

    //Add the piece to the specific Square on the file and rank provided
    public void addPiece(Piece piece, Integer file, Integer rank){
        boardSquares.getSquare(file, rank).addPiece(piece);
    }


    // change the whitesTurn
    public void nextTurn(){
        whitesTurn = !whitesTurn;
    }

    //WILL CHANGE WITH FEN UPDATE
    public void movePieces(Integer fileMoveFrom, Integer rankMoveFrom, Integer fileMoveTo, Integer rankMoveTo){
        Piece piece = boardSquares.getSquare(fileMoveFrom,rankMoveFrom).getPiece();
        if(checkForEnPassant(fileMoveFrom, rankMoveFrom, fileMoveTo, rankMoveTo, piece)){
            return;
        }
        disableEnPassant();

        if(piece.getPieceType() == PieceType.Pawn && abs(rankMoveFrom-rankMoveTo)==2){
            enableEnPassant(fileMoveTo, rankMoveTo, piece.getIsWhite());
        }
        boardSquares.getSquare(fileMoveFrom,rankMoveFrom).removePiece();
        boardSquares.getSquare(fileMoveTo,rankMoveTo).addPiece(piece);
        checkForPromotion(rankMoveTo, piece);
        nextTurn();
        _observer.update(fileMoveFrom, rankMoveFrom, piece);
    }

    //Check if a pawn did a double move, then update any pawn that can en Passant next turn and then
    //add any pieces that are able to en passant on the next turn to the enPassantAvailablePieces list
    public void enableEnPassant(Integer file, Integer rank, Boolean isWhite){
        int tempFile;
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempFile = file+iFile;
            if (tempFile >= 0 && tempFile < 8){
                Piece piece = this.boardSquares.getSquare(tempFile,rank).getPiece();
                if(!isNull(piece) && piece.getPieceType()==PieceType.Pawn && piece.getIsWhite() != isWhite){
                    piece.setEnPassantAvailableToTakeFile(file);
                    enPassantAvailablePieces.add(piece);
                }
            }
        }
    }

    //WILL CHANGE WITH FEN UPDATE
    private Boolean checkForEnPassant(Integer fileMoveFrom, Integer rankMoveFrom, Integer fileMoveTo, Integer rankMoveTo, Piece piece){
        if (piece.getPieceType() == PieceType.Pawn){
            if(enPassantAvailablePieces.contains(piece)){
                if(Objects.equals(piece.getEnPassantAvailableToTakeFile(), fileMoveTo)){
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

    //remove the availability to en Passant from all the necessary pieces
    private void disableEnPassant(){
        for (Piece piece:enPassantAvailablePieces) {
            piece.setEnPassantAvailableToTakeFile(null);
        }
        enPassantAvailablePieces.clear();
    }

    public void checkForPromotion(int newRank, Piece piece){
        if(piece.getPieceType()!=PieceType.Pawn){
            return;
        }
        int rank=piece.getIsWhite()?0:7;
        if(newRank!=rank){
            return;
        }
        piece.pawnPromotion(PieceType.Queen);
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
}
