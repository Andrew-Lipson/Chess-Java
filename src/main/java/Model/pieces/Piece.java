package Model.pieces;


public class Piece {
    private Boolean isWhite;
    private Integer file;
    private Integer rank;
    private PieceType pieceType;
    private Integer enPassantAvailableToTakeFile = null;

    public Piece(Boolean isWhite, PieceType pieceType){

        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }

    //add the file and rank position to the Piece
    public void addPosition(Integer file, Integer rank){
        this.file = file;
        this.rank = rank;
    }

    //Set the enPassantAvailableToTakeFile to the File that the piece will move to after the en Passant
    public void setEnPassantAvailableToTakeFile(Integer file){
        this.enPassantAvailableToTakeFile = file;
    }


    // Change the PieceType for when a Pawn get promoted
    public void pawnPromotion(PieceType pieceType){
        this.pieceType = pieceType;
    }

    public Integer getFile() {
        return file;
    }

    public Integer getRank() {
        return rank;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Boolean getIsWhite() {
        return isWhite;
    }

    public Integer getEnPassantAvailableToTakeFile(){
        return enPassantAvailableToTakeFile;
    }
}
