package Contract;

import View.PositionView;

public interface Contract {

    interface Listener {

        void handlePieceClicked(PositionView positionView);

        void handleCircleClicked(PositionView positionView);

        void onPromotionPieceDecided(String string);

        void newGame();

        void stockFish();
    }

    interface Observer {

        void updateView();

        void displayPromotionPopup();

        void gameOver(boolean isStaleMate, boolean isWhite);

    }
}
