package View;


import Contract.Contract;
import View.Board.BoardScene;
import View.Board.PositionView;
import View.Board.SquareColour;
import View.Board.SquareNode;
import View.Menu.MainMenuScene;
import View.Modals.GameOverModal;
import View.Modals.PromotionModal;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

import static java.lang.Character.isDigit;

public class MainStage {

    private final Stage stage;
    private final Contract.Listener listener;
    private BoardScene boardView;
    private final ArrayList<SquareNode> circlesActivated = new ArrayList<>();

    public MainStage(Stage stage, Contract.Listener listener) {
        this.stage = stage;
        this.listener = listener;
    }

    /**
     * Set up the stage with the necessary settings
     */
    public void createStage() {
        Image icon = new Image("chess-icon.png");
        this.stage.getIcons().add(icon);
        this.stage.setTitle("CHESS");
        this.stage.setWidth(680); // plus 50 when wanted file and rank numbers
        this.stage.setHeight(700); // plus 50 when wanted file and rank numbers
        this.stage.setResizable(false);
        this.stage.show();
    }

    /**
     * Show the Main Menu
     */
    public void showMainMenu() {
        MainMenuScene menuView = new MainMenuScene(this.listener);
        menuView.setChooseNumberOfPlayersMenu();
        this.stage.setScene(menuView);

    }

    /**
     * Show the board, and invert it if necessary
     *
     * @param inverted should the board be displayed inverted
     */
    public void showBoard(boolean inverted) {
        this.boardView = new BoardScene(this.listener, inverted);
        this.stage.setScene(this.boardView);
    }

    /**
     * Using the FEN string, update the view to match the FEN input
     * 
     * @param FENPosition string that is the FEN position to be displayed
     */
    public void updateView(String FENPosition) {
        System.out.println(FENPosition);
        String[] str = FENPosition.split(" ");
        char[] chars = str[0].toCharArray();
        int rank = 0;
        int file = 0;
        for (Character character:chars) {
            if(character.equals('/')) {
                rank += 1;
                file = 0;
            } else if(isDigit(character)) {
                for(int i = 0; i < Character.getNumericValue(character); i++) {
                    getSquareView(new PositionView(file,rank)).addPiece('x');
                    file += 1;
                }

            } else {
                getSquareView(new PositionView(file,rank)).addPiece(character);
                file += 1;
            }

        }
        removeMoveOptionsCircles();
        this.circlesActivated.clear();
    }


    /**
     * Adding circles to squares after a piece has been clicked to show what moves are available
     * 
     * @param possibleMoves List of PositionView that circles need to be added to
     */
    public void addMoveOptionsCircles(PositionView positionView , ArrayList<PositionView> possibleMoves) {
        this.boardView.getSquareView(positionView).changeSquareColour(SquareColour.CLICKED);
        for (PositionView position:possibleMoves) {
            SquareNode squareView = this.boardView.getSquareView(position);
            squareView.addCircle();
            this.circlesActivated.add(squareView);
        }
    }

    /**
     * Removing the circles of the possible moves and return the clicked square back to its normal colour
     */
    public void removeMoveOptionsCircles(PositionView positionView) {
        this.boardView.getSquareView(positionView).changeSquareColour();
        for (SquareNode squareview:this.circlesActivated) {
            squareview.removeCircle();
        }
    }

    /**
     * Removing the circles of the possible moves
     */
    public void removeMoveOptionsCircles() {
        for (SquareNode squareview:this.circlesActivated) {
            squareview.removeCircle();
        }
    }

    /**
     * Show the promotion popup
     *
     * @param isWhite is the promoted piece White
     */
    public void promotionPopup(boolean isWhite) {
        PromotionModal promotionView = new PromotionModal(this.listener, isWhite);
        promotionView.showAndWait();
    }

    /**
     * Show the Game Over popup
     *
     * @param isADraw Was it a draw
     * @param string String to be displayed
     */
    public void gameOverPopup(boolean isADraw, String string) {
        GameOverModal gameOverModal = new GameOverModal(isADraw,string);
        gameOverModal.showAndWait();
        if (gameOverModal.isNewGame()) {
            showMainMenu();
        }
    }

    private SquareNode getSquareView(PositionView position) {
        return this.boardView.getSquareView(position);
    }
}
