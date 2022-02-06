package Code.pieces;

import javafx.scene.image.ImageView;

public abstract class Piece {
    private ImageView imageview;
    private Boolean white;

    public Piece(Boolean white){

        this.white = white;

        this.imageview = new ImageView(getPNGString(this.getClass().getSimpleName()));
        this.imageview.setFitHeight(80);
        this.imageview.setFitWidth(80);

    }

    public String getPNGString(String piece){
        String output = piece + "-";
        if(white){
            output+="White";
        }
        else{
            output+="Black";
        }
        output+=".png";
        return output;
    }
    public void setPosition(double x, double y){
        imageview.setX(x);
        imageview.setY(y);
    }

    public ImageView getImage(){
        return imageview;
    }
}
