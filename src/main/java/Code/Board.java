package Code;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Board {

    private Square[][] ranks = new Square[8][];
    private Group root = new Group();

    public Board() {
        for (int rank = 0; rank < 8; rank++) {
            Square[] files = new Square[8];
            for (int file = 0; file < 8; file++) {
                if ((file+rank)%2==0){
                    files[file] = new Square(Color.BEIGE, 80, 80, (10 + 80 * file), (10 + 80 * rank));
                }
                else {
                    files[file] = new Square(Color.GREY, 80, 80, (10 + 80 * file), (10 + 80 * rank));
                }

            }
            ranks[rank] = files;
        }
    }
    private void updateRoot(){
        for (int rank = 0; rank < 8; rank++){
            for (int file = 0; file < 8; file++){
                root.getChildren().add(ranks[rank][file].getRectangle());
            }
        }
    }

    public Group getRoot(){
        updateRoot();
        return root;
    }
}
