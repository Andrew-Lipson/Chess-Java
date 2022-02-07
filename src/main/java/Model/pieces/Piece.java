package Model.pieces;

import Model.Square;

public class Piece {
    private Boolean isWhite;
    private Integer file;
    private Integer rank;
    private PieceType pieceType;


    public Piece(Boolean isWhite, PieceType pieceType){

        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }


    public void addPosition(Square square){
        this.file = square.getFile();
        this.rank = square.getRank();
    }

    public Integer getFile() {
        return file;
    }

    public Integer getRank() {
        return rank;
    }

    public String getPieceType() {
        return pieceType.toString();
    }

    public Boolean getIsWhite() {
        return isWhite;
    }

    public Boolean isKing(){
        return false;
    }

}
