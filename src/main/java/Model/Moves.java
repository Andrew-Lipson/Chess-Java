package Model;

import Model.pieces.PieceType;

import java.util.ArrayList;

public class Moves {

    private Board board;

    public Moves(Board board){
        this.board = board;
    }

    public static ArrayList<Integer[]> chooseMove(Integer file, Integer rank, Boolean isWhite, PieceType pieceType){
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        switch (pieceType){
            case Bishop:
                return diagnal(file,rank,isWhite);
            case Rook:
                return straight(file,rank,isWhite);
            case Queen:
                return queen(file,rank,isWhite);
            case Knight:
                return knight(file,rank,isWhite);
            case King:
                return king(file,rank,isWhite);
            case Pawn:
                return pawn(file,rank,isWhite);
        }

        return possibleMoves;
    }



    public static ArrayList<Integer[]> diagnal(Integer file, Integer rank, Boolean isWhite){
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int iRank = -1; iRank < 2; iRank+=2) {
            for (int iFile = -1; iFile < 2; iFile+=2){
                Integer tempRank = rank+iRank;
                Integer tempFile = file+iFile;
                while (tempRank>=0 && tempRank<8 && tempFile>=0 && tempFile<8){
                    possibleMoves.add(new Integer[]{tempFile,tempRank});
                    tempRank+=iRank;
                    tempFile+=iFile;
                }
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> straight(Integer file, Integer rank, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int i = -1; i < 2; i += 2) {
            Integer tempRank = rank + i;
            while (tempRank >= 0 && tempRank < 8) {
                possibleMoves.add(new Integer[]{file, tempRank});
                tempRank += i;
            }
            Integer tempFile = file + i;
            while (tempFile >= 0 && tempFile < 8) {
                possibleMoves.add(new Integer[]{tempFile, rank});
                tempFile += i;
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> queen(Integer file, Integer rank, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = diagnal(file, rank, isWhite);
        possibleMoves.addAll(straight(file, rank, isWhite));
        return possibleMoves;
    }
    public static ArrayList<Integer[]> knight(Integer file, Integer rank, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int iRank = -2; iRank < 3; iRank++) {
            for (int iFile = -2; iFile < 3; iFile++){
                Integer tempRank = rank+iRank;
                Integer tempFile = file+iFile;
                if(iRank!=0 && iFile!=0 && ((iFile+iRank)%2!=0) && tempRank>=0 && tempRank<8 && tempFile>=0 && tempFile<8) {
                    possibleMoves.add(new Integer[]{tempFile, tempRank});
                }
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> king(Integer file, Integer rank, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int iRank = -1; iRank < 2; iRank++) {
            for (int iFile = -1; iFile < 2; iFile++) {
                if(iRank!=0 || iFile!=0){
                    Integer tempRank = rank+iRank;
                    Integer tempFile = file+iFile;
                    if(tempRank>=0 && tempRank<8 && tempFile>=0 && tempFile<8) {
                        possibleMoves.add(new Integer[]{tempFile, tempRank});
                    }
                }
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> pawn(Integer file, Integer rank, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        Integer startingRank;
        Integer movementDirection;
        if(isWhite){
            startingRank = 6;
            movementDirection = -1;
        }
        else{
            startingRank = 1;
            movementDirection = 1;
        }
        for (int iFile = -1; iFile < 2; iFile++) {
            Integer tempRank = rank+movementDirection;
            Integer tempFile = file+iFile;
            if(tempFile>=0 && tempFile<8){
                possibleMoves.add(new Integer[]{tempFile, tempRank});
                if(iFile == 0 && rank == startingRank){
                    possibleMoves.add(new Integer[]{tempFile, tempRank+movementDirection});
                }
            }
        }
        return possibleMoves;
    }
}
