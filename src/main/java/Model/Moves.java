package Model;

import Model.pieces.Piece;
import Model.pieces.PieceType;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

public final class Moves {

    /**
     * Choosing the correct function to call
     * 
     * @param position
     * @param isWhite
     * @param board
     * @param pieceType
     * @return list of possible moves
     */
    public static ArrayList<Position> chooseMove(Position position, boolean isWhite, Board board, PieceType pieceType) {
        switch (pieceType) {
            case Bishop:
                return diagonal(position,board, isWhite);
            case Rook:
                return straight(position,board, isWhite);
            case Queen:
                return queen(position,board, isWhite);
            case Knight:
                return knight(position,board, isWhite);
            case King:
                return king(position,board, isWhite);
            case Pawn:
                return pawn(position,board, isWhite);
        }

        return new ArrayList<Position>();
    }

    /**
     * Adding moves to the possibleMoves List for the Straight and Diagonal
     * If there is a piece on the position return true, otherwise False
     * @param position
     * @param board
     * @param isWhite
     * @param possibleMoves
     * @return boolean indicating if there is a piece on the position
     */
    private static boolean addPossibleMoveStraightAndDiagonal(Position position, Board board, boolean isWhite, ArrayList<Position> possibleMoves) {
        Piece piece = board.getPiece(position);
        // If no Piece on the Position, add possible move and return false
        if (isNull(piece)) {
            possibleMoves.add(position);
            return false;
        }
        // If there is an opponent's Piece on the Position, add possible move and return true
        else if (piece.getIsWhite() != isWhite) {
            possibleMoves.add(position);
            return true;
        }
        // If there is same colour piece on the Position, just return true
        else {
            return true;
        }
    }

    /**
     * Add a Position to possibleMoves if the square is null or an opponent's piece is there
     * 
     * @param position
     * @param board
     * @param isWhite
     * @param possibleMoves
     */
    private static void addPossibleMoves(Position position, Board board, boolean isWhite, ArrayList<Position> possibleMoves) {
        Piece piece = board.getPiece(position);
        if(isNull(piece) || piece.getIsWhite() != isWhite){
            possibleMoves.add(position);
        }
    }


    /**
     * Adding all diagonal moves to possibleMoves
     * 
     * @param position
     * @param board
     * @param isWhite
     * @return all possible diagonal moves
     */
    private static ArrayList<Position> diagonal(Position position, Board board, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int iRank = -1; iRank < 2; iRank += 2) {
            for (int iFile = -1; iFile < 2; iFile += 2){
                int tempRank = position.getRank() + iRank;
                int tempFile = position.getFile() + iFile;
                while (tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8){
                    if(addPossibleMoveStraightAndDiagonal(new Position(tempFile, tempRank), board,isWhite,possibleMoves)) {
                        break;
                    };
                        tempRank += iRank;
                        tempFile += iFile;
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all straight moves to possibleMoves
     * 
     * @param position
     * @param board
     * @param isWhite
     * @return all possible straight moves
     */
    private static ArrayList<Position> straight(Position position, Board board, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int i = -1; i < 2; i += 2) {
            int tempRank = position.getRank() + i;
            while (tempRank >= 0 && tempRank < 8) {
                if(addPossibleMoveStraightAndDiagonal(new Position(position.getFile(), tempRank), board,isWhite,possibleMoves)) {
                    break;
                };
                    tempRank += i;
            }
            int tempFile = position.getFile() + i;
            while (tempFile >= 0 && tempFile < 8) {
                if(addPossibleMoveStraightAndDiagonal(new Position(tempFile, position.getRank()), board,isWhite,possibleMoves)) {
                    break;
                };
                tempFile += i;
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all diagonal and straight moves to possibleMoves
     * 
     * @param position
     * @param board
     * @param isWhite
     * @return all possible digonal and straight moves
     */
    private static ArrayList<Position> queen(Position position, Board board, boolean isWhite) {
        ArrayList<Position> possibleMoves = diagonal(position, board, isWhite);
        possibleMoves.addAll(straight(position, board, isWhite));
        return possibleMoves;
    }

    /**
     * Adding all knight moves to possibleMoves
     * 
     * @param position
     * @param board
     * @param isWhite
     * @return all possible knight moves
     */
    private static ArrayList<Position> knight(Position position, Board board, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int iRank = -2; iRank < 3; iRank++) {
            for (int iFile = -2; iFile < 3; iFile++){
                int tempRank = position.getRank() + iRank;
                int tempFile = position.getFile() + iFile;
                if(iRank != 0 && iFile != 0 && ((iFile + iRank) % 2 != 0) && tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8) {
                    addPossibleMoves(new Position(tempFile, tempRank), board,isWhite,possibleMoves);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all king moves to possibleMoves
     * 
     * @param position
     * @param board
     * @param isWhite
     * @return all possible king moves
     */
    private static ArrayList<Position> king(Position position, Board board, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int iRank = -1; iRank < 2; iRank++) {
            for (int iFile = -1; iFile < 2; iFile++) {
                if(iRank != 0 || iFile != 0){
                    int tempRank = position.getRank() + iRank;
                    int tempFile = position.getFile() + iFile;
                    if(tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8) {
                        addPossibleMoves(new Position(tempFile, tempRank), board,isWhite,possibleMoves);
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all pawn moves to possibleMoves
     * 
     * @param position
     * @param board
     * @param isWhite
     * @return all possible pawn moves
     */
    private static ArrayList<Position> pawn(Position position, Board board, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        int file = position.getFile();
        int rank = position.getRank();
        int startingRank;
        int movementDirection;
        if(isWhite){
            startingRank = 6;
            movementDirection = -1;
        }
        else{
            startingRank = 1;
            movementDirection = 1;
        }
        int tempRank = rank + movementDirection;
        int tempFile = file;
        Piece piece = board.getPiece(new Position(tempFile, tempRank));
        if(tempFile >= 0 && tempFile < 8 && isNull(piece)) {
            possibleMoves.add(new Position(tempFile, tempRank));
            if(rank == startingRank){
                tempRank += movementDirection;
                if(isNull(board.getPiece(new Position(tempFile, tempRank)))) {
                    possibleMoves.add(new Position(tempFile, tempRank));
                }
            }
        }
        for (int iFile = -1; iFile < 2; iFile += 2) {
            tempRank = rank + movementDirection;
            tempFile = file + iFile;
            if (tempFile >= 0 && tempFile < 8){
                piece = board.getPiece(new Position(tempFile, tempRank));
                if(nonNull(piece) && piece.getIsWhite() != isWhite){
                    possibleMoves.add(new Position(tempFile, tempRank));
                }
            }
        }
        piece = board.getPiece(position);
        if(nonNull(piece.getEnPassantAvailableToTakeFile())) {
            possibleMoves.add(new Position(piece.getEnPassantAvailableToTakeFile(), rank + movementDirection));
        }
        return possibleMoves;
    }
}
