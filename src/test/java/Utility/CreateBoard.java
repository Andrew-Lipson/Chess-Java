package Utility;

import Model.Board;
import Model.pieces.Piece;
import Model.pieces.PieceType;

public class CreateBoard {

    public static Board firstPosition() {
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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
        return new Board(new Piece[][]{
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

    static Piece white(String fenRep) {
        return new Piece(true, PieceType.getPieceType(fenRep));
    }

    static Piece black(String fenRep) {
        return new Piece(false, PieceType.getPieceType(fenRep));
    }
}
