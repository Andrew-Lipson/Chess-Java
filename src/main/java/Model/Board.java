package Model;

import Model.Pieces.Piece;

import static java.util.Objects.isNull;

public class Board {

    Piece[][] board = new Piece[8][8];

    public Board() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                board[file][rank] = null;
            }
        }
    }


    /**
     * Return a Square from the specific File and Rank
     * 
     * @param position The position of the piece to get
     * @return the piece
     */
    public Piece getPiece(Position position) {
        return board[position.getRank()][position.getFile()];
    }

    /**
     * Create a new Piece instance instead of returning the original instance
     * 
     * @param position The position of the piece to get
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
     * @param position The position of the piece to remove
     */
    public void removePiece(Position position) {
        Piece piece = board[position.getRank()][position.getFile()];
        if(!isNull(piece)) {
            piece.setPosition(null);
            board[position.getRank()][position.getFile()] = null;
        }

    }

    /**
     * Add a piece to the square at position position
     * 
     * @param position The position of the piece to add
     * @param piece The Piece that is being added
     */
    public void addPiece(Position position, Piece piece) {
        board[position.getRank()][position.getFile()] = piece;
        piece.setPosition(position);
    }

    /**
     * Returns an array of Pieces all on rank 'rank'. This will be used for creating the FEN
     *
     * @param rank the rank of the board that is needed
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
