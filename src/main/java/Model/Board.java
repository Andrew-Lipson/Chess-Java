package Model;

import Model.pieces.*;

public class Board{

    private BoardSquares boardSquares;
    private Piece[] whitePieces = new Piece[16];
    private Piece[] blackPieces = new Piece[16];
    //private Group root = new Group();

    public Board() {
        boardSquares = new BoardSquares();
        createPieces(whitePieces, true);
        createPieces(blackPieces, false);
    }

    private void createPieces( Piece[] pieces, Boolean white){
        int rank = white?6:1;
        for(int i = 0;i<8;i++){
            pieces[i] = new Pawn(white);
            addPiece(pieces[i],rank,i );
        }

        rank = white?7:0;

        for(int i = 8;i<16;i+=7) {
            pieces[i] = new Rook(white);
        }
        for(int i = 9;i<16;i+=5) {
            pieces[i] = new Knight(white);
        }
        for(int i = 10;i<16;i+=3) {
            pieces[i] = new Bishop(white);
        }
        pieces[11] = new Queen(white);
        pieces[12] = new King(white);

        for(int i = 8;i<16;i++) {
            addPiece(pieces[i],rank,i-8);
        }

    }

    public void addPiece(Piece piece, Integer rank, Integer file){
        boardSquares.getSquare(rank, file).addPiece(piece);
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
}
