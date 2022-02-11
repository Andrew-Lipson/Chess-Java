package Model;

import Model.pieces.Piece;

import static java.util.Objects.isNull;

public class BoardSquares {

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

    public void removePiece(Position position){
        Piece piece = getPiece(position);
        if(!isNull(piece)){
            piece.addPosition(null);
            squares[position.getFile()][position.getRank()] = null;
        }

    }

    public void addPiece(Position position, Piece piece){
        squares[position.getFile()][position.getRank()] = piece;
        piece.addPosition(position);
    }



}
