package Contract;

import Model.Pieces.PieceType;
import View.PositionView;

public interface Contract {

    interface Listener {


        void handlePieceClicked(PositionView positionView);

        void handleCircleClicked(PositionView positionView);

        void onPromotionPieceDecided(String string);

    }

    interface Observer {

        void update();

        void displayPromotionPopup();

        void gameOver(boolean isStaleMate, boolean isWhite);

    }
}
