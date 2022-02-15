package View;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import Contract.*;

public class SquareView extends Group {

    private final Contract.Controller controller;
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

    //Intilising the SquareView for X & Y coordinates, the colour, the shape (rectangle) and size
    //setting the ImageView and the Circle
    public SquareView(int file, int rank, Contract.Controller controller){

        this.file = file;
        this.rank = rank;
        this.controller = controller;
        this.positionView = new PositionView(file,rank);
        this.rectangle = new Rectangle();

        this.xCoordinate = (10 + heightWidth * file);
        this.yCoordinate = (10 + heightWidth * rank);

        initialiseRectangle();
        initialiseImageView();
        initialiseCircle();
    }

    public void initialiseRectangle() {
        this.rectangle.setFill(
            (this.rank + this.file) % 2 == 0
                ? Color.BEIGE
                : Color.GREY
        );
        this.rectangle.setWidth(heightWidth);
        this.rectangle.setHeight(heightWidth);
        this.rectangle.setX(xCoordinate);
        this.rectangle.setY(yCoordinate);
        getChildren().add(this.rectangle);
    }

    //sets the ImageView's X & Y coordinates and it's height and width
    public void initialiseImageView(){
        this.imageview.setX(xCoordinate);
        this.imageview.setY(yCoordinate);
        this.imageview.setFitHeight(heightWidth);
        this.imageview.setFitWidth(heightWidth);
        imageview.setPickOnBounds(true);
        imageview.setOnMouseClicked(__ -> this.controller.handlePieceClicked(this.positionView));
    }

    //sets the Circle's X & Y coordinates, it's radius and colour
    public void initialiseCircle(){
        circle.setRadius(20);
        circle.setCenterX(xCoordinate+40);
        circle.setCenterY(yCoordinate+40);
        circle.setFill(Color.DARKBLUE);
        circle.setOpacity(0.5);
        circle.setPickOnBounds(true);
        circle.setOnMouseClicked(__ -> this.controller.handleCircleClicked(this.positionView));
    }

    //Update the FENPiece and then get the correct image from the resouces and add/remove the imageview from the Group.
    public void addPiece(Character charactor){
        //check if the charactor is the same. If it is then return
        if(charactor == this.FENPiece){
            return;
        }
        //check if there was no piece previous on the spot. If so then add imageview to the Group
        if (this.FENPiece == 'x'){
            getChildren().add(imageview);
        }
        this.FENPiece = charactor;
        //if there is no longer a piece on this square, remove the imageview and return
        if (charactor == 'x'){
            getChildren().remove(imageview);
            return;
        }
        boolean isWhite = true;
        if(Character.isLowerCase(charactor)){
            isWhite = false;
        }

        Image image = new Image(getPNGString(Character.toLowerCase(charactor), isWhite));
        this.imageview.setImage(image);
    }


    //returns the correct url (String) that the piece requires
    public String getPNGString(Character character, boolean isWhite){
        String output = "";
        switch (character){
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
        if(isWhite){
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

