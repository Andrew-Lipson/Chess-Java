package Utility;

import Model.Board;
import Model.Position;
import Model.Pieces.Piece;
import Model.Pieces.PieceType;

import static java.util.Objects.nonNull;

public class CreateBoard {

    public static Board firstPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p")},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board secondPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board thirdPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null, null, null, null, null, null, null},
                {null, white("p"), null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), null, white("p"), white("p"), white("p"), white("p"), white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board fourthPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), null, black("p"), black("p"), black("p"), black("p"), black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null,  black("p"), null, null, null, null, null},
                {null, white("p"), null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), null, white("p"), white("p"), white("p"), white("p"), white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board fifthPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), null, black("p"), black("p"), black("p"), black("p"), black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null,  black("p"), null, null, null, null, null},
                {null, white("p"), null, white("p"), null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), null, white("p"), null, white("p"), white("p"), white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board sixthPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), null, black("p"), null, black("p"), black("p"), black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null,  black("p"), null, black("p"), null, null, null},
                {null, white("p"), null, white("p"), null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), null, white("p"), null, white("p"), white("p"), white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board seventhPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), null, black("p"), null, black("p"), black("p"), black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null,  black("p"), null, black("p"), null, null, null},
                {null, white("p"), null, white("p"), null, white("p"), null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), null, white("p"), null, white("p"), null, white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board eighthPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), null, black("p"), null, black("p"), null, black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null,  black("p"), null, black("p"), null, black("p"), null},
                {null, white("p"), null, white("p"), null, white("p"), null, null},
                {null, null, null, null, null, null, null, null},
                {white("p"), null, white("p"), null, white("p"), null, white("p"), white("p")},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board ninthPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), black("n"), black("b"), black("q"), black("k"), black("b"), black("n"), black("r")},
                {null, black("p"), null, black("p"), null, black("p"), null, black("p")},
                {null, null, null, null, null, null, null, null},
                {black("p"), null,  black("p"), null, black("p"), null, black("p"), null},
                {null, white("p"), null, white("p"), null, white("p"), null, white("p")},
                {null, null, null, null, null, null, null, null},
                {white("p"), null, white("p"), null, white("p"), null, white("p"), null},
                {white("r"), white("n"), white("b"), white("q"), white("k"), white("b"), white("n"), white("r")}
        });
    }

    public static Board tenthPosition() {
        return makeBoard(new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        });
    }

    public static Board eleventhPosition() {
        return makeBoard(new Piece[][]{
                {black("r"), null, null, black("q"), null, black("r"), black("k"), null},
                {black("p"), black("p"), null, null, black("p"), black("p"), black("b"), black("p")},
                {null, null, black("p"), null, null, black("n"), black("p"), null},
                {null, null, null, null, null, null, white("b"), null},
                {null, null, null, white("p"), white("p"), null, black("b"), null},
                {white("q"), null, white("p"), null, null, white("n"), null, null},
                {white("p"), null, null, null, null, white("p"), white("p"), white("p")},
                {null, null, null, white("r"), white("k"), white("b"), null, white("r")}
        });
    }

    public static Board twelfthPosition() {
        return makeBoard(new Piece[][]{
                {black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p")},
                {black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p")},
                {black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p")},
                {black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p"), black("p")},
                {white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p")},
                {white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p")},
                {white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p")},
                {white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p"), white("p")}
        });
    }

    public static Board thirteenthPosition() {
        return makeBoard(new Piece[][]{
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")},
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")},
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")},
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")},
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")},
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")},
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")},
                {white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q"), white("q")}
        });
    }

    public static Board fourteenthPosition() {
        return makeBoard(new Piece[][]{
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")},
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")},
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")},
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")},
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")},
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")},
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")},
                {black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k"), black("k")}
        });
    }

    static Board makeBoard(Piece[][] piecePositions){
        Board board = new Board();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                if (nonNull( piecePositions[rank][file])){
                    board.addPiece(new Position(file,rank),piecePositions[rank][file]);
                }
            }
        }
        return board;
    }


    static Piece white(String fenRep) {
        return new Piece(true, PieceType.getPieceType(fenRep));
    }

    static Piece black(String fenRep) {
        return new Piece(false, PieceType.getPieceType(fenRep));
    }
}
