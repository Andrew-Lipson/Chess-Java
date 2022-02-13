package Model;


import Model.pieces.Piece;

import static java.util.Objects.isNull;

public class FEN {

    private String[] fenPosition = new String[8];
    private String[] fenBreaks = {"/","/","/","/","/","/","/"," ",};
    private Boolean whitesTurn = false;
    private Boolean[] whiteCastling = {true, true};
    private Boolean[] blackCastling = {true, true};
    private Piece enPassantPiece = null;
    private int halfmove = 0;
    private int fullmove = 0;
    private String output;

    public FEN(Piece[][] piece2DArray){
        for (int rank = 0; rank < 8; rank++){
                updateFENPosition(piece2DArray[rank],rank);
            }
    }

    //with all the pieces in the rank, update the appropriate FEN rank in FENPosition
    public void updateFENPosition(Piece[] pieceRank, int rank){
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
                output += getFENletter(piece);

            }
        }
        if (!(emptySquare==0)) {
            output += emptySquare;
        }
        fenPosition[rank]=output;
    }

    //From the Piece arg, get the correct FEN letter
    private String getFENletter(Piece piece){
        String output = "";
        switch (piece.getPieceType()){
            case King:
                output+="k";
                break;
            case Queen:
                output+="q";
                break;
            case Rook:
                output+="r";
                break;
            case Knight:
                output+="n";
                break;
            case Bishop:
                output+="b";
                break;
            case Pawn:
                output+="p";
                break;
        }
        if(piece.getIsWhite()){
            output = output.toUpperCase();
        }
        return output;
    }

    //Set the En Passant Piece
    public void setEnPassantPiece(Piece piece){
        this.enPassantPiece = piece;
    }

    //Add all the FEN elements together to get a completed FEN string
    public String createCompleteFEN(){
        updateFENTurns(false);
        output = "";
        for (int i =0;i< fenPosition.length;i++) {
            output+=fenPosition[i];
            output+=fenBreaks[i];
        }

        if (whitesTurn){
            output+="w ";
        }
        else{
            output+="b ";
        }

        castlingToString();

        output += enPassantToString(enPassantPiece);


        output+=" " + halfmove + " " + fullmove;

        return output;
    }

    //update the fullmove if it's back to white. Update the halfmove if required
    private void updateFENTurns(Boolean halfmoveUpdate){
        whitesTurn=!whitesTurn;
        if (whitesTurn){
            fullmove+=1;
        }
        if(halfmoveUpdate){
            halfmove+=1;
        }
    }

    //add the correct castling notation to the output FEN String
    private void castlingToString() {
        if (!whiteCastling[0] && !whiteCastling[1] && !blackCastling[0] && !blackCastling[1]){
            output+="-";
        }
        if (whiteCastling[0]){
            output+="K";
        }
        if (whiteCastling[1]){
            output+="Q";
        }
        if (blackCastling[0]){
            output+="k";
        }
        if (blackCastling[1]){
            output+="q";
        }

        output+= " ";
    }

    //return the correct enPassant notation
    private String enPassantToString(Piece piece){

        String output = "";

        if (isNull(piece)){
            output += "-";
            return output;
        }

        switch (piece.getPosition().getFile()) {
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

        output+=8-(piece.getPosition().getRank());

        return output;

    }





}
