package Model.Utilities;

import Model.Game;
import Model.Position;
import Model.Pieces.Piece;
import Model.Pieces.PieceType;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Check {


    /**
     *
     * @param game
     * @param whitesTurn
     * @return
     */
    public static boolean isTheKingUnderAttack(Game game, boolean whitesTurn){
        ArrayList<Piece> attackingPieces = game.getClonedColouredPieces(!whitesTurn);
        Piece kingPiece = game.getClonedColouredPieces(whitesTurn).stream().filter(piece3 -> piece3.getPieceType() == PieceType.King).findFirst().orElse(null);

        if (isNull(kingPiece)){
            return false;
        }
        Position kingPosition = kingPiece.getPosition();
        for (Piece piece:attackingPieces) {
            ArrayList<Position> possibleMoves = Moves.getAllMovesIncludingIllegalMoves(piece.getPosition(), game);
            if (possibleMoves.stream().anyMatch(position -> position.getFile()==kingPosition.getFile() && position.getRank()==kingPosition.getRank())){
                return true;
            }
        }
        return false;
    }


    /**
     *
     * @param game
     * @param whitesTurn
     * @return
     */
    public static boolean isThereALegalMove(Game game, boolean whitesTurn){
        ArrayList<Piece> defendingPieces = game.getClonedColouredPieces(whitesTurn);
        for (Piece piece:defendingPieces) {
            if (game.getLegalMoves(piece.getPosition()).size()!=0){
                return false;
            }
        }
        return true;
    }

}
