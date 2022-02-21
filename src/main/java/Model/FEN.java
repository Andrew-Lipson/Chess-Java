package Model;

import Model.Contract.Observer;
import Model.pieces.Piece;
import Model.pieces.PieceType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.Character.isDigit;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Fen {

    private String[] fenBreaks = {"/","/","/","/","/","/","/"," ",};

    public static String convertBoardToFen(Piece[][] piece2DArray, boolean whitesTurn, boolean[] whiteCastling, boolean[] blackCastling, Position enPassantPosition,  int halfmove, int fullmove){

        String[] fenBreaks = {"/","/","/","/","/","/","/"," ",};
        String output = "";

        for (int rank = 0; rank < 8; rank++) {
            output += updateFENPosition(piece2DArray[rank],rank);
            output += fenBreaks[rank];
        }

        if (whitesTurn){
            output += "w ";
        }
        else{
            output += "b ";
        }


        output += castlingToString(whiteCastling, blackCastling);

        output += enPassantToString(enPassantPosition);


        output += " " + halfmove + " " + fullmove;

        return output;
    }

    /**
     *
     * @param fenString
     * @param observer
     * @return null if game is impossible (Fen error, castling error, en passant error)
     */
    public static Game convertFenToBoard(String fenString, Observer observer){

        final Board boardSquares = new Board();
        ArrayList<Piece> whitePieces = new ArrayList<Piece>();
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();
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
                Piece piece = new Piece(isWhite,pieceType);
                piece.setPosition(new Position(file,rank));
                boardSquares.addPiece(new Position(file,rank),piece);
                if (isWhite){
                    whitePieces.add(piece);
                }
                else {
                    blackPieces.add(piece);
                }
                file += 1;
            }
        }
        if(file != 8 || rank != 7){
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
                    }
                    else {
                        return null;
                    }
                    break;
                case 'Q':
                    if (checkCastlingIsPossible(boardSquares, new Position(4,7),new Position(0,7))){
                        whiteCastling[1] = true;
                    }
                    else {
                        return null;
                    }
                    break;
                case 'k':
                    if (checkCastlingIsPossible(boardSquares, new Position(4,0),new Position(7,0))){
                        blackCastling[0] = true;
                    }
                    else {
                        return null;
                    }
                    break;
                case 'q':
                    if (checkCastlingIsPossible(boardSquares, new Position(4,0),new Position(0,0))){
                        blackCastling[1] = true;
                    }
                    else {
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
        }
        else {
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

        return new Game(observer, boardSquares, whitePieces, blackPieces, whiteCastling, blackCastling, whitesTurn, enPassantPosition, halfmove, fullmove);
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
        }
        else {
            Piece piece = board.getPiece(new Position(enPassantPosition.getFile(),4));
            if (isNull(piece) || piece.getPieceType() != PieceType.Pawn || !piece.getIsWhite()){
                return false;
            }
        }
        return true;
    }
}
