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
     * @param game
     * @return list of possible moves
     */
    public static ArrayList<Position> chooseMove(Position position, Game game, boolean realMove) {
        Piece piece = realMove?game.getPiece(position):game.getPieceFromTempBoard(position);
        PieceType pieceType = piece.getPieceType();
        boolean isWhite = piece.getIsWhite();
        switch (pieceType) {
            case Bishop:
                return diagonal(piece,game, isWhite, realMove);
            case Rook:
                return straight(piece,game, isWhite, realMove);
            case Queen:
                return queen(piece,game, isWhite, realMove);
            case Knight:
                return knight(piece,game, isWhite, realMove);
            case King:
                return king(piece,game, isWhite, realMove);
            case Pawn:
                return pawn(piece,game, isWhite, realMove);
        }


        return new ArrayList<Position>();
    }

    /**
     * Adding all diagonal moves to possibleMoves
     * 
     * @param piece
     * @param game
     * @param isWhite
     * @return all possible diagonal moves
     */
    private static ArrayList<Position> diagonal(Piece piece, Game game, boolean isWhite, boolean realMove) {
        Position position = piece.getPosition();
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int iRank = -1; iRank < 2; iRank += 2) {
            for (int iFile = -1; iFile < 2; iFile += 2){
                int tempRank = position.getRank() + iRank;
                int tempFile = position.getFile() + iFile;
                while (tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8){
                    if(addPossibleMoveStraightAndDiagonal(piece, new Position(tempFile, tempRank), game,isWhite,possibleMoves, realMove)) {
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
     * @param piece
     * @param game
     * @param isWhite
     * @return all possible straight moves
     */
    private static ArrayList<Position> straight(Piece piece, Game game, boolean isWhite, boolean realMove) {
        Position position = piece.getPosition();
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int i = -1; i < 2; i += 2) {
            int tempRank = position.getRank() + i;
            while (tempRank >= 0 && tempRank < 8) {
                if(addPossibleMoveStraightAndDiagonal(piece, new Position(position.getFile(), tempRank), game,isWhite,possibleMoves, realMove)) {
                    break;
                };
                    tempRank += i;
            }
            int tempFile = position.getFile() + i;
            while (tempFile >= 0 && tempFile < 8) {
                if(addPossibleMoveStraightAndDiagonal(piece, new Position(tempFile, position.getRank()), game,isWhite,possibleMoves, realMove)) {
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
     * @param piece
     * @param game
     * @param isWhite
     * @return all possible digonal and straight moves
     */
    private static ArrayList<Position> queen(Piece piece, Game game, boolean isWhite, boolean realMove) {
        ArrayList<Position> possibleMoves = diagonal(piece, game, isWhite, realMove);
        possibleMoves.addAll(straight(piece, game, isWhite, realMove));
        return possibleMoves;
    }

    /**
     * Adding all knight moves to possibleMoves
     * 
     * @param piece
     * @param game
     * @param isWhite
     * @return all possible knight moves
     */
    private static ArrayList<Position> knight(Piece piece, Game game, boolean isWhite, boolean realMove) {
        Position position = piece.getPosition();
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int iRank = -2; iRank < 3; iRank++) {
            for (int iFile = -2; iFile < 3; iFile++){
                int tempRank = position.getRank() + iRank;
                int tempFile = position.getFile() + iFile;
                if(iRank != 0 && iFile != 0 && ((iFile + iRank) % 2 != 0) && tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8) {
                    addPossibleMoves(piece, new Position(tempFile, tempRank), game,isWhite,possibleMoves, realMove);
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all king moves to possibleMoves
     * 
     * @param piece
     * @param game
     * @param isWhite
     * @return all possible king moves
     */
    private static ArrayList<Position> king(Piece piece, Game game, boolean isWhite, boolean realMove) {
        Position position = piece.getPosition();
        ArrayList<Position> possibleMoves = new ArrayList<Position>();
        for (int iRank = -1; iRank < 2; iRank++) {
            for (int iFile = -1; iFile < 2; iFile++) {
                if(iRank != 0 || iFile != 0){
                    int tempRank = position.getRank() + iRank;
                    int tempFile = position.getFile() + iFile;
                    if(tempRank >= 0 && tempRank < 8 && tempFile >= 0 && tempFile < 8) {
                        addPossibleMoves(piece, new Position(tempFile, tempRank), game,isWhite,possibleMoves, realMove);
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Adding all pawn moves to possibleMoves
     * 
     * @param piece
     * @param game
     * @param isWhite
     * @return all possible pawn moves
     */
    private static ArrayList<Position> pawn(Piece piece, Game game, boolean isWhite, boolean realMove) {
        Position position = piece.getPosition();
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

        //Moving Pawn forward
        Piece piece2 = realMove?game.getPiece(new Position(tempFile, tempRank)):game.getPieceFromTempBoard(new Position(tempFile, tempRank));
        if(tempFile >= 0 && tempFile < 8 && isNull(piece2)) {
            if (realMove){
                if(!checkForIllegalMoves(game,piece,new Position(tempFile, tempRank))){
                    possibleMoves.add(new Position(tempFile, tempRank));
                }
            }
            if(rank == startingRank){
                tempRank += movementDirection;
                if(isNull(realMove?game.getPiece(new Position(tempFile, tempRank)):game.getPieceFromTempBoard(new Position(tempFile, tempRank)))) {
                    if (realMove){
                        if(!checkForIllegalMoves(game,piece,new Position(tempFile, tempRank))){
                            possibleMoves.add(new Position(tempFile, tempRank));
                        }
                    }
                    else {
                        possibleMoves.add(new Position(tempFile, tempRank));
                    }

                }
            }
        }

        //Capturing diagonally
        for (int iFile = -1; iFile < 2; iFile += 2) {
            tempRank = rank + movementDirection;
            tempFile = file + iFile;
            if (tempFile >= 0 && tempFile < 8){
                piece2 = realMove?game.getPiece(new Position(tempFile, tempRank)):game.getPieceFromTempBoard(new Position(tempFile, tempRank));
                if(nonNull(piece2) && piece2.getIsWhite() != isWhite){
                    if (realMove){
                        if(!checkForIllegalMoves(game,piece,new Position(tempFile, tempRank))){
                            possibleMoves.add(new Position(tempFile, tempRank));
                        }
                    }
                    else {
                        possibleMoves.add(new Position(tempFile, tempRank));
                    }
                }
            }
        }
        //en Passant
        if(nonNull(piece.getEnPassantAvailableToTakeFile())) {
            if (realMove){
                if(!checkForIllegalMoves(game,piece,new Position(piece.getEnPassantAvailableToTakeFile(), rank + movementDirection))){
                    possibleMoves.add(new Position(piece.getEnPassantAvailableToTakeFile(), rank + movementDirection));
                }
            }
            else {
                possibleMoves.add(new Position(piece.getEnPassantAvailableToTakeFile(), rank + movementDirection));
            }
                    }
        return possibleMoves;
    }


    /**
     * Adding moves to the possibleMoves List for the Straight and Diagonal
     * If there is a piece on the position return true, otherwise False
     * @param position
     * @param game
     * @param isWhite
     * @param possibleMoves
     * @return boolean indicating if there is a piece on the position
     */
    private static boolean addPossibleMoveStraightAndDiagonal(Piece movingPiece, Position position, Game game, boolean isWhite, ArrayList<Position> possibleMoves, boolean realMove) {
        Piece piece = realMove?game.getPiece(position):game.getPieceFromTempBoard(position);
        // If no Piece on the Position, add possible move and return false
        if (isNull(piece)) {
            if (realMove){
                if(checkForIllegalMoves(game,movingPiece,position)){
                    return false;
                }
            }
            possibleMoves.add(position);
            return false;
        }
        // If there is an opponent's Piece on the Position, add possible move and return true
        else if (piece.getIsWhite() != isWhite) {
            if (realMove){
                if(checkForIllegalMoves(game,movingPiece,position)){
                    return true;
                }
            }
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
     * @param game
     * @param isWhite
     * @param possibleMoves
     */
    private static void addPossibleMoves(Piece movingPiece, Position position, Game game, boolean isWhite, ArrayList<Position> possibleMoves, boolean realMove) {
        Piece piece = realMove?game.getPiece(position):game.getPieceFromTempBoard(position);
        if(isNull(piece) || piece.getIsWhite() != isWhite){
            if (realMove){
                if(checkForIllegalMoves(game,movingPiece,position)){
                    return;
                }
            }
            possibleMoves.add(position);
        }
    }

    private static boolean checkForIllegalMoves(Game game, Piece piece, Position position){
        Board tempBoard = game.getTempBoard();
        Piece piece2 = new Piece(piece);
        tempBoard.removePiece(piece2.getPosition());
        tempBoard.addPiece(position, piece2);
        Piece kingPiece;
        ArrayList<Piece> checkingPieces;
        if(game.getWhitesTurn()){
            kingPiece = piece.getPieceType()==PieceType.King?piece2:game.getCloneWhitePieces().stream().filter(piece3 -> piece3.getPieceType() == PieceType.King).findFirst().orElse(null);
            checkingPieces = game.getCloneBlackPieces();
        }
        else{
            kingPiece = piece.getPieceType()==PieceType.King?piece2:game.getCloneBlackPieces().stream().filter(piece3 -> piece3.getPieceType() == PieceType.King).findFirst().orElse(null);
            checkingPieces = game.getCloneWhitePieces();
        }
        return checkForCheck(kingPiece, checkingPieces, game, false);
    }


    /**
     *
     * @param kingPiece
     * @param checkingPieces
     * @param game
     * @return
     */
    public static boolean checkForCheck(Piece kingPiece, ArrayList<Piece> checkingPieces, Game game, boolean realMove){
        if (isNull(kingPiece)){
            return false;
        }
        Position kingPosition = kingPiece.getPosition();
        for (Piece piece:checkingPieces) {
            ArrayList<Position> possibleMoves = Moves.chooseMove(piece.getPosition(), game, realMove);
            if (possibleMoves.stream().anyMatch(position -> position.getFile()==kingPosition.getFile() && position.getRank()==kingPosition.getRank())){
                return true;
            }
        }
        return false;
    }
}
