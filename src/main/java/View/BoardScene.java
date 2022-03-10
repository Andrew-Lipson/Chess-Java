package View;

import Contract.Contract;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class BoardScene extends Scene {

    SquareView[][] squareView = new SquareView[8][8];

    public BoardScene(Contract.Listener listener, boolean inverted) {
        super(new Group(), Color.BROWN);
        Group root = (Group) this.getRoot();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                squareView[file][rank] = new SquareView(file, rank, listener, inverted);
                root.getChildren().add(squareView[file][rank]);
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
