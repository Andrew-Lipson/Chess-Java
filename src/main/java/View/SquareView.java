package View;

import Contract.Contract;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SquareView extends Group {

    private final Contract.Listener listener;
    private final PositionView positionView;
    private final Rectangle rectangle;
    private final int file;
    private final int rank;

    private final double xCoordinate;
    private final double yCoordinate;
    private final ImageView imageview = new ImageView();
    private final Circle circle = new Circle();
    private final double heightWidth = 80;
    private Character FENPiece = 'x';
    private SquareColour color;

    /**
     * Intilising the SquareView for X & Y coordinates, the colour, the shape (rectangle) and size
     * seeting the ImageView and the Circle
     * 
     * @param file
     * @param rank
     * @param listener
     */
    public SquareView(int file, int rank, Contract.Listener listener, boolean inverted) {

        this.file = file;
        this.rank = rank;
        this.listener = listener;
        this.positionView = new PositionView(file,rank);
        this.rectangle = new Rectangle();

        int startingCoordinate = inverted?570:10;
        double changingCoordinate = inverted?(-heightWidth):heightWidth;

        this.xCoordinate = (startingCoordinate + changingCoordinate * file);
        this.yCoordinate = (startingCoordinate + changingCoordinate * rank);

        initialiseRectangle();
        initialiseImageView();
        initialiseCircle();
    }

    public void initialiseRectangle() {
        color =
            (this.rank + this.file) % 2 == 0
                ? SquareColour.EVEN
                : SquareColour.ODD;
        changeSquareColour(color);
        this.rectangle.setWidth(heightWidth);
        this.rectangle.setHeight(heightWidth);
        this.rectangle.setX(xCoordinate);
        this.rectangle.setY(yCoordinate);
        getChildren().add(this.rectangle);
    }


    /**
     * Sets the ImageView's X & Y coordinates and it's height and width
     */
    public void initialiseImageView(){
        this.imageview.setX(xCoordinate);
        this.imageview.setY(yCoordinate);
        this.imageview.setFitHeight(heightWidth);
        this.imageview.setFitWidth(heightWidth);
        imageview.setPickOnBounds(true);
        imageview.setOnMouseClicked(__ -> {
        this.listener.handlePieceClicked(this.positionView);
        });
    }

    /**
     * Sets the Circle's X & Y coordinates, it's radius and colour
     */
    public void initialiseCircle() {
        circle.setRadius(20);
        circle.setCenterX(xCoordinate+40);
        circle.setCenterY(yCoordinate+40);
        circle.setFill(Color.DARKBLUE);
        circle.setOpacity(0.5);
        circle.setPickOnBounds(true);
        circle.setOnMouseClicked(__ -> this.listener.handleCircleClicked(this.positionView));
    }

    /**
     * Change the colour of the square to squareColour
     *
     * @param squareColour is the colour the Square will change to
     */
    public void changeSquareColour(SquareColour squareColour){
        this.rectangle.setFill(squareColour.getSquareColor());
    }

    /**
     * Change the colour of the square to the Square's normal colour
     */
    public void changeSquareColour(){
        if(this.rectangle.getFill()==SquareColour.CLICKED.getSquareColor()){
            this.rectangle.setFill(color.getSquareColor());
        }
    }

    /**
     * Update the FENPiece and then get the correct image from the resouces and add/remove the imageview from the Group.
     * 
     * @param charactor
     */
    public void addPiece(Character charactor) {
        // check if the charactor is the same. If it is then return
        if(charactor == this.FENPiece) {
            changeSquareColour(color);
            return;
        }
        // check if there was no piece previous on the spot. If so then add imageview to the Group
        if (this.FENPiece == 'x') {
            changeSquareColour(SquareColour.MOVED);
            getChildren().add(imageview);
        }
        this.FENPiece = charactor;
        // if there is no longer a piece on this square, remove the imageview and return
        if (charactor == 'x') {
            getChildren().remove(imageview);
            changeSquareColour(SquareColour.MOVED);
            return;
        }
        boolean isWhite = true;
        if(Character.isLowerCase(charactor)) {
            isWhite = false;
        }

        changeSquareColour(SquareColour.MOVED);
        Image image = new Image(getPNGString(Character.toLowerCase(charactor), isWhite));
        this.imageview.setImage(image);
    }


    /**
     * Returns the correct uri (String) that the piece requires
     * 
     * @param character
     * @param isWhite
     * @return uri of the filename
     */
    public static String getPNGString(Character character, boolean isWhite) {
        String output = "";
        switch (character) {
            case 'k':
                output += "King";
                break;
            case 'q':
                output += "Queen";
                break;
            case 'r':
                output += "Rook";
                break;
            case 'n':
                output += "Knight";
                break;
            case 'b':
                output += "Bishop";
                break;
            case 'p':
                output += "Pawn";
                break;
        }

        output += "-";
        output += isWhite ? "White" : "Black";
        output+=".png";
        return output;
    }

    /**
     * Add Circle to the Group
     */
    public void addCircle(){
        this.getChildren().add(circle);
    }

    /**
     * Remove Circle from the Group
     */
    public void removeCircle(){
        this.getChildren().remove(circle);
    }
}

