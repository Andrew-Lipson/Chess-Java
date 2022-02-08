package Model.pieces;

public class Pawn extends Piece{

    private Integer enPassantAvailableToTakeFile = null;

    public Pawn(Boolean isWhite, PieceType pieceType) {
        super(isWhite, pieceType);
    }

    public void setEnPassantAvailableToTakeFile(Integer file){
        this.enPassantAvailableToTakeFile = file;
    }

    public Integer getEnPassantAvailableToTakeFile(){
        return enPassantAvailableToTakeFile;
    }
}
