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


    //Add a Piece to this Square
    public void addPiece(Piece piece){
        this.piece = piece;
        this.piece.addPosition(this.file,this.rank);
    }

    //Remove a Piece from this Square
    public void removePiece(){
        this.piece =  null;
    }

    public Piece getPiece() {
        return this.piece;
    }

//    public Integer getFile(){
//        return this.file;
//    }
//
//    public Integer getRank(){
//        return this.rank;
//    }
}
