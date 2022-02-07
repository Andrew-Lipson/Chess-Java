package Model;

import Model.pieces.*;

public class Square {
    private final Integer rank;
    private final Integer file;
    private Piece piece;


    public Square(Integer file, Integer rank) {
        this.rank = rank;
        this.file = file;
        this.piece = null;
    }

    public Integer getFile(){
        return this.file;
    }

    public Integer getRank(){
        return this.rank;
    }

    public void addPiece(Piece piece){
        this.piece = piece;
        this.piece.addPosition(this);
    }

    public void removePiece(){
        this.piece =  null;
    }
}
