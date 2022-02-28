package Model.Utilities;

import Model.Game;
import Model.Position;
import Model.Pieces.Piece;
import Model.Pieces.PieceType;

import java.util.ArrayList;

import static java.util.Objects.nonNull;
import static java.util.Objects.isNull;

public final class Moves {

    /**
     * Choosing the correct function to call
     * 
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @return list of possible moves including Illegal Moves
     */
    public static ArrayList<Position> getAllMovesIncludingIllegalMoves(Position position, Game game) {
        Piece piece = game.getPiece(position);
        PieceType pieceType = piece.getPieceType();
        boolean isWhite = piece.getIsWhite();
        switch (pieceType) {
            case Bishop:
                return diagonal(position,game, isWhite);
            case Rook:
                return straight(position,game, isWhite);
            case Queen:
                return queen(position,game, isWhite);
            case Knight:
                return knight(position,game, isWhite);
            case King:
                return king(position,game, isWhite);
            case Pawn:
                return pawn(position,game, isWhite);
        }


        return new ArrayList<>();
    }

    /**
     * Adding all diagonal moves to possibleMoves
     *
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @return all possible diagonal moves (including illegal)
     */
    private static ArrayList<Position> diagonal(Position position, Game game, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        for (int iRank = -1; iRank < 2; iRank += 2) {
            for (int iFile = -1; iFile < 2; iFile += 2){
                int tempRank = position.getRank() + iRank;
                int tempFile = position.getFile() + iFile;
                while (tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8){
                    if(addPossibleMoveStraightAndDiagonal(new Position(tempFile, tempRank), game,isWhite,possibleMoves)) {
                        break;
                    }
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
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @return all possible straight moves (including illegal)
     */
    private static ArrayList<Position> straight(Position position, Game game, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        for (int i = -1; i < 2; i += 2) {
            int tempRank = position.getRank() + i;
            while (tempRank >= 0 && tempRank < 8) {
                if(addPossibleMoveStraightAndDiagonal(new Position(position.getFile(), tempRank), game,isWhite,possibleMoves)) {
                    break;
                }
                    tempRank += i;
            }
            int tempFile = position.getFile() + i;
            while (tempFile >= 0 && tempFile < 8) {
                if(addPossibleMoveStraightAndDiagonal(new Position(tempFile, position.getRank()), game,isWhite,possibleMoves)) {
                    break;
                }
                tempFile += i;
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all diagonal and straight moves to possibleMoves
     *
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @return all possible diagonal and straight moves (including illegal)
     */
    private static ArrayList<Position> queen(Position position, Game game, boolean isWhite) {
        ArrayList<Position> possibleMoves = diagonal(position, game, isWhite);
        possibleMoves.addAll(straight(position, game, isWhite));
        return possibleMoves;
    }

    /**
     * Adding all knight moves to possibleMoves
     *
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @return all possible knight moves (including illegal)
     */
    private static ArrayList<Position> knight(Position position, Game game, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        for (int iRank = -2; iRank < 3; iRank++) {
            for (int iFile = -2; iFile < 3; iFile++){
                int tempRank = position.getRank() + iRank;
                int tempFile = position.getFile() + iFile;
                if(iRank != 0 && iFile != 0 && ((iFile + iRank) % 2 != 0) && tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8) {
                    addPossibleMoves(new Position(tempFile, tempRank), game,isWhite,possibleMoves);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all king moves to possibleMoves
     *
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @return all possible king moves (including illegal)
     */
    private static ArrayList<Position> king(Position position, Game game, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
        for (int iRank = -1; iRank < 2; iRank++) {
            for (int iFile = -1; iFile < 2; iFile++) {
                if(iRank != 0 || iFile != 0){
                    int tempRank = position.getRank() + iRank;
                    int tempFile = position.getFile() + iFile;
                    if(tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8) {
                        addPossibleMoves(new Position(tempFile, tempRank), game,isWhite,possibleMoves);
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all pawn moves to possibleMoves
     *
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @return all possible pawn moves (including illegal)
     */
    private static ArrayList<Position> pawn(Position position, Game game, boolean isWhite) {
        ArrayList<Position> possibleMoves = new ArrayList<>();
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

        //Moving Pawn forward
        Piece piece = game.getPiece(new Position(tempFile, tempRank));
        if(tempFile >= 0 && tempFile < 8 && isNull(piece)) {
            possibleMoves.add(new Position(tempFile, tempRank));
            if(rank == startingRank){
                tempRank += movementDirection;
                if(isNull(game.getPiece(new Position(tempFile, tempRank)))) {
                        possibleMoves.add(new Position(tempFile, tempRank));
                }
            }
        }

        //Capturing diagonally
        for (int iFile = -1; iFile < 2; iFile += 2) {
            tempRank = rank + movementDirection;
            tempFile = file + iFile;
            if (tempFile >= 0 && tempFile < 8){
                piece = game.getPiece(new Position(tempFile, tempRank));
                if(nonNull(piece) && piece.getIsWhite() != isWhite){
                    possibleMoves.add(new Position(tempFile, tempRank));
                }
            }
        }
        //en Passant
        piece = game.getPiece(position);
        if(nonNull(piece.getEnPassantAvailableToTakeFile())) {
                possibleMoves.add(new Position(piece.getEnPassantAvailableToTakeFile(), rank + movementDirection));
        }
        return possibleMoves;
    }


    /**
     * Adding moves to the possibleMoves List for the Straight and Diagonal
     * If there is a piece on the position return true, otherwise False
     *
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @param possibleMoves ArrayList of the possible moves already created
     * @return boolean indicating if there is a piece on the position
     */
    private static boolean addPossibleMoveStraightAndDiagonal(Position position, Game game, boolean isWhite, ArrayList<Position> possibleMoves) {
        Piece piece = game.getPiece(position);
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
     * @param position of the piece we are trying to move
     * @param game that the piece is playing in
     * @param isWhite is the piece white or black
     * @param possibleMoves ArrayList of the possible moves already created
     */
    private static void addPossibleMoves(Position position, Game game, boolean isWhite, ArrayList<Position> possibleMoves) {
        Piece piece = game.getPiece(position);
        if(isNull(piece) || piece.getIsWhite() != isWhite){
            possibleMoves.add(position);
        }
    }
}
