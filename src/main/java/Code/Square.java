package Code;

import Code.pieces.Piece;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square extends Group {

    private Piece piece = null;
    private final double xCoordinate;
    private final double yCoordinate;



    public Square(Color color, double width, double height, double x, double y){
        xCoordinate = x;
        yCoordinate = y;

        Rectangle rectangle =  new Rectangle();
        rectangle.setFill(color);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setX(xCoordinate);
        rectangle.setY(yCoordinate);


        getChildren().add(rectangle);
    }

    public void addPiece(Piece piece){
        this.piece = piece;
        this.piece.setPosition(xCoordinate,yCoordinate);
        getChildren().add(this.piece.getImage());
    }

}
