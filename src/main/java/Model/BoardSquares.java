package Model;

public class BoardSquares {

    Square[][] squares = new Square[8][8];

    public BoardSquares() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squares[file][rank] = new Square(file, rank);
            }
        }
    }
    // Return a Square from the specific File and Rank
    public Square getSquare(Integer file, Integer rank){
        return squares[file][rank];
    }



}
