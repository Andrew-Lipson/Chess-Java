package Model;

import Model.Contract.Observer;
import Model.pieces.*;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Character.isDigit;
import static java.lang.Math.abs;
import static java.util.Objects.isNull;

public class Board {

    private final BoardSquares boardSquares;
    private final ArrayList<Piece> whitePieces = new ArrayList<Piece>();
    private final ArrayList<Piece> blackPieces = new ArrayList<Piece>();
    private boolean[] whiteCastling;
    private boolean[] blackCastling;
    private boolean whitesTurn;
    private final Observer _observer;
    private ArrayList<Piece> enPassantAvailablePieces = new ArrayList<Piece>();
    private FEN fen;
    private int halfmove = 0;
    private int fullmove = 1;

    public Board(Observer observer) {
        boardSquares = new BoardSquares();
        createPieces(whitePieces, true);
        createPieces(blackPieces, false);
        whiteCastling = new boolean[]{true, true};
        blackCastling = new boolean[]{true, true};
        this._observer = observer;
        whitesTurn = true;
        fen = new FEN(createFirstFENPosition());

    }

    public Board(Observer observer, String fenString) {
        boardSquares = new BoardSquares();
        decodeFEN(fenString);
        this._observer = observer;
    }

    private void decodeFEN(String fenString){
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

        this.fen = new FEN(createFirstFENPosition());

        //str[1] whose turn
        whitesTurn = Objects.equals(str[1], "w");

        //str[2] is castling
        chars = str[2].toCharArray();
        whiteCastling = new boolean[]{false, false};
        blackCastling = new boolean[]{false, false};
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
        this.fen.setEnPassantPieceString(str[3]);
        chars = str[3].toCharArray();
        int enPassantFile = chars[0];
        enableEnPassant(new Position(enPassantFile-97,9-Character.getNumericValue(chars[1])), false);

        // str[4] is halfmove
        this.halfmove = Integer.parseInt(str[4]);

        // str[5] is fullmove
        this.fullmove = Integer.parseInt(str[5]);

    }

    /**
     * Create all the piece objects and put them on the board in the correct position
     * 
     * @param pieces
     * @param isWhite
     */
    private void createPieces(ArrayList<Piece> pieces, boolean isWhite) {
        int rank = isWhite ? 6 : 1;
        for(int i = 0; i < 8; i++){
            pieces.add(new Piece(isWhite, PieceType.Pawn));
            addPiece(new Position(i,rank), pieces.get(i));
        }

        rank = isWhite ? 7 : 0;

        pieces.add(new Piece(isWhite, PieceType.Rook));
        pieces.add(new Piece(isWhite, PieceType.Knight));
        pieces.add(new Piece(isWhite, PieceType.Bishop));
        pieces.add(new Piece(isWhite, PieceType.Queen));
        pieces.add(new Piece(isWhite, PieceType.King));
        pieces.add(new Piece(isWhite, PieceType.Bishop));
        pieces.add(new Piece(isWhite, PieceType.Knight));
        pieces.add(new Piece(isWhite, PieceType.Rook));

        for(int i = 8; i < 16; i++) {
            addPiece(new Position(i-8, rank), pieces.get(i));
        }
    }

    /**
     * basically create a copy of boardSquares to help create the first FEN
     * 
     * @return 2d array of the first fen position
     */
    private Piece[][] createFirstFENPosition() {
        Piece[][] piece2DArray = new Piece[8][8];
        for (int rank = 0; rank < 8; rank++) {
            piece2DArray[rank] = boardSquares.getRankPiece(rank);
        }
        return piece2DArray;
    }

    /**
     * Add the piece to the specific Square on the file and rank provided
     * 
     * @param position
     * @param piece
     */
    private void addPiece(Position position, Piece piece) {
        boardSquares.addPiece(position, piece);
    }

    /**
     * remove the piece on the specific position
     * 
     * @param position
     */
    private void removePiece(Position position) {
        boardSquares.removePiece(position);
    }

    /**
     * Change the whitesTurn
     */
    private void nextTurn() {
        whitesTurn = !whitesTurn;
        fullmove+=whitesTurn?1:0;
    }

    /**
     * Move the piece from the previousPosition to the newPosition.
     * Also checking for en Passant and promotion. Then update the view
     * 
     * @param previousPosition
     * @param newPosition
     */
    public void movePieces(Position previousPosition, Position newPosition) {
        fen.setEnPassantPiece(null);
        Piece piece = boardSquares.getPiece(previousPosition);

        // if en Passant occured, return as the checkForEnPassant function did everything necessary
        if(checkForEnPassant(previousPosition, newPosition, piece)) {
            return;
        }

        disableEnPassant();
        this.removePiece(previousPosition);
        this.addPiece(newPosition,piece);
        // check if a pawn double moved and do what is needed
        if(piece.getPieceType() == PieceType.Pawn && abs(previousPosition.getRank() - newPosition.getRank()) == 2) {
            fen.setEnPassantPiece(new Position(newPosition.getFile(),newPosition.getRank()));
            enableEnPassant(newPosition, piece.getIsWhite());
        }

        checkForPromotion(newPosition.getRank(), piece);

        nextTurn();
        updateView(newPosition, previousPosition);

    }

    /**
     * Update any pawn that can en Passant next turn and then add those pieces that
     * are able to en passant on the next turn to the enPassantAvailablePieces list
     * 
     * @param position
     * @param isWhite
     */
    
    private void enableEnPassant(Position position, boolean isWhite) {
        int tempFile;
        for (int iFile = -1; iFile < 2; iFile+=2) {
            tempFile = position.getFile() + iFile;
            if (tempFile >= 0 && tempFile < 8){
                Piece piece = boardSquares.getPiece(new Position(tempFile,position.getRank()));
                if(!isNull(piece) && piece.getPieceType() == PieceType.Pawn && piece.getIsWhite() != isWhite){
                    piece.setEnPassantAvailableToTakeFile(position.getFile());
                    enPassantAvailablePieces.add(piece);
                }
            }
        }
    }

    /**
     * check to see if the piece being moved did En Passant
     * 
     * @param previousPosition
     * @param newPosition
     * @param piece
     * @return boolean indicating if enPassant available
     */
    private boolean checkForEnPassant(Position previousPosition, Position newPosition, Piece piece) {
        if (piece.getPieceType() == PieceType.Pawn) {
            if(enPassantAvailablePieces.contains(piece)) {
                if(Objects.equals(piece.getEnPassantAvailableToTakeFile(), newPosition.getFile())) {
                    this.removePiece(previousPosition);
                    this.removePiece(new Position(newPosition.getFile(), previousPosition.getRank()));
                    this.addPiece(newPosition, piece);
                    nextTurn();
                    disableEnPassant();
                    updateView(newPosition, previousPosition);

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Remove the availability to en Passant from all the necessary pieces
     */
    private void disableEnPassant() {
        for (Piece piece:enPassantAvailablePieces) {
            piece.setEnPassantAvailableToTakeFile(null);
        }
        enPassantAvailablePieces.clear();
    }

    /**
     * Check to see if a pawn has made it to the other side of the board. If so call the promotion function
     * 
     * @param newRank
     * @param piece
     */
    private void checkForPromotion(int newRank, Piece piece) {
        if(piece.getPieceType() != PieceType.Pawn) {
            return;
        }
        int rank = piece.getIsWhite() ? 0 : 7;
        if(newRank != rank){
            return;
        }
        piece.pawnPromotion(PieceType.Queen);
    }


    /**
     * Update the FEN and then update the view
     * 
     * @param newPosition
     * @param previousPosition
     */
    public void updateView(Position newPosition, Position previousPosition) {
        fen.updateFENPosition(boardSquares.getRankPiece(newPosition.getRank()), newPosition.getRank());
        if (previousPosition.getRank() != newPosition.getRank()){
            fen.updateFENPosition(boardSquares.getRankPiece(previousPosition.getRank()), previousPosition.getRank());
        }
        _observer.update();
    }


    /**
     * Call chooseMove in Moves class
     * 
     * @param position
     * @param isWhite
     * @param board
     * @param pieceType
     * @return list of possible moves
     */
    public ArrayList<Position> chooseMove(Position position, boolean isWhite, Board board, PieceType pieceType) {
        return Moves.chooseMove(position, isWhite, board, pieceType);
    }

    public String getCompleteFEN() {
        return fen.createCompleteFEN(whitesTurn, whiteCastling, blackCastling, halfmove, fullmove);
    }

    public Piece getPiece(Position position) {
        return boardSquares.getPieceClone(position);
    }

    public boolean getWhitesTurn() {
        return whitesTurn;
    }
}