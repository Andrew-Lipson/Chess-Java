package Model.pieces;

import Model.Square;
import javafx.scene.image.ImageView;


public abstract class Piece {
    private ImageView imageview;
    private Boolean white;
    private Integer file;
    private Integer rank;
    private String pieceType;


    public Piece(Boolean white){

        this.white = white;
        this.pieceType = this.getClass().getSimpleName();

    }


    public void addPosition(Square position){
        this.file = position.getFile();
        this.rank = position.getRank();
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
