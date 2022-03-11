package Model;

import Contract.Contract;
import Model.Pieces.Piece;
import Model.Pieces.PieceType;
import Model.Utilities.Check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Character.isDigit;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Fen {

    private String fullFen;
    private String fenForRepetitionCheck;
    private String[] fenBreaks = {"/","/","/","/","/","/","/"," ",};

    public void updateFen(Piece[][] piece2DArray, boolean whitesTurn, boolean[] whiteCastling, boolean[] blackCastling, Position enPassantPosition, int halfmove, int fullmove){
        convertBoardToFen(piece2DArray, whitesTurn, whiteCastling, blackCastling, enPassantPosition, halfmove, fullmove);
        convertBoardToFenForCheckingRepetition(piece2DArray, whitesTurn, whiteCastling, blackCastling, enPassantPosition);
    }


    /** Giving all necessary information to create a FEN string that represents the current state of the board
     *
     * @param piece2DArray is the Board but in a friendlier version to read
     * @param whitesTurn is who's turn it is
     * @param whiteCastling is the ability for white to castle
     * @param blackCastling is the ability for black to castle
     * @param enPassantPosition is the position of any piece that just double moved
     * @param halfmove is the current halfmove count
     * @param fullmove is the current fullmove count
     * @return the full FEN string
     */
    public void convertBoardToFen(Piece[][] piece2DArray, boolean whitesTurn, boolean[] whiteCastling, boolean[] blackCastling, Position enPassantPosition, int halfmove, int fullmove){

        StringBuilder output = new StringBuilder();

        for (int rank = 0; rank < 8; rank++) {
            output.append(updateFENPosition(piece2DArray[rank], rank));
            output.append(fenBreaks[rank]);
        }

        if (whitesTurn){
            output.append("w ");
        } else{
            output.append("b ");
        }


        output.append(castlingToString(whiteCastling, blackCastling));

        output.append(enPassantToString(enPassantPosition));


        output.append(" ").append(halfmove).append(" ").append(fullmove);

        fullFen = output.toString();
    }

    /**
     *  Giving all necessary information minus the number of moves to create a FEN string
     *  that can be checked against other Fen strings for repetition
     *
     * @param piece2DArray is the Board but in a friendlier version to read
     * @param whitesTurn is who's turn it is
     * @param whiteCastling is the ability for white to castle
     * @param blackCastling is the ability for black to castle
     * @return the full FEN string
     */
    public void convertBoardToFenForCheckingRepetition(Piece[][] piece2DArray, boolean whitesTurn, boolean[] whiteCastling, boolean[] blackCastling, Position enPassantPosition){

        StringBuilder output = new StringBuilder();

        for (int rank = 0; rank < 8; rank++) {
            output.append(updateFENPosition(piece2DArray[rank], rank));
            output.append(fenBreaks[rank]);
        }

        if (whitesTurn){
            output.append("w ");
        } else{
            output.append("b ");
        }


        output.append(castlingToString(whiteCastling, blackCastling));

        output.append(enPassantToString(enPassantPosition));

        fenForRepetitionCheck = output.toString();
    }


    /**
     * With all the pieces in the rank, update the appropriate FEN rank in FENPosition
     *
     * @param pieceRank
     * @param rank
     */
    private static String updateFENPosition(Piece[] pieceRank, int rank) {
        String output = "";
        int emptySquare = 0;
        for (Piece piece:pieceRank) {
            if (isNull(piece)) {
                emptySquare += 1;
            } else {
                if (!(emptySquare == 0)) {
                    output += emptySquare;
                    emptySquare = 0;
                }
                output += piece.getFenRepresentation();

            }
        }
        if (!(emptySquare == 0)) {
            output += emptySquare;
        }
        return output;
    }

    /**
     * Add the correct castling notation to the output FEN String
     */
    private static String castlingToString(boolean[] whiteCastling, boolean[] blackCastling) {
        String output = "";
        if (!whiteCastling[0] && !whiteCastling[1] && !blackCastling[0] && !blackCastling[1]) {
            output += "-";
        }
        if (whiteCastling[0]) {
            output += "K";
        }
        if (whiteCastling[1]) {
            output += "Q";
        }
        if (blackCastling[0]) {
            output += "k";
        }
        if (blackCastling[1]) {
            output += "q";
        }

        output += " ";
        return output;
    }


    /**
     * Returns the correct enPassant notation
     *
     * @param position
     * @return enPassant notation
     */
    private static String enPassantToString(Position position) {

        String output = "";

        if (isNull(position)){
            return "-";
        }

        int asciiValue = 97 + position.getFile();
        char convertedChar = (char) asciiValue;
        output += convertedChar;

        output += 8 - (position.getRank());

        return output;
    }

    private static boolean checkCastlingIsPossible(Board board, Position kingPosition, Position rookPosition){
        Piece kingPiece = board.getPiece(kingPosition);
        Piece rookPiece = board.getPiece(rookPosition);
        if (isNull(rookPiece)||isNull(kingPiece)){
            return false;
        }
        return (kingPiece.getPieceType() == PieceType.King && rookPiece.getPieceType() == PieceType.Rook);
    }

    private static boolean checkEnPassantIsPossible(Board board, Position enPassantPosition){
        if (!Arrays.asList(new Integer[]{2,5}).contains(enPassantPosition.getRank())){
            return false;
        }
        if(nonNull(board.getPiece(enPassantPosition))){
            return false;
        }
        if (enPassantPosition.getRank()==2){
            Piece piece = board.getPiece(new Position(enPassantPosition.getFile(),3));
            if (isNull(piece) || piece.getPieceType() != PieceType.Pawn || piece.getIsWhite()){
                return false;
            }
        } else {
            Piece piece = board.getPiece(new Position(enPassantPosition.getFile(),4));
            if (isNull(piece) || piece.getPieceType() != PieceType.Pawn || !piece.getIsWhite()){
                return false;
            }
        }
        return true;
    }

    public String getFullFen() {
        return fullFen;
    }

    public String getFenForRepetitionCheck() {
        return fenForRepetitionCheck;
    }

    /** Go through the inputed fenString and create the correct state of the Game. Returning null if the game is impossible
     *
     * @param fenString that we are creating the board off
     * @param observer that will be injected into the Game
     * @return a Game in the same state as the FEN, or null if game is impossible (Fen error, castling error, en passant error)
     */
    public static Game convertFenToBoard(String fenString, Contract.Observer observer){

        final Board boardSquares = new Board();
        ArrayList<Piece> whitePieces = new ArrayList<>();
        ArrayList<Piece> blackPieces = new ArrayList<>();
        boolean[] whiteCastling = {false, false};
        boolean[] blackCastling = {false, false};
        boolean whitesTurn;
        Position enPassantPosition;
        int halfmove;
        int fullmove;

        String[] str = fenString.split(" ");
        if (str.length!=6){
            return null;
        }


        //str[0] what's the position
        char[] chars = str[0].toCharArray();
        int rank = 0;
        int file = 0;
        boolean whiteKing = false;
        boolean blackKing = false;
        for (Character character:chars) {
            if (character.equals('/')) {
                if(file!=8){
                    return null;
                }
                rank += 1;
                file = 0;
            } else if (isDigit(character)) {
                for (int i = 0; i < Character.getNumericValue(character); i++) {
                    file += 1;
                }

            } else {
                boolean isWhite = Character.isUpperCase(character);
                String lowerCase = character.toString().toLowerCase();
                if (!Arrays.asList(new String[]{"k","q","r","n","b","p"}).contains(lowerCase)){
                    return null;
                }
                PieceType pieceType = PieceType.getPieceType(lowerCase);
                if (pieceType == PieceType.King){
                    if (isWhite){
                        if (whiteKing){
                            return null;
                        } else {
                            whiteKing = true;
                        }
                    } else{
                        if (blackKing){
                            return null;
                        } else {
                            blackKing = true;
                        }
                    }
                }
                Piece piece = new Piece(isWhite,pieceType);
                piece.setPosition(new Position(file,rank));
                boardSquares.addPiece(new Position(file,rank),piece);
                if (isWhite){
                    whitePieces.add(piece);
                } else {
                    blackPieces.add(piece);
                }
                file += 1;
            }
        }
        if(file != 8 || rank != 7){
            return null;
        }
        if (!whiteKing || !blackKing){
            return null;
        }

        //str[1] whose turn
        if (!Arrays.asList(new String[]{"w", "b"}).contains(str[1])){
            return null;
        }
        whitesTurn = Objects.equals(str[1], "w");

        //str[2] is castling
        chars = str[2].toCharArray();
        for (char character: chars) {
            switch (character) {
                case 'K':
                    if (checkCastlingIsPossible(boardSquares, new Position(4,7),new Position(7,7))){
                        whiteCastling[0] = true;
                    } else {
                        return null;
                    }
                    break;
                case 'Q':
                    if (checkCastlingIsPossible(boardSquares, new Position(4,7),new Position(0,7))){
                        whiteCastling[1] = true;
                    } else {
                        return null;
                    }
                    break;
                case 'k':
                    if (checkCastlingIsPossible(boardSquares, new Position(4,0),new Position(7,0))){
                        blackCastling[0] = true;
                    } else {
                        return null;
                    }
                    break;
                case 'q':
                    if (checkCastlingIsPossible(boardSquares, new Position(4,0),new Position(0,0))){
                        blackCastling[1] = true;
                    } else {
                        return null;
                    }
                    break;
                case '-':
                    break;
                default:
                    return null;
            }
        }

        // str[3] is en Passant
        if(!Objects.equals(str[3], "-")){
            chars = str[3].toCharArray();
            int enPassantFile = chars[0];
            enPassantPosition = new Position(enPassantFile-97,8-Character.getNumericValue(chars[1]));
            if (!checkEnPassantIsPossible(boardSquares,enPassantPosition)){
                return null;
            }
        } else {
            enPassantPosition = null;
        }

        // str[4] is halfmove
        halfmove = Integer.parseInt(str[4]);
        if (halfmove<0){
            return null;
        }

        // str[5] is fullmove
        fullmove = Integer.parseInt(str[5]);
        if (fullmove<1){
            return null;
        }

        Game game = new Game(observer, boardSquares, whitePieces, blackPieces, whiteCastling, blackCastling, whitesTurn, enPassantPosition, halfmove, fullmove);
        return Check.isTheKingUnderAttack(game,!game.getWhitesTurn())?null:game;

    }
}
