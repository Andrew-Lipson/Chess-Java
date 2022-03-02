package Contract;

import Model.Pieces.PieceType;
import View.PositionView;

public interface Contract {

    interface Listener {

        public void handlePieceClicked(PositionView positionView);

        public void handleCircleClicked(PositionView positionView);

        public void onPromotionPieceDecided(String string);

    }

    interface Observer {

        public void update();

        public void displayPromotionPopup();

    }
}
