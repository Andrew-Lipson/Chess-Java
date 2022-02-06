package Code;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Square {

    private Rectangle rectangle =  new Rectangle();

    public Square(Color color, double width, double height, double x, double y){
        rectangle.setFill(color);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setX(x);
        rectangle.setY(y);
    }

    public Rectangle getRectangle(){
        return rectangle;
    }

}
