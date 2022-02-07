package View;

import Model.Board;
import Model.BoardSquares;
import Model.pieces.Piece;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import Controller.*;

public class SquareView extends Group {

    private final double xCoordinate;
    private final double yCoordinate;
    private ImageView imageview = new ImageView();
    private Piece piece = null;


    public SquareView(int rank, int file){
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
    }

    public void addPiece(Piece piece){
        this.piece = piece;
        Image image = new Image(getPNGString(piece));
        this.imageview.setImage(image);
        getChildren().add(imageview);
    }

    public void removePiece(){
        getChildren().remove(this.imageview);
    }

    public String getPNGString(Piece piece){
        String output = piece.getPieceType() + "-";
        if(piece.getWhite()){
            output+="White";
        }
        else{
            output+="Black";
        }
        output+=".png";
        return output;
    }

    private Piece getPiece(){
        return piece;
    }

    public ImageView getImageview() {
        return imageview;
    }
}
