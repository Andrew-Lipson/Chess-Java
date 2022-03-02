package Contract;

import Model.Pieces.PieceType;
import View.PositionView;

public interface Contract {

    interface Listener {


        void handlePieceClicked(PositionView positionView);

        void handleCircleClicked(PositionView positionView);

    }

    interface Observer {

        void update();

        String pawnPromotion(boolean isWhite);

        void gameOver(boolean isStaleMate, boolean isWhite);

    }
}
