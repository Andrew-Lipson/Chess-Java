package Model.Pieces;

import Model.Utilities.Position;

public class Piece {

    private final boolean isWhite;
    private Position position;
    private PieceType pieceType;
    private Integer enPassantAvailableToTakeFile = null;

    /**
     * Constructs a Piece
     *
     * @param isWhite Is the Piece white
     * @param pieceType Is the PieceType of the Piece
     */
    public Piece(boolean isWhite, PieceType pieceType) {
        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }

    /**
     * Use to Clone a previous Piece
     * 
     * @param piece The piece that is required to clone
     */
    public Piece(Piece piece) {
        this.isWhite = piece.getIsWhite();
        this.position = piece.getPosition();
        this.pieceType = piece.getPieceType();
        this.enPassantAvailableToTakeFile = piece.getEnPassantAvailableToTakeFile();
    }

    /**
     * add the file and rank position to the Piece
     * 
     * @param position The Position that Piece is on
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Set the enPassantAvailableToTakeFile to the File that the piece will move to after the en Passant
     * 
     * @param file The file that the pawn will move to if it En Passants
     */
    public void setEnPassantAvailableToTakeFile(Integer file) {
        this.enPassantAvailableToTakeFile = file;
    }

    /**
     * Change the PieceType for when a Pawn get promoted
     * 
     * @param pieceType Is the PieceType the pawn will promote too
     */
    public void pawnPromotion(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    /**
     * returns if the Piece is positioned on a lightSquare or not
     *
     * @return true if the Piece is on a lightSquare
     */
    public boolean isOnLightSquares() {
        return this.position.isALightSquares();
    }

    public Position getPosition() {
        return this.position;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public String getFenRepresentation() {
        return this.pieceType.getFenRepresentation(this.isWhite);
    }

    public boolean getIsWhite() {
        return this.isWhite;
    }

    public Integer getEnPassantAvailableToTakeFile() {
        return this.enPassantAvailableToTakeFile;
    }

}