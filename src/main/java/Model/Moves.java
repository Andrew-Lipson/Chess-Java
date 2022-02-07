package Model;

import java.util.ArrayList;

public class Moves {

    private Board board;

    public Moves(Board board){
        this.board = board;
    }

    public static ArrayList<Integer[]> diagnal(Integer rank, Integer file, Boolean isWhite){
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

    public static ArrayList<Integer[]> straight(Integer rank, Integer file, Boolean isWhite) {
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

    public static ArrayList<Integer[]> knight(Integer rank, Integer file, Boolean isWhite) {
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

}
