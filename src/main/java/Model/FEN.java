package Model;

import Model.pieces.Piece;

import static java.util.Objects.isNull;

public class FEN {

    private String[] fenPosition = new String[8];
    private String[] fenBreaks = {"/","/","/","/","/","/","/"," ",};
    private String enPassantPiece = "-";
    private int halfmove = 0;
    private int fullmove = 0;
    private String output;

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

    public void setHalfmove(int halfmove) {
        this.halfmove = halfmove;
    }

    public void setFullmove(int fullmove) {
        this.fullmove = fullmove;
    }

    public void setEnPassantPieceString(String coordinates) {
        this.enPassantPiece = coordinates;
    }

    /**
     * Add all the FEN elements together to get a completed FEN string
     * 
     * @return
     */
    public String createCompleteFEN(boolean whitesTurn, boolean[] whiteCastling, boolean[] blackCastling) {
        updateFENTurns( whitesTurn, false);
        output = "";
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

        castlingToString(whiteCastling, blackCastling);

        output += enPassantPiece;


        output += " " + halfmove + " " + fullmove;

        return output;
    }

    /**
     * Update the fullmove if it's back to white. Update the halfmove if required
     * 
     * @param halfmoveUpdate
     */
    private void updateFENTurns(boolean whitesTurn ,boolean halfmoveUpdate) {
        if (whitesTurn){
            fullmove += 1;
        }
        if(halfmoveUpdate) {
            halfmove += 1;
        }
    }

    /**
     * Add the correct castling notation to the output FEN String
     */
    private void castlingToString(boolean[] whiteCastling, boolean[] blackCastling) {
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
    }

    /**
     * Returns the correct enPassant notation
     * 
     * @param position
     * @return enPassant notation
     */
    private String enPassantToString(Position position) {

        String output = "";


        switch (position.getFile()) {
            case 0:
                output += "a";
                break;
            case 1:
                output += "b";
                break;
            case 2:
                output += "c";
                break;
            case 3:
                output += "d";
                break;
            case 4:
                output += "e";
                break;
            case 5:
                output += "f";
                break;
            case 6:
                output += "g";
                break;
            case 7:
                output += "h";
                break;
        }

        output += 8 - (position.getRank());

        return output;
    }
}