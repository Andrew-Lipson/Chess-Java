package Model;

import Model.pieces.Piece;

import static java.util.Objects.isNull;

public class FEN {

    private String[] fenPosition = new String[8];
    private String[] fenBreaks = {"/","/","/","/","/","/","/"," ",};
    private String enPassantPiece = "-";


    public FEN(Piece[][] piece2DArray) {
        for (int rank = 0; rank < 8; rank++) {
            updateFENPosition(piece2DArray[rank],rank);
        }
    }

    /**
     * With all the pieces in the rank, update the appropriate FEN rank in FENPosition
     * 
     * @param pieceRank
     * @param rank
     */
    public void updateFENPosition(Piece[] pieceRank, int rank) {
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
        fenPosition[rank] = output;
    }

    /**
     * Set the En Passant Piece
     * 
     * @param position
     */
    public void setEnPassantPiece(Position position) {
        if(isNull(position)){
            this.enPassantPiece = "-";
            return;
        }
        this.enPassantPiece = enPassantToString(position);
    }

    public void setEnPassantPieceString(String coordinates) {
        this.enPassantPiece = coordinates;
    }

    /**
     * Add all the FEN elements together to get a completed FEN string
     * 
     * @return
     */
    public String createCompleteFEN(boolean whitesTurn, boolean[] whiteCastling, boolean[] blackCastling, int halfmove, int fullmove) {
        String output = "";
        for (int i =0;i< fenPosition.length;i++) {
            output += fenPosition[i];
            output += fenBreaks[i];
        }

        if (whitesTurn){
            output += "w ";
        }
        else{
            output += "b ";
        }

        output += castlingToString(whiteCastling, blackCastling);

        output += enPassantPiece;


        output += " " + halfmove + " " + fullmove;

        return output;
    }


    /**
     * Add the correct castling notation to the output FEN String
     */
    private String castlingToString(boolean[] whiteCastling, boolean[] blackCastling) {
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
    private String enPassantToString(Position position) {

        String output = "";

        int asciiValue = 97 + position.getFile();
        char convertedChar = (char) asciiValue;
        output += convertedChar;

        output += 8 - (position.getRank());

        return output;
    }
}