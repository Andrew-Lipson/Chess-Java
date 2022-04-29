package View.Board;

import Contract.Contract;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class BoardScene extends Scene {

    SquareNode[][] squareView = new SquareNode[8][8];

    public BoardScene(Contract.Listener listener, boolean inverted) {
        super(new Group(), Color.BROWN);
        Group root = (Group) this.getRoot();
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                this.squareView[file][rank] = new SquareNode(file, rank, listener, inverted);
                root.getChildren().add(this.squareView[file][rank]);
            }
        }
    }

    /**
     * Return a SquareView from the specific File and Rank
     * 
     * @param position the position of the required square
     * @return the square view
     */
    public SquareNode getSquareView(PositionView position) {
        return this.squareView[position.getFile()][position.getRank()];
    }
}
