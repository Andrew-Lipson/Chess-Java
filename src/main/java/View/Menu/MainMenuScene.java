package View.Menu;

import Contract.Contract;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;

public class MainMenuScene extends Scene{

    private final Contract.Listener listener;

    public MainMenuScene(Contract.Listener listener) {
        super(new Group(), Color.BROWN);
        this.listener = listener;
        chooseNumberOfPlayersMenu();
    }

    private void chooseNumberOfPlayersMenu(){
        ChooseNumberOfPlayersRoot chooseNumberOfPlayersRoot = new ChooseNumberOfPlayersRoot(listener, this);
        this.setRoot(chooseNumberOfPlayersRoot);
    }

    public void chooseColourMenu(){
        ChooseColourRoot chooseColourRoot = new ChooseColourRoot(listener);
        this.setRoot(chooseColourRoot);
    }
}
