package View.Contract;

import View.PositionView;

public interface Listener {

    public void onPieceClicked(PositionView positionView);

    public void onCircleClicked(PositionView positionView);
    
}
