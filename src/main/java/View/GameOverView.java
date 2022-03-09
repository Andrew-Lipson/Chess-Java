package View;

import Contract.Contract;
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

public class GameOverView {

    private final Stage gameOverStage;
    private final Contract.Listener listener;

    public GameOverView(Contract.Listener listener, boolean isStaleMate, boolean isWhite){
        this.listener = listener;
        gameOverStage = new Stage();
        gameOverStage.setScene(setUpStage(gameOverStage,isStaleMate, isWhite));
        showGameOverStage();
    }

    public void showGameOverStage(){
        gameOverStage.showAndWait();
    }


    private Scene setUpStage(Stage gameOverStage, boolean isStaleMate, boolean isWhite){
        Image icon = new Image("chess-icon.png");
        gameOverStage.getIcons().add(icon);
        gameOverStage.setTitle("GAME OVER");
        gameOverStage.setWidth(500);
        gameOverStage.setHeight(150);
        gameOverStage.setResizable(false);
        gameOverStage.initModality(Modality.APPLICATION_MODAL);
        gameOverStage.initStyle(StageStyle.UTILITY);
        StackPane root = new StackPane();
        root.setBackground(Background.EMPTY);

        if (isStaleMate) {
            addText(root, "STALEMATE!");
        } else {
            addCheckMateDisplays(root, isWhite);
        }

        addButton(root);

        return new Scene(root, Color.BROWN);
    }

    private void addText(StackPane root, String string){
        Text text = new Text(string);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFont(Font.font(50));
        root.getChildren().add(text);
    }

    private void addCheckMateDisplays(StackPane root, boolean isWhite){
        ImageView imageView1 = createImageView(isWhite);
        root.getChildren().add(imageView1);
        StackPane.setAlignment(imageView1, Pos.CENTER_LEFT);

        ImageView imageView2 = createImageView(isWhite);
        root.getChildren().add(imageView2);
        StackPane.setAlignment(imageView2, Pos.CENTER_RIGHT);

        String output = isWhite?"WHITE ":"BLACK ";
        output += "WINS!";

        addText(root, output);
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
            this.listener.newGame();
            gameOverStage.close();
        });
        root.getChildren().add(button);
        StackPane.setAlignment(button,Pos.BOTTOM_CENTER);
    }
}
