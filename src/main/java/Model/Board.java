package Model;

import Model.pieces.*;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.util.Objects.isNull;

public class Board{

    private final BoardSquares boardSquares;
    private final Piece[] whitePieces = new Piece[16];
    private final Piece[] blackPieces = new Piece[16];
    private boolean whitesTurn;
    private final Observer _observer;
    private ArrayList<Piece> enPassantAvailablePieces = new ArrayList<Piece>();
    private FEN fen;

    public Board(Observer observer) {
        boardSquares = new BoardSquares();
        createPieces(whitePieces, true);
        createPieces(blackPieces, false);
        this._observer = observer;
        fen = new FEN(createFirstFENPosition());
        whitesTurn = true;
    }



    //Create all the piece objects and put them on the board in the correct position
    private void createPieces( Piece[] pieces, boolean isWhite){
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

    // basically create a copy of boardSquares to help create the first FEN
    private Piece[][] createFirstFENPosition(){
        Piece[][] piece2DArray = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++) {
            piece2DArray[rank] = boardSquares.getRankPiece(rank);
        }
        return piece2DArray;
    }

    //Add the piece to the specific Square on the file and rank provided
    private void addPiece(Position position, Piece piece){
        boardSquares.addPiece(position, piece);
    }

    //remove the piece on the specific position
    private void removePiece(Position position){
        boardSquares.removePiece(position);
    }


    // change the whitesTurn
    private void nextTurn(){
        whitesTurn = !whitesTurn;
    }

    // move the piece from the previousPosition to the newPosition.
    // also checking for en Passant and promotion. Then update the view
    public void movePieces(Position previousPosition, Position newPosition){
        fen.setEnPassantPiece(null);
        Piece piece = boardSquares.getPiece(previousPosition);

        // if en Passant occured, return as the checkForEnPassant function did everything necessary
        if(checkForEnPassant(previousPosition, newPosition, piece)){
            return;
        }

        disableEnPassant();

        // check if a pawn double moved and do what is needed
        if(piece.getPieceType() == PieceType.Pawn && abs(previousPosition.getRank()- newPosition.getRank())==2){
            fen.setEnPassantPiece(boardSquares.getPiece(new Position(previousPosition.getFile(),previousPosition.getRank())));
            enableEnPassant(newPosition, piece.getIsWhite());
        }
        this.removePiece(previousPosition);
        this.addPiece(newPosition,piece);
        checkForPromotion(newPosition.getRank(), piece);


        updateView(newPosition, previousPosition);
        nextTurn();

    }



    //update any pawn that can en Passant next turn and then add those pieces that
    // are able to en passant on the next turn to the enPassantAvailablePieces list
    private void enableEnPassant(Position position, boolean isWhite){
        int tempFile;
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempFile = position.getFile()+iFile;
            if (tempFile >= 0 && tempFile < 8){
                Piece piece = boardSquares.getPiece(new Position(tempFile,position.getRank()));
                if(!isNull(piece) && piece.getPieceType()==PieceType.Pawn && piece.getIsWhite() != isWhite){
                    piece.setEnPassantAvailableToTakeFile(position.getFile());
                    enPassantAvailablePieces.add(piece);
                }
            }
        }
    }

    //check to see if the piece being moved did En Passant
    private boolean checkForEnPassant(Position previousPosition, Position newPosition, Piece piece){
        if (piece.getPieceType() == PieceType.Pawn){
            if(enPassantAvailablePieces.contains(piece)){
                if(Objects.equals(piece.getEnPassantAvailableToTakeFile(), newPosition.getFile())){
                    this.removePiece(previousPosition);
                    this.removePiece(new Position(newPosition.getFile(), previousPosition.getRank()));
                    this.addPiece(newPosition, piece);

                    updateView(newPosition, previousPosition);
                    nextTurn();
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

    //check to see if a pawn has made it to the other side of the board. If so call the promotion function
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


    //update the FEN and then update the view
    public void updateView(Position newPosition, Position previousPosition){
        fen.updateFENPosition(boardSquares.getRankPiece(newPosition.getRank()), newPosition.getRank());
        if (previousPosition.getRank()!= newPosition.getRank()){
            fen.updateFENPosition(boardSquares.getRankPiece(previousPosition.getRank()), previousPosition.getRank());
        }
        _observer.update();
    }


    //call chooseMove in Moves class
    public ArrayList<Position> chooseMove(Position position, boolean isWhite, Board board, PieceType pieceType){
        return Moves.chooseMove(position, isWhite, board, pieceType);
    }

    public String getCompleteFEN(){
        return fen.createCompleteFEN();
    }


    public Piece getPiece(Position position){
        return boardSquares.getPieceClone(position);
    }


    public boolean getWhitesTurn() {
        return whitesTurn;
    }

}
