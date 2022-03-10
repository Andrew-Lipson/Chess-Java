package View;

import Contract.Contract;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MainMenuScene extends Scene{

    private final Contract.Listener listener;

    public MainMenuScene(Contract.Listener listener ) {
        super(new Group(), Color.BROWN);
        this.listener = listener;
    }

    public void PopulateScene(){
        Button p1Button = new Button(" 1 Player");
        Button p2Button = new Button(" 2 Player");
        p1Button.setOnMouseClicked(__ -> {
            this.listener.numberOfPlayersDecision(true);
        });
        p2Button.setOnMouseClicked(__ -> {
            this.listener.numberOfPlayersDecision(false);
        });
        placeButton(p1Button,0);
        placeButton(p2Button,1);
    }

    void placeButton(Button button, int placement){
        button.setLayoutX(150+placement*200);
        button.setLayoutY(300);
        button.setFont(new Font(24));
        Group root = (Group) getRoot();
        root.getChildren().add(button);
    }

    public Contract.Listener getListener(){
        return listener;
    }
}
