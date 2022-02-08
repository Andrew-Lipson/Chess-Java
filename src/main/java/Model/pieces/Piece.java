package Model.pieces;


public class Piece {
    private Boolean isWhite;
    private Integer file;
    private Integer rank;
    private PieceType pieceType;


    public Piece(Boolean isWhite, PieceType pieceType){

        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }


    public void addPosition(Integer file, Integer rank){
        this.file = file;
        this.rank = rank;
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

    public Boolean isKing(){
        return false;
    }


}
