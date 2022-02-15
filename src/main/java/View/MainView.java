package View;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Character.isDigit;

public class MainView {

    private final Stage stage;
    private BoardSquaresView boardSquaresView;
    private final Group root = new Group();
    private String[] currentFEN;
    private ArrayList<SquareView> circlesActivated = new ArrayList<SquareView>();

    //Setting up the JAVAFX stage that will be used for the display
    public MainView(Stage stage, OnClick controller) throws IOException {
        this.stage = stage;


        for (int i = 0; i < 8; i++) {
            rankNumbers(root,i);
            fileNumbers(root,i);
        }

        this.boardSquaresView = new BoardSquaresView(controller);

        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                root.getChildren().add(this.boardSquaresView.getSquareView(new PositionView(file,rank)));
            }
        }

    }

    public void showBoard(){
        Image icon = new Image("chess-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("CHESS");
        stage.setWidth(730);
        stage.setHeight(750);
        stage.setResizable(false);
        Scene scene = new Scene(root, Color.BROWN);
        stage.setScene(scene);
        stage.show();
    }

    //Adding Rank numbers to make it easier for me to see what file and rank each square is.
    //DELETE ONCE PROJECT IS COMPLETE
    private void rankNumbers(Group root, Integer rank){
        Text text = new Text();
        text.setText(rank.toString());
        text.setX(660);
        text.setY(65+80*rank);
        text.setFont(Font.font(50));
        root.getChildren().add(text);
    }

    //Adding File numbers to make it easier for me to see what file and rank each square is.
    //DELETE ONCE PROJECT IS COMPLETE
    private void fileNumbers(Group root, Integer file){
        Text text = new Text();
        text.setText(file.toString());
        text.setX(40+80*file);
        text.setY(700);
        text.setFont(Font.font(50));
        root.getChildren().add(text);
    }


    // using the FEN string, update the view to match the FEN input
    public void updateView(String FENPosition) {
        System.out.println(FENPosition);
        String[] str = FENPosition.split(" ");
        char[] chars = str[0].toCharArray();
        int rank = 0;
        int file = 0;
        for (Character character:chars) {
            if(character.equals('/')){
                rank+=1;
                file=0;
            }
            else if(isDigit(character)) {
                for(int i = 0; i<Character.getNumericValue(character);i++){
                    getSquareView(new PositionView(file,rank)).addPiece('x');
                    file +=1;
                }

            }
            else{
                getSquareView(new PositionView(file,rank)).addPiece(character);
                file+=1;
            }

        }
        removeMoveOptionsCircles();
        this.circlesActivated.clear();
    }


    //Adding circles to squares after a piece has been clicked to show what moves are available
    public void addMoveOptionsCircles(ArrayList<PositionView> possibleMoves){
        for (PositionView position:possibleMoves){
            SquareView squareView = boardSquaresView.getSquareView(position);
            squareView.addCircle();
            circlesActivated.add(squareView);
        }
    }

    //Removing the circles of the possible moves
    public void removeMoveOptionsCircles(){
        for (SquareView squareview:this.circlesActivated) {
            squareview.removeCircle();
        }
    }

    private SquareView getSquareView(PositionView position) {
        return boardSquaresView.getSquareView(position);
    }
}
