package Model.pieces;


import Model.Position;

public class Piece {
    private Boolean isWhite;
    private Position position;
    private PieceType pieceType;
    private Integer enPassantAvailableToTakeFile = null;

    public Piece(Boolean isWhite, PieceType pieceType){

        this.isWhite = isWhite;
        this.pieceType = pieceType;
    }

    //Use to Clone a previous Piece
    public Piece(Piece piece){

        this.isWhite = piece.getIsWhite();
        this.position = piece.getPosition();
        this.pieceType = piece.getPieceType();
        this.enPassantAvailableToTakeFile = piece.getEnPassantAvailableToTakeFile();
    }

    //add the file and rank position to the Piece
    public void setPosition(Position position){
        this.position = position;
    }

    //Set the enPassantAvailableToTakeFile to the File that the piece will move to after the en Passant
    public void setEnPassantAvailableToTakeFile(Integer file){
        this.enPassantAvailableToTakeFile = file;
    }

    // Change the PieceType for when a Pawn get promoted
    public void pawnPromotion(PieceType pieceType){
        this.pieceType = pieceType;
    }

    public Position getPosition() {
        return position;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public String getFenRepresentation(){
        return pieceType.getFenRepresentation(isWhite);
    }

    public Boolean getIsWhite() {
        return isWhite;
    }

    public Integer getEnPassantAvailableToTakeFile(){
        return enPassantAvailableToTakeFile;
    }
}
