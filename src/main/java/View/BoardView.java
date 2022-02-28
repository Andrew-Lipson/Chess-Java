package View;

import Contract.Contract;

public class BoardView {

    SquareView[][] squareView = new SquareView[8][8];

    public BoardView(Contract.Listener listener) {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squareView[file][rank] = new SquareView(file, rank, listener);
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
