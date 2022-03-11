package View;


import Contract.Contract;
import View.Board.BoardScene;
import View.Board.PositionView;
import View.Board.SquareColour;
import View.Board.SquareNode;
import View.Menu.MainMenuScene;
import View.Modals.GameOverModal;
import View.Modals.PromotionModal;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Character.isDigit;

public class MainStage {

    private final Stage stage;
    private final Contract.Listener listener;
    private BoardScene boardView;
    private ArrayList<SquareNode> circlesActivated = new ArrayList<SquareNode>();

    /**
     * Setting up the JAVAFX stage that will be used for the display
     * 
     * @param stage
     * @param listener
     * @throws IOException
     */
    public MainStage(Stage stage, Contract.Listener listener) throws IOException {
        this.stage = stage;
        this.listener = listener;

//        for (int i = 0; i < 8; i++) {
//            rankNumbers(root,i);
//            fileNumbers(root,i);
//        }

    }

    public void createStage() {
        Image icon = new Image("chess-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("CHESS");
        stage.setWidth(680); // plus 50 when wanted file and rank numbers
        stage.setHeight(700); // plus 50 when wanted file and rank numbers
        stage.setResizable(false);
        stage.show();
    }

    public void showMainMenu() {
        MainMenuScene menuView = new MainMenuScene(listener);
        menuView.chooseNumberOfPlayersMenu();
        stage.setScene(menuView);
    }

    public void showBoard(boolean inverted){
        this.boardView = new BoardScene(listener, inverted);
        stage.setScene(boardView);
    }

    /**
     * Adding Rank numbers to make it easier for me to see what file and rank each square is.
     * DELETE ONCE PROJECT IS COMPLETE

     * @param root
     * @param rank
     */
    private void rankNumbers(Group root, Integer rank) {
        Text text = new Text();
        text.setText(rank.toString());
        text.setX(660);
        text.setY(65+80*rank);
        text.setFont(Font.font(50));
        root.getChildren().add(text);
    }

    /**
     * Adding File numbers to make it easier for me to see what file and rank each square is.
     * DELETE ONCE PROJECT IS COMPLETE

     * @param root
     * @param file
     */
    private void fileNumbers(Group root, Integer file) {
        Text text = new Text();
        text.setText(file.toString());
        text.setX(40+80*file);
        text.setY(700);
        text.setFont(Font.font(50));
        root.getChildren().add(text);
    }


    /**
     * Using the FEN string, update the view to match the FEN input
     * 
     * @param FENPosition
     */
    public void updateView(String FENPosition) {
        System.out.println(FENPosition);
        String[] str = FENPosition.split(" ");
        char[] chars = str[0].toCharArray();
        int rank = 0;
        int file = 0;
        for (Character character:chars) {
            if(character.equals('/')){
                rank += 1;
                file = 0;
            } else if(isDigit(character)) {
                for(int i = 0; i < Character.getNumericValue(character); i++){
                    getSquareView(new PositionView(file,rank)).addPiece('x');
                    file += 1;
                }

            } else{
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
     * @param possibleMoves
     */
    public void addMoveOptionsCircles(PositionView positionView , ArrayList<PositionView> possibleMoves) {
        boardView.getSquareView(positionView).changeSquareColour(SquareColour.CLICKED);
        for (PositionView position:possibleMoves) {
            SquareNode squareView = boardView.getSquareView(position);
            squareView.addCircle();
            circlesActivated.add(squareView);
        }
    }

    /**
     * Removing the circles of the possible moves and return the clicked square back to its normal colour
     */
    public void removeMoveOptionsCircles(PositionView positionView) {
        boardView.getSquareView(positionView).changeSquareColour();
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

    public void promotionPopup(boolean isWhite){
        PromotionModal promotionView = new PromotionModal(listener, isWhite);
        promotionView.show();
    }

    public void gameOverPopup(boolean isADraw, String string){
        GameOverModal gameOverModal = new GameOverModal(isADraw,string);
        gameOverModal.show();
        if (gameOverModal.isNewGame()){
            showMainMenu();
        }
    }

    private SquareNode getSquareView(PositionView position) {
        return boardView.getSquareView(position);
    }
}
