package View.Modals;

import Contract.Contract;
import View.Board.SquareNode;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class PromotionModal extends Stage{

    private final Contract.Listener listener;
    private double xOffset = 0;
    private double yOffset = 0;

    public PromotionModal(Contract.Listener listener, boolean isWhite){
        this.listener = listener;

        Image icon = new Image("chess-icon.png");
        this.getIcons().add(icon);
        this.setTitle("PAWN PROMOTION");
        this.setWidth(400);
        this.setHeight(120);
        this.setResizable(false);
        this.initModality(Modality.APPLICATION_MODAL);
        this.initStyle(StageStyle.UNDECORATED);
        Group root = new Group();
        Scene scene = new Scene(root);

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            this.setX(event.getScreenX() - this.xOffset);
            this.setY(event.getScreenY() - this.yOffset);
        });

        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.BROWN);
        rectangle.setWidth(500);
        rectangle.setHeight(500);
        rectangle.setX(0);
        rectangle.setY(0);
        root.getChildren().add(rectangle);

        for (int i = 0; i < 4; i++) {
            root.getChildren().add(initialiseRectangle(i));
            root.getChildren().add(initialiseImageView(i, isWhite));
        }

        this.setScene(scene);

        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> Platform.exit());

    }

    /**
     * Initialising the 4 rectangles that the 4 promotion piece will be chosen from
     *
     * @param i position on the Scene
     * @return rectangle with the right settings
     */
    private Rectangle initialiseRectangle(int i) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.SANDYBROWN);
        rectangle.setWidth(80);
        rectangle.setHeight(80);
        rectangle.setX(20+90*i);
        rectangle.setY(20);
        return rectangle;
    }

    /**
     * Get the correct image with the right settings
     *
     * @param i position on the Scene
     * @param isWhite is the piece white
     * @return ImageView of the correct piece
     */
    private ImageView initialiseImageView(int i, boolean isWhite) {
        String[] fenRep = {"q","r","b","n"};
        ImageView imageview = new ImageView();
        imageview.setX(20+90*i);
        imageview.setY(20);
        imageview.setFitHeight(80);
        imageview.setFitWidth(80);
        imageview.setPickOnBounds(true);
        char character = fenRep[i].charAt(0);
        Image image = new Image( SquareNode.getPNGString(character, isWhite));
        imageview.setImage(image);

        imageview.setOnMouseClicked(__ -> {
            this.listener.handlePromotionDecision(fenRep[i]);
            this.close();
        });
        return imageview;
    }

}
