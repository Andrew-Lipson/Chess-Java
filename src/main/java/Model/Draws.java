package Model;

import Model.Pieces.Piece;
import Model.Pieces.PieceType;

import java.util.ArrayList;
import java.util.HashMap;

public class Draws {

    private HashMap<String, Integer> hashmap;

    public Draws(){
        hashmap = new HashMap<>();
    }

    public boolean isThreefoldRepetition(String fenForRepetitionCheck, int halfMove) {
        if (halfMove==0){
            hashmap.clear();
            hashmap.put(fenForRepetitionCheck,1);
            return false;
        }
        if (hashmap.containsKey(fenForRepetitionCheck)){
            int tempValue = hashmap.get(fenForRepetitionCheck) + 1;
            if (tempValue == 3){
                return true;
            } else{
                hashmap.replace(fenForRepetitionCheck,tempValue);
            }
        } else{
           hashmap.put(fenForRepetitionCheck,1);
        }
        return false;
    }

    enum FinalMaterial {
        KING,
        KING_AND_BISHOP,
        KING_AND_KNIGHT,
        OTHER
    }

    /**
     * Check to see if it's a draw by insufficient material
     *
     * @param whitePieces the white Pieces on the board
     * @param blackPieces the black pieces on the board
     * @return true if it's a draw via insufficient material, false otherwise
     */
    public boolean isThereInsufficientMaterial(ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces) {

        FinalMaterial whiteValue = getRemainingPiecesValue(whitePieces);
        FinalMaterial blackValue = getRemainingPiecesValue(blackPieces);
        
        if (whiteValue==FinalMaterial.OTHER || blackValue==FinalMaterial.OTHER) {
            return false;
        } else if (whiteValue==FinalMaterial.KING || blackValue==FinalMaterial.KING) {
            return true;
        } else if (whiteValue==FinalMaterial.KING_AND_BISHOP && blackValue==FinalMaterial.KING_AND_BISHOP) {
                Piece whiteBishop = whitePieces.stream().filter(piece -> piece.getPieceType().equals(PieceType.Bishop)).findFirst().orElse(null);
                Piece blackBishop = blackPieces.stream().filter(piece -> piece.getPieceType().equals(PieceType.Bishop)).findFirst().orElse(null);
                return whiteBishop.isOnLightSquares() == blackBishop.isOnLightSquares();
        }

        return false;
    }

    /**
     * return a value depending on the remaining pieces:
     *
     * @param colouredPieces pieces all relating to a specific colour
     * @return FinalMaterial depending on what pieces are remaining
     */
    private FinalMaterial getRemainingPiecesValue(ArrayList<Piece> colouredPieces) {
        if (colouredPieces.size()>2) {
            return FinalMaterial.OTHER;
        } else if (colouredPieces.size()==1) {
            return FinalMaterial.KING;
        } else {
            Piece nonKingPiece = colouredPieces.stream().filter(piece -> !piece.getPieceType().equals(PieceType.King)).findFirst().orElse(null);
            if (nonKingPiece.getPieceType()==PieceType.Bishop) {
                return FinalMaterial.KING_AND_BISHOP;
            } else if(nonKingPiece.getPieceType()==PieceType.Knight) {
                return FinalMaterial.KING_AND_KNIGHT;
            } else {
                return FinalMaterial.OTHER;
            }
        }
    }
}
