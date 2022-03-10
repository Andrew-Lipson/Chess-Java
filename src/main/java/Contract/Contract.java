package Contract;

import View.PositionView;

public interface Contract {

    interface Listener {

        void handlePieceClicked(PositionView positionView);

        void handleCircleClicked(PositionView positionView);

        void onPromotionPieceDecided(String string);

        void showMainMenu();

        void numberOfPlayersDecision(boolean singlePlayer);

        void colourToPlayAsDecision(boolean isWhite);
    }

    interface Observer {

        void update();

        void displayPromotionPopup();

        void gameOver(boolean isStaleMate, boolean isWhite);

    }
}
