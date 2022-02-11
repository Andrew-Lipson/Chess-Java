package View;

import Model.Position;

public class BoardSquaresView {

    SquareView[][] squareView = new SquareView[8][8];

    public BoardSquaresView() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squareView[file][rank] = new SquareView(file, rank);
            }
        }
    }

    // Return a SquareView from the specific File and Rank
    public SquareView getSquareView(Position position){
        return squareView[position.getFile()][position.getRank()];
    }
}
