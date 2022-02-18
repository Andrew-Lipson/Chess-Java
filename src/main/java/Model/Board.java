package Model;

import Model.pieces.Piece;

import static java.util.Objects.isNull;

public class Board {

    Piece[][] squares = new Piece[8][8];

    public Board() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squares[file][rank] = null;
            }
        }
    }

    /**
     * Return a Square from the specific File and Rank
     * 
     * @param position
     * @return the piece
     */
    public Piece getPiece(Position position) {
        return squares[position.getFile()][position.getRank()];
    }

    /**
     * Create a new Piece instance instead of returning the original instance
     * 
     * @param position
     * @return a clone of the piece on the position, or null if no piece found
     */
    public Piece getPieceClone(Position position) {
        if (isNull(getPiece(position))) {
            return null;
        }
        return new Piece(getPiece(position));
    }

    /**
     * remove the piece from the square at position position
     * 
     * @param position
     */
    public void removePiece(Position position) {
        Piece piece = squares[position.getFile()][position.getRank()];
        if(!isNull(piece)) {
            piece.setPosition(null);
            squares[position.getFile()][position.getRank()] = null;
        }

    }

    /**
     * Add a piece to the square at position position
     * 
     * @param position
     * @param piece
     */
    public void addPiece(Position position, Piece piece) {
        squares[position.getFile()][position.getRank()] = piece;
        piece.setPosition(position);
    }

    /**
     * Returns an array of Pieces all on rank 'rank'. This will be used for creating the FEN
     * @param rank
     * @return all pieces on rank
     */
    public Piece[] getRankPiece(int rank) {
        Piece[] rankPiece = new Piece[8];
        for (int file = 0; file < 8; file++) {
            rankPiece[file] = getPieceClone(new Position(file,rank));
        }
        return rankPiece;
    }
}
