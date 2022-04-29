package Contract;

import View.Board.PositionView;

public interface Contract {

    interface Observer {

        /**
         * Let the Observer know that the game has been updated
         */
        void update();

        /**
         * Let the Observer know that the game requires a promotion option
         */
        void requiresPromotionOptions();

        /**
         * Let the Observer know that the game has ended
         *
         * @param isADraw Did the game end in a draw
         * @param string If there was a win, who won. If there was a draw, what was the reason for the draw
         */
        void gameOver(boolean isADraw, String string);

    }

    interface Listener {

        /**
         * Listener to handle required actions when a Piece is clicked
         *
         * @param positionView What is the position of the Piece that got clicked
         */
        void handlePieceClicked(PositionView positionView);

        /**
         * Listener to handle required actions when a Circle is clicked
         *
         * @param positionView What is the position of the Circle that got clicked
         */
        void handleCircleClicked(PositionView positionView);

        /**
         * Listener to handle required action when a promotion decision is made
         *
         * @param string fen representation of the promtotion Piece
         */
        void handlePromotionDecision(String string);

        /**
         * Listener to create a new game
         *
         * @param singlePlayer Is it a single player match, or 2 player
         * @param computerIsWhite Is the computer playing as white
         */
        void newGame(boolean singlePlayer, Boolean computerIsWhite);
    }


}
