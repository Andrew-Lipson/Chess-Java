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

    public boolean isThereInsufficientMaterial(ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces){

        int whiteValue = getRemainingPiecesValue(whitePieces);
        int blackValue = getRemainingPiecesValue(blackPieces);
        
        if (whiteValue==-1 || blackValue==-1){
            return false;
        } else if (whiteValue==0 || blackValue==0){
            return true;
        } else if (whiteValue==1 || blackValue==1){
                Piece whiteBishop = whitePieces.stream().filter(piece -> piece.getPieceType().equals(PieceType.Bishop)).findFirst().orElse(null);
                Piece blackBishop = blackPieces.stream().filter(piece -> piece.getPieceType().equals(PieceType.Bishop)).findFirst().orElse(null);
                return whiteBishop.isOnLightSquares() == blackBishop.isOnLightSquares();
        }

        return false;
    }

    /**
     * return a value depending on the remaining pieces:
     *  0: Just a King
     *  1: King and a Bishop
     *  2: King and a Knight
     *  -1: anything else
     *
     * @param colouredPieces
     * @return
     */
    private int getRemainingPiecesValue(ArrayList<Piece> colouredPieces){
        if (colouredPieces.size()>2) {
            return -1;
        } else if (colouredPieces.size()==1){
            return 0;
        } else {
            Piece nonKingPiece = colouredPieces.stream().filter(piece -> !piece.getPieceType().equals(PieceType.King)).findFirst().orElse(null);
            if (nonKingPiece.getPieceType()==PieceType.Bishop){
                return 1;
            } else if(nonKingPiece.getPieceType()==PieceType.Knight){
                return 2;
            } else {
                return -1;
            }
        }
    }
}
