package Controller;

import Model.Pieces.PieceType;
import Model.Position;

public class StockFishOutput {

    private final Position previousPosition;
    private final Position newPosition;
    private PieceType pieceType = null;

    public StockFishOutput(Position previousPosition, Position newPosition, PieceType pieceType){
        this.previousPosition = previousPosition;
        this.newPosition = newPosition;
        this.pieceType = pieceType;
    }

    public StockFishOutput(Position previousPosition, Position newPosition){
        this.previousPosition = previousPosition;
        this.newPosition = newPosition;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }

    public Position getNewPosition() {
        return newPosition;
    }

    public PieceType getPieceType() {
        return pieceType;
    }
}
