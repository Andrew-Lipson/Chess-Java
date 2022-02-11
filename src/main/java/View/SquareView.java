package View;

import Model.pieces.Piece;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SquareView extends Group {

    private final double xCoordinate;
    private final double yCoordinate;
    private final ImageView imageview = new ImageView();
    private final Circle circle = new Circle();
    private final double heightWidth = 80;

    //Intilising the SquareView for X & Y coordinates, the colour, the shape (rectangle) and size
    //setting the ImageView and the Circle
    public SquareView(int file, int rank){
        this.xCoordinate = (10 + heightWidth * file);
        this.yCoordinate = (10 + heightWidth * rank);

        Rectangle rectangle =  new Rectangle();
        if ((rank + file) % 2 == 0) {
            rectangle.setFill(Color.BEIGE);
        }
        else {
            rectangle.setFill(Color.GREY);
        }
        rectangle.setWidth(heightWidth);
        rectangle.setHeight(heightWidth);
        rectangle.setX(xCoordinate);
        rectangle.setY(yCoordinate);
        getChildren().add(rectangle);

        setImageview();
        setCircle();
    }

    //sets the ImageView's X & Y coordinates and it's height and width
    public void setImageview(){
        this.imageview.setX(xCoordinate);
        this.imageview.setY(yCoordinate);
        this.imageview.setFitHeight(heightWidth);
        this.imageview.setFitWidth(heightWidth);
        //getChildren().add(imageview);
    }

    //sets the Circle's X & Y coordinates, it's radius and colour
    public void setCircle(){
        circle.setRadius(20);
        circle.setCenterX(xCoordinate+40);
        circle.setCenterY(yCoordinate+40);
        circle.setFill(Color.DARKBLUE);
        circle.setOpacity(0.5);
    }

    //get's the correct image from the resouces. add's the Image to the ImageView
    //removes any previous imageview (incase it's a capture) and then add the imageview to the group
    public void addPiece(Piece piece){
        Image image = new Image(getPNGString(piece));
        this.imageview.setImage(image);
        getChildren().remove(imageview);
        getChildren().add(imageview);
    }

    //remove a piece from this SquareView
    public void removePiece(){
        getChildren().remove(this.imageview);
    }

    //returns the correct url (String) that the piece requires
    public String getPNGString(Piece piece){
        String output = piece.getPieceType().toString() + "-";
        if(piece.getIsWhite()){
            output+="White";
        }
        else{
            output+="Black";
        }
        output+=".png";
        return output;
    }

    //Add Circle to the Group
    public void addCircle(){
        this.getChildren().add(circle);
    }

    //Remove Circle from the Group
    public void removeCircle(){
        this.getChildren().remove(circle);
    }

    public ImageView getImageview() {
        return imageview;
    }
    public Circle getCircle() {return circle;}
}

