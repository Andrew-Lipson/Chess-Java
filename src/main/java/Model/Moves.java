package Model;

import Model.pieces.Piece;
import Model.pieces.PieceType;

import java.util.ArrayList;

import static java.util.Objects.isNull;

public class Moves {

    private Board board;

    public Moves(Board board){
        this.board = board;
    }

    public static ArrayList<Integer[]> chooseMove(Integer file, Integer rank, Boolean isWhite, Board board, PieceType pieceType){
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        switch (pieceType){
            case Bishop:
                return diagnal(file,rank,board, isWhite);
            case Rook:
                return straight(file,rank,board, isWhite);
            case Queen:
                return queen(file,rank,board, isWhite);
            case Knight:
                return knight(file,rank,board, isWhite);
            case King:
                return king(file,rank,board, isWhite);
            case Pawn:
                return pawn(file,rank,board, isWhite);
        }

        return possibleMoves;
    }

    private static Boolean addPossibleMoveStraightAndDiagnal(Integer file, Integer rank, Board board, Boolean isWhite, ArrayList<Integer[]> possibleMoves){
        Piece piece = board.getBoardSquares().getSquare(file,rank).getPiece();
        if (isNull(piece)){
            possibleMoves.add(new Integer[]{file,rank});
            return false;
        }
        else if (piece.getIsWhite() != isWhite){
            possibleMoves.add(new Integer[]{file,rank});
            return true;
        }
        else {
            return true;
        }
    }

    private static void addPossibleMoves(Integer file, Integer rank, Board board, Boolean isWhite, ArrayList<Integer[]> possibleMoves){
        Piece piece = board.getBoardSquares().getSquare(file,rank).getPiece();
        if(isNull(piece)){
            possibleMoves.add(new Integer[]{file,rank});
        }
        else if (piece.getIsWhite() != isWhite){
            possibleMoves.add(new Integer[]{file,rank});
        }
    }


    public static ArrayList<Integer[]> diagnal(Integer file, Integer rank, Board board, Boolean isWhite){
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int iRank = -1; iRank < 2; iRank+=2) {
            for (int iFile = -1; iFile < 2; iFile+=2){
                Integer tempRank = rank+iRank;
                Integer tempFile = file+iFile;
                while (tempRank>=0 && tempRank<8 && tempFile>=0 && tempFile<8){
                    if(addPossibleMoveStraightAndDiagnal(tempFile, tempRank, board,isWhite,possibleMoves)){
                        break;
                    };
                        tempRank += iRank;
                        tempFile += iFile;
                }
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> straight(Integer file, Integer rank, Board board, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int i = -1; i < 2; i += 2) {
            Integer tempRank = rank + i;
            while (tempRank >= 0 && tempRank < 8) {
                if(addPossibleMoveStraightAndDiagnal(file, tempRank, board,isWhite,possibleMoves)){
                    break;
                };
                    tempRank += i;
            }
            Integer tempFile = file + i;
            while (tempFile >= 0 && tempFile < 8) {
                if(addPossibleMoveStraightAndDiagnal(tempFile, rank, board,isWhite,possibleMoves)){
                    break;
                };
                tempFile += i;
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> queen(Integer file, Integer rank, Board board, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = diagnal(file, rank, board, isWhite);
        possibleMoves.addAll(straight(file, rank, board, isWhite));
        return possibleMoves;
    }
    public static ArrayList<Integer[]> knight(Integer file, Integer rank, Board board, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int iRank = -2; iRank < 3; iRank++) {
            for (int iFile = -2; iFile < 3; iFile++){
                Integer tempRank = rank+iRank;
                Integer tempFile = file+iFile;
                if(iRank!=0 && iFile!=0 && ((iFile+iRank)%2!=0) && tempRank>=0 && tempRank<8 && tempFile>=0 && tempFile<8) {
                    addPossibleMoves(tempFile, tempRank, board,isWhite,possibleMoves);
                }
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> king(Integer file, Integer rank, Board board, Boolean isWhite) {
        ArrayList<Integer[]> possibleMoves = new ArrayList<Integer[]>();
        for (int iRank = -1; iRank < 2; iRank++) {
            for (int iFile = -1; iFile < 2; iFile++) {
                if(iRank!=0 || iFile!=0){
                    Integer tempRank = rank+iRank;
                    Integer tempFile = file+iFile;
                    if(tempRank>=0 && tempRank<8 && tempFile>=0 && tempFile<8) {
                        addPossibleMoves(tempFile, tempRank, board,isWhite,possibleMoves);
                    }
                }
            }
        }
        return possibleMoves;
    }

    public static ArrayList<Integer[]> pawn(Integer file, Integer rank, Board board, Boolean isWhite) {
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
        Integer tempRank = rank+movementDirection;
        Integer tempFile = file;
        Piece piece = board.getBoardSquares().getSquare(tempFile,tempRank).getPiece();
        if(tempFile>=0 && tempFile<8 && isNull(piece)){
            possibleMoves.add(new Integer[]{tempFile, tempRank});
            if(rank == startingRank){
                tempRank+=movementDirection;
                if(isNull(board.getBoardSquares().getSquare(tempFile,tempRank).getPiece())) {
                    possibleMoves.add(new Integer[]{tempFile, tempRank});
                }
            }
        }
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempRank = rank + movementDirection;
            tempFile = file+iFile;
            if (tempFile >= 0 && tempFile < 8){
                piece = board.getBoardSquares().getSquare(tempFile,tempRank).getPiece();
                if(!isNull(piece) && piece.getIsWhite()!=isWhite){
                    possibleMoves.add(new Integer[]{tempFile, tempRank});
                }
            }
        }
        piece = board.getBoardSquares().getSquare(file,rank).getPiece();
        if(!isNull(piece.getEnPassantAvailableToTakeFile())){
            possibleMoves.add(new Integer[]{piece.getEnPassantAvailableToTakeFile(), rank + movementDirection});
        }
        return possibleMoves;
    }
}
