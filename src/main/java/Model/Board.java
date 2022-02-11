package Model;

import Model.pieces.*;
import Observer.Observer;

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
            addPiece(new Position(i,rank),pieces[i]);
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
            addPiece(new Position(i-8,rank),pieces[i]);
        }

    }

    //Add the piece to the specific Square on the file and rank provided
    private void addPiece(Position position, Piece piece){
        boardSquares.addPiece(position, piece);
    }

    private void removePiece(Position position){
        boardSquares.removePiece(position);
    }


    // change the whitesTurn
    private void nextTurn(){
        whitesTurn = !whitesTurn;
    }

    //WILL CHANGE WITH FEN UPDATE
    public void movePieces(Position previousPosition, Position newPosition){
        Piece piece = boardSquares.getPiece(previousPosition);
        if(checkForEnPassant(previousPosition, newPosition, piece)){
            return;
        }
        disableEnPassant();

        if(piece.getPieceType() == PieceType.Pawn && abs(previousPosition.getRank()- newPosition.getRank())==2){
            enableEnPassant(newPosition, piece.getIsWhite());
        }
        this.removePiece(previousPosition);
        this.addPiece(newPosition,piece);
        checkForPromotion(newPosition.getRank(), piece);
        nextTurn();
        _observer.update(previousPosition, piece);
    }

    //Check if a pawn did a double move, then update any pawn that can en Passant next turn and then
    //add any pieces that are able to en passant on the next turn to the enPassantAvailablePieces list
    private void enableEnPassant(Position position, Boolean isWhite){
        int tempFile;
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempFile = position.getFile()+iFile;
            if (tempFile >= 0 && tempFile < 8){
                Piece piece = this.getPiece(new Position(tempFile,position.getRank()));
                if(!isNull(piece) && piece.getPieceType()==PieceType.Pawn && piece.getIsWhite() != isWhite){
                    piece.setEnPassantAvailableToTakeFile(position.getFile());
                    enPassantAvailablePieces.add(piece);
                }
            }
        }
    }

    //WILL CHANGE WITH FEN UPDATE
    private Boolean checkForEnPassant(Position previousPosition, Position newPosition, Piece piece){
        if (piece.getPieceType() == PieceType.Pawn){
            if(enPassantAvailablePieces.contains(piece)){
                if(Objects.equals(piece.getEnPassantAvailableToTakeFile(), newPosition.getFile())){
                    this.removePiece(previousPosition);
                    this.removePiece(new Position(newPosition.getFile(), previousPosition.getFile()));
                    this.addPiece(newPosition, piece);
                    nextTurn();
                    _observer.update(previousPosition, piece);
                    _observer.update(new Position(newPosition.getFile(), previousPosition.getFile()), piece);
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

    private void checkForPromotion(int newRank, Piece piece){
        if(piece.getPieceType()!=PieceType.Pawn){
            return;
        }
        int rank=piece.getIsWhite()?0:7;
        if(newRank!=rank){
            return;
        }
        piece.pawnPromotion(PieceType.Queen);
    }

    public Piece getPiece(Position position){
        return boardSquares.getPiece(position);
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
