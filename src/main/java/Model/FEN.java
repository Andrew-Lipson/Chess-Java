package Model;

import Model.Contract.Observer;
import Model.pieces.Piece;
import Model.pieces.PieceType;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Character.isDigit;
import static java.util.Objects.isNull;

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

    public static Game convertFenToBoard(String fenString, Observer observer){

        final Board boardSquares = new Board();
        ArrayList<Piece> whitePieces = new ArrayList<Piece>();
        ArrayList<Piece> blackPieces = new ArrayList<Piece>();
        boolean[] whiteCastling = {false, false};
        boolean[] blackCastling = {false, false};
        boolean whitesTurn;
        ArrayList<Piece> enPassantAvailablePieces = new ArrayList<Piece>();
        Position enPassantPosition;
        int halfmove;
        int fullmove;

        String[] str = fenString.split(" ");

        //str[0] what's the position
        char[] chars = str[0].toCharArray();
        int rank = 0;
        int file = 0;
        for (Character character:chars) {
            if (character.equals('/')) {
                rank += 1;
                file = 0;
            } else if (isDigit(character)) {
                for (int i = 0; i < Character.getNumericValue(character); i++) {
                    file += 1;
                }

            } else {
                boolean isWhite = Character.isUpperCase(character);
                PieceType pieceType = PieceType.getPieceType(character.toString().toLowerCase());
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

        //str[1] whose turn
        whitesTurn = Objects.equals(str[1], "w");

        //str[2] is castling
        chars = str[2].toCharArray();
        for (char character: chars) {
            switch (character) {
                case 'K':
                    whiteCastling[0] = true;
                    break;
                case 'Q':
                    whiteCastling[1] = true;
                    break;
                case 'k':
                    blackCastling[0] = true;
                    break;
                case 'q':
                    blackCastling[1] = true;
                    break;
                default:
                    break;
            }
        }

        // str[3] is en Passant
        //this.fen.setEnPassantPieceString(str[3]);
        //Position enPassantPosition;
        if(!Objects.equals(str[3], "-")){
            chars = str[3].toCharArray();
            int enPassantFile = chars[0];
            enPassantPosition = new Position(enPassantFile-97,8-Character.getNumericValue(chars[1]));
        }
        else {
            enPassantPosition = null;
        }

        // str[4] is halfmove
        halfmove = Integer.parseInt(str[4]);

        // str[5] is fullmove
        fullmove = Integer.parseInt(str[5]);

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
}
