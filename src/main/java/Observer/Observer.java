package Observer;

import Model.Position;
import Model.pieces.Piece;

public interface Observer {

    public void update(Position previousPosition, Piece piece);

}
