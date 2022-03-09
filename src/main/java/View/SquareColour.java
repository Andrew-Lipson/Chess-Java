package View;

import javafx.scene.paint.Color;

public enum SquareColour {
    EVEN(Color.BEIGE),
    ODD(Color.GREY),
    CLICKED(Color.RED),
    MOVED(Color.YELLOWGREEN);

    public final Color squareColor;

    SquareColour(Color squareColor) {
        this.squareColor = squareColor;
    }

    public Color getSquareColor() {
        return squareColor;
    }
}
