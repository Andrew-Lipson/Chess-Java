package View;

import Contract.Contract;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ChooseColourScene extends MainMenuScene {

    public ChooseColourScene(Contract.Listener listener) {
        super(listener);
    }

    @Override
    public void PopulateScene() {
        Button blackButton = new Button();
        Button whiteButton = new Button();
        blackButton.setGraphic(getPawnIcon(false));
        whiteButton.setGraphic(getPawnIcon(true));
        blackButton.setOnMouseClicked(__ -> {
            getListener().colourToPlayAsDecision(false);
        });
        whiteButton.setOnMouseClicked(__ -> {
            getListener().colourToPlayAsDecision(true);
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
}
