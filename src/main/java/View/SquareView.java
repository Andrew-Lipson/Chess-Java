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
    private ImageView imageview = new ImageView();
    private Circle circle = new Circle();
//    private Piece piece = null;


    public SquareView(int file, int rank){
        double heightWidth = 80;

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
        this.imageview.setX(xCoordinate);
        this.imageview.setY(yCoordinate);
        this.imageview.setFitHeight(heightWidth);
        this.imageview.setFitWidth(heightWidth);

        getChildren().add(rectangle);
        setCircle();
    }

    public void addPiece(Piece piece){
        //this.piece = piece;
        Image image = new Image(getPNGString(piece));
        this.imageview.setImage(image);
        getChildren().remove(imageview);
        getChildren().add(imageview);
    }

    public void removePiece(){
        //this.piece = null;
        getChildren().remove(this.imageview);
    }

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

//    private Piece getPiece(){
//        return piece;
//    }

    public ImageView getImageview() {
        return imageview;
    }

    public void setCircle(){
        circle.setRadius(20);
        circle.setCenterX(xCoordinate+40);
        circle.setCenterY(yCoordinate+40);
        circle.setFill(Color.DARKBLUE);
        circle.setOpacity(0.5);
    }

    public Circle getCircle() {
        return circle;
    }

    public void addCircle(){
        this.getChildren().add(circle);
    }

    public void removeCircle(){
        this.getChildren().remove(circle);
    }

}

