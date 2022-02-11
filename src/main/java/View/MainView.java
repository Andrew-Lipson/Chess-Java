package View;

import Model.Position;
import Model.pieces.Piece;
import Observer.Observer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainView implements Observer{

    private BoardSquaresView boardSquaresView;
    private final Group root = new Group();
    private Piece[] whitePieces, blackPieces;
    private ArrayList<SquareView> circlesActivated = new ArrayList<SquareView>();

    //Setting up the JAVAFX stage that will be used for the display
    public MainView(Stage stage) throws IOException {
        Scene scene = new Scene(root, Color.BROWN);
        Image icon = new Image("chess-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("CHESS");
        stage.setWidth(730);
        stage.setHeight(750);
        stage.setResizable(false);

        for (int i = 0; i < 8; i++) {
            rankNumbers(root,i);
            fileNumbers(root,i);
        }



        stage.setScene(scene);
        stage.show();
    }

    //WILL CHANGE WITH FEN UPDATE
    public void setUpMainView(Piece [] whitePieces, Piece [] blackPieces){
        this.whitePieces = whitePieces;
        this.blackPieces = blackPieces;
        renderBoard();
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

    //WILL CHANGE WITH FEN UPDATE
    public void renderBoard(){
        
        this.boardSquaresView = new BoardSquaresView();

        for (Piece piece: this.whitePieces) {
            SquareView squareView = this.boardSquaresView.getSquareView(piece.getPosition());
            squareView.addPiece(piece);
        }

        for (Piece piece: this.blackPieces) {
            SquareView squareView = this.boardSquaresView.getSquareView(piece.getPosition());
            squareView.addPiece(piece);
        }

        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                root.getChildren().add(this.boardSquaresView.getSquareView(new Position(file,rank)));
            }
        }
    }

    public BoardSquaresView getBoardSquaresView() {
        return boardSquaresView;
    }

    //WILL CHANGE WITH FEN UPDATE
    @Override
    public void update(Position previousPosition, Piece piece) {
        removeMoveOptionsCircles();
        this.circlesActivated.clear();
        this.boardSquaresView.getSquareView(previousPosition).removePiece();
        this.boardSquaresView.getSquareView(piece.getPosition()).addPiece(piece);
    }

    //Adding circles to squares after a piece has been clicked to show what moves are available
    public void addMoveOptionsCircles(ArrayList<Position> possibleMoves){
        for (Position position:possibleMoves){
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
}
