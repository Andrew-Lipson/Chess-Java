package View;

import Contract.*;

public class BoardSquaresView {

    SquareView[][] squareView = new SquareView[8][8];

    public BoardSquaresView(Contract.Controller controller) {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squareView[file][rank] = new SquareView(file, rank, controller);
            }
        }
    }

    /**
     * Return a SquareView from the specific File and Rank
     * 
     * @param position
     * @return the square view
     */
    public SquareView getSquareView(PositionView position) {
        return squareView[position.getFile()][position.getRank()];
    }
}
