package View.Menu;

import Contract.Contract;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

public class ChooseColourRoot extends Group {

    private final Contract.Listener listener;

    public ChooseColourRoot(Contract.Listener listener){
        this.listener = listener;
        populateScene();
    }

    public void populateScene() {
        Button blackButton = new Button();
        Button whiteButton = new Button();
        blackButton.setGraphic(getPawnIcon(false));
        whiteButton.setGraphic(getPawnIcon(true));
        blackButton.setOnMouseClicked(__ -> {
            this.listener.newGame(true, true);
        });
        whiteButton.setOnMouseClicked(__ -> {
            this.listener.newGame(true,false);
        });

        placeButton(blackButton, 0);
        placeButton(whiteButton, 1);
    }

    private ImageView getPawnIcon(boolean isWhite){
        String string = "Pawn-";
        string+=isWhite?"White":"Black";
        string+=".png";
        Image image = new Image(string);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(80);
        imageView.setFitWidth(80);
        return imageView;
    }

    private void placeButton(Button button, int placement){
        button.setLayoutX(150+placement*200);
        button.setLayoutY(300);
        button.setFont(new Font(24));
        this.getChildren().add(button);
    }
}
