package Model;

import Model.pieces.Piece;

import static java.util.Objects.isNull;

public class BoardSquares{

    Piece[][] squares = new Piece[8][8];

    public BoardSquares() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squares[file][rank] = null;
            }
        }
    }

    // Return a Square from the specific File and Rank
    public Piece getPiece(Position position){
        return squares[position.getFile()][position.getRank()];
    }

    // Create a new Piece instance instead of returning the original instance
    public Piece getPieceClone(Position position){
        if (isNull(getPiece(position))){
            return null;
        }
        return new Piece(getPiece(position));
    }

    // remove the piece from the square at position position
    public void removePiece(Position position){
        Piece piece = squares[position.getFile()][position.getRank()];
        if(!isNull(piece)){
            piece.setPosition(null);
            squares[position.getFile()][position.getRank()] = null;
        }

    }

    // adda piece to the square at position position
    public void addPiece(Position position, Piece piece){
        squares[position.getFile()][position.getRank()] = piece;
        piece.setPosition(position);
    }

    // return an array of Pieces all on rank 'rank'. This will be used for creating the FEN
    public Piece[] getRankPiece(int rank){
        Piece[] rankPiece = new Piece[8];
        for (int file = 0; file < 8; file++) {
            rankPiece[file] = getPieceClone(new Position(file,rank));
        }
        return rankPiece;
    }
}
