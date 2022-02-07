package View;

import Model.*;
import Model.pieces.Piece;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainView {

    private final Board board;
    private BoardSquares boardSquares;
    private BoardSquaresView boardSquaresView;
    private final Group root = new Group();
    private Piece[] whitePieces;
    private Piece[] blackPieces;
//    private PieceView[] whitePiecesView = new PieceView[16];
//    private PieceView[] blackPiecesView = new PieceView[16];

    public MainView(Board board, Stage stage){
        this.board = board;
        this.boardSquares = this.board.getBoardSquares();
        startUpStage(stage);
        renderBoard();
    }

    private void startUpStage(Stage stage){
        Scene scene = new Scene(root, Color.BROWN);
        Image icon = new Image("chess-icon.png");
        stage.getIcons().add(icon);
        stage.setTitle("Window");
        stage.setWidth(680);
        stage.setHeight(700);
        stage.setResizable(true);

        stage.setScene(scene);
        stage.show();
    }

    public void renderBoard(){
        
        this.whitePieces = this.board.getWhitePieces();
        this.blackPieces = this.board.getBlackPieces();
        this.boardSquaresView = new BoardSquaresView();

        for (Piece piece: this.whitePieces) {
            SquareView squareView = this.boardSquaresView.getSquareView(piece.getFile(), piece.getRank());
            squareView.addPiece(piece);
        }

        for (Piece piece: this.blackPieces) {
            SquareView squareView = this.boardSquaresView.getSquareView(piece.getFile(), piece.getRank());
            squareView.addPiece(piece);
        }

        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                root.getChildren().add(this.boardSquaresView.getSquareView(file,rank));
            }
        }
    }

    public BoardSquaresView getBoardSquaresView() {
        return boardSquaresView;
    }
}
