package Model.Utilities;

import Model.Game;
import Model.Pieces.Piece;
import Model.Pieces.PieceType;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public final class Check {

    /**
     * With the Game provided, is the king of colour whitesTurn under attack
     *
     * @param game The game that is being used
     * @param whitesTurn the colour of the king that might be under attack
     * @return true if the king is under attack, false otherwise
     */
    public static boolean isTheKingUnderAttack(Game game, boolean whitesTurn) {
        ArrayList<Piece> attackingPieces = game.getClonedColouredPieces(!whitesTurn);
        Piece kingPiece = game.getClonedColouredPieces(whitesTurn).stream().filter(piece3 -> piece3.getPieceType() == PieceType.King).findFirst().orElse(null);

        if (isNull(kingPiece)) {
            return false;
        }
        Position kingPosition = kingPiece.getPosition();
        for (Piece piece:attackingPieces) {
            ArrayList<Position> possibleMoves = Moves.getAllMovesIncludingIllegalMoves(piece.getPosition(), game);
            if (possibleMoves.stream().anyMatch(position -> position.getFile()==kingPosition.getFile() && position.getRank()==kingPosition.getRank())) {
                return true;
            }
        }
        return false;
    }


    /**
     * With the Game provided, is there any legal moves for the whitesTurn pieces
     *
     * @param game The game that is being used
     * @param whitesTurn Is it white's turn
     * @return true if there is a legal move, false otherwise
     */
    public static boolean isThereALegalMove(Game game, boolean whitesTurn) {
        ArrayList<Piece> defendingPieces = game.getClonedColouredPieces(whitesTurn);
        for (Piece piece:defendingPieces) {
            if (game.getLegalMoves(piece.getPosition()).size()!=0) {
                return false;
            }
        }
        return true;
    }

}
