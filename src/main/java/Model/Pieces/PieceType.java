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

    public String getFenRepresentation(boolean isWhite) {
        return isWhite ? this.fenRepresentation.toUpperCase() : this.fenRepresentation;
    }

    public static PieceType getPieceType(String fenRep){
        return Arrays.stream(PieceType.values()).filter(pt -> pt.fenRepresentation.equals(fenRep)).findFirst().orElse(null);
    }
}
