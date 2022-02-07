package Model.pieces;

public class King extends Piece{

    public King(Boolean white){

        super(white);
        }

    @Override
    public Boolean isKing(){
        return true;
    }
}
