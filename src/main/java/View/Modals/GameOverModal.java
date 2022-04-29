package View.Modals;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class GameOverModal extends Stage{

    private boolean newGame = false;

    public GameOverModal(boolean isADraw, String string){
        gameOverStage = new Stage();
        gameOverStage.setScene(setUpStage(gameOverStage,isADraw, string));
    }

    public void show(){
        gameOverStage.showAndWait();
    }


    private Scene setUpStage(Stage gameOverStage, boolean isADraw, String string){
        Image icon = new Image("chess-icon.png");
        this.getIcons().add(icon);
        this.setTitle("GAME OVER");
        this.setWidth(500);
        this.setHeight(150);
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.UTILITY);
        StackPane root = new StackPane();
        root.setBackground(Background.EMPTY);

        if (isADraw) {
            addDrawDisplay(root, string);
        } else {
            addCheckMateDisplays(root, string);
        }

        addButton(root);


        this.setScene(new Scene(root, Color.BROWN));
    }

    private void addDrawDisplay(StackPane root, String string){
        Text text = new Text(string);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(36));
        root.getChildren().add(text);

        Text text2 = new Text("DRAW by");
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setFont(Font.font(24));
        root.getChildren().add(text2);

        StackPane.setAlignment(text2, Pos.TOP_CENTER);
    }

    private void addCheckMateDisplays(StackPane root, String string){
        ImageView imageView1 = createImageView(Objects.equals(string, "WHITE"));
        root.getChildren().add(imageView1);
        StackPane.setAlignment(imageView1, Pos.CENTER_LEFT);

        ImageView imageView2 = createImageView(Objects.equals(string, "WHITE"));
        root.getChildren().add(imageView2);
        StackPane.setAlignment(imageView2, Pos.CENTER_RIGHT);

        String output = string;
        output += " WINS!";

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

    private void addButton(StackPane root){
        Button button = new Button("Play Again");
        button.setOnMouseClicked(__ -> {
            this.newGame = true;
            this.close();
        });
        root.getChildren().add(button);
        StackPane.setAlignment(button,Pos.BOTTOM_CENTER);
    }

    public boolean isNewGame() {
        return newGame;
    }
}
