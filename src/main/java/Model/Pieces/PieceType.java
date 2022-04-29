package Model.Pieces;

import java.util.Arrays;

public enum PieceType {
    King("k"),
    Queen("q"),
    Rook("r"),
    Knight("n"),
    Bishop("b"),
    Pawn("p");

    public final String fenRepresentation;

    private PieceType(String fenRepresentation) {
        this.fenRepresentation = fenRepresentation;
    }

    /**
     * Get the fenREpresentation of the PieceType, including being capital if it's White
     *
     * @param isWhite Is the Piece White
     * @return the Fen representation of the PieceType
     */
    public String getFenRepresentation(boolean isWhite) {
        return isWhite ? this.fenRepresentation.toUpperCase() : this.fenRepresentation;
    }

    /**
     * From the Fen Representation to return the PieceType
     *
     * @param fenRep that want the PieceType of
     * @return PieceType corresponding to the fenRep
     */
    public static PieceType getPieceType(String fenRep) {
        return Arrays.stream(PieceType.values()).filter(pt -> pt.fenRepresentation.equals(fenRep)).findFirst().orElse(null);
    }
}
