package Controller.Engine;

import Model.Pieces.PieceType;
import Model.Utilities.Position;

public class StockFishOutput {

    private final Position previousPosition;
    private final Position newPosition;
    private PieceType pieceType = null;

    /**
     * Constructs a StockFishOutput with the Previous Position, New Position, and PieceType for Promotion
     *
     * @param previousPosition Position that the Piece will move from
     * @param newPosition Position that the Piece will move too
     * @param pieceType On promotion, what the Piece will be promoted too
     */
    public StockFishOutput(Position previousPosition, Position newPosition, PieceType pieceType) {
        this.previousPosition = previousPosition;
        this.newPosition = newPosition;
        this.pieceType = pieceType;
    }

    /**
     * Constructs a StockFishOutput with the Previous Position, New Position
     *
     * @param previousPosition Position that the Piece will move from
     * @param newPosition Position that the Piece will move too
     */
    public StockFishOutput(Position previousPosition, Position newPosition) {
        this.previousPosition = previousPosition;
        this.newPosition = newPosition;
    }

    public Position getPreviousPosition() {
        return this.previousPosition;
    }

    public Position getNewPosition() {
        return this.newPosition;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }
}
