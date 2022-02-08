package Observer;

import Model.pieces.Piece;

public interface Observer {

    public void update(Integer previousFile, Integer previousRank, Piece piece);

}
