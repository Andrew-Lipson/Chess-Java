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

    /**
     * Set the scene to the "Choose Number of Players" Menu
     */
    public void setChooseNumberOfPlayersMenu() {
        ChooseNumberOfPlayersRoot chooseNumberOfPlayersRoot = new ChooseNumberOfPlayersRoot(this.listener, this);
        this.setRoot(chooseNumberOfPlayersRoot);
    }

    /**
     * Set the scene to the "Choose Colour" Menu
     */
    public void setChooseColourMenu() {
        ChooseColourRoot chooseColourRoot = new ChooseColourRoot(this.listener);
        this.setRoot(chooseColourRoot);
    }
}
