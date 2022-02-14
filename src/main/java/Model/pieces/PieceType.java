package Model.pieces;

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

    public String getFenRepresentation(boolean isWhite) {
        return isWhite? this.fenRepresentation.toUpperCase():this.fenRepresentation;
    }
}
