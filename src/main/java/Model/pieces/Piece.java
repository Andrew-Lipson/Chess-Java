package Model.pieces;

import Model.Square;


public abstract class Piece {
    private Boolean white;
    private Integer file;
    private Integer rank;
    private String pieceType;


    public Piece(Boolean white){

        this.white = white;
        this.pieceType = this.getClass().getSimpleName();
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
        return pieceType;
    }

    public Boolean getWhite() {
        return white;
    }

    public Boolean isKing(){
        return false;
    }

}
