package Contract;

import View.PositionView;

public interface Contract {

    interface Listener {

        public void handlePieceClicked(PositionView positionView);

        public void handleCircleClicked(PositionView positionView);

    }

    interface Observer {

        public void update();

    }
}
