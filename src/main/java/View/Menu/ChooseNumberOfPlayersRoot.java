package View.Menu;

import Contract.Contract;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class ChooseNumberOfPlayersRoot extends Group {

    private final Contract.Listener listener;

    public ChooseNumberOfPlayersRoot(Contract.Listener listener, MainMenuScene mainMenuScene) {
        this.listener = listener;
        Button p1Button = new Button(" 1 Player");
        Button p2Button = new Button(" 2 Player");
        p1Button.setOnMouseClicked(__ -> mainMenuScene.setChooseColourMenu());
        p2Button.setOnMouseClicked(__ -> this.listener.newGame(false,null));
        placeButton(p1Button,0);
        placeButton(p2Button,1);
    }


    /**
     * Place the button in the correct spot
     *
     * @param button button to be placed
     * @param placement the placement spot of the button
     */
    private void placeButton(Button button, int placement) {
        button.setLayoutX(150+placement*200);
        button.setLayoutY(300);
        button.setFont(new Font(24));
        this.getChildren().add(button);
    }
}
