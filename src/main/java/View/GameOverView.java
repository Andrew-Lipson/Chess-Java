package View;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameOverView {

    private final Stage gameOverStage;

    public GameOverView(boolean isStaleMate, boolean isWhite){
        gameOverStage = new Stage();
        gameOverStage.setScene(setUpStage(gameOverStage,isStaleMate, isWhite));
        showGameOverStage();
    }

    public void showGameOverStage(){
        gameOverStage.showAndWait();
    }


    private Scene setUpStage(Stage promotionStage, boolean isStaleMate, boolean isWhite){
        Image icon = new Image("chess-icon.png");
        promotionStage.getIcons().add(icon);
        promotionStage.setTitle("GAME OVER");
        promotionStage.setWidth(500);
        promotionStage.setHeight(150);
        promotionStage.setResizable(false);
        promotionStage.initModality(Modality.APPLICATION_MODAL);
        promotionStage.initStyle(StageStyle.UTILITY);
        StackPane root = new StackPane();
        Scene scene = new Scene(root, Color.BROWN);

        if (isStaleMate) {
            staleMate(root);
        } else {
            checkMate(root, isWhite);
        }

        return scene;
    }

    private void staleMate(StackPane root){
        Text text = new Text("STALEMATE");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(50));
        root.getChildren().add(text);
    }

    private void checkMate(StackPane root, boolean isWhite){


        ImageView imageView1 = createImageView(isWhite);
        root.getChildren().add(imageView1);
        StackPane.setAlignment(imageView1, Pos.CENTER_LEFT);

        ImageView imageView2 = createImageView(isWhite);
        root.getChildren().add(imageView2);
        StackPane.setAlignment(imageView2, Pos.CENTER_RIGHT);

        String output = isWhite?"WHITE ":"BLACK ";
        output += "WINS!";
        Text text = new Text(output);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(50));
        root.getChildren().add(text);


    }

    private ImageView createImageView(boolean isWhite){
        ImageView imageView = new ImageView(getPNGString(isWhite));
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        return imageView;
    }


    private String getPNGString(boolean isWhite) {
        String output = "King";
        output += "-";
        output += isWhite ? "White" : "Black";
        output+=".png";
        return output;
    }
}
