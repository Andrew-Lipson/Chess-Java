package Contract;

import View.Board.PositionView;

public interface Contract {

    interface Observer {

        void update();

        void displayPromotionPopup();

        void gameOver(boolean isStaleMate, boolean isWhite);

    }

    interface Listener {

        void handlePieceClicked(PositionView positionView);

        void handleCircleClicked(PositionView positionView);

        void onPromotionPieceDecided(String string);

        void newGame(boolean singlePlayer, Boolean computerIsWhite);
    }


}
