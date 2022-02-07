package View;

import Model.*;
import Model.pieces.Piece;
import javafx.scene.Group;

public class MainView {

    private final Board board;
    private BoardSquares boardSquares;
    private BoardSquaresView boardSquaresView;
    private final Group root;
    private Piece[] whitePieces;
    private Piece[] blackPieces;
//    private PieceView[] whitePiecesView = new PieceView[16];
//    private PieceView[] blackPiecesView = new PieceView[16];

    public MainView(Board board, Group root){
        this.board = board;
        this.root = root;
        this.boardSquares = this.board.getBoardSquares();
        renderBoard();
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

}
