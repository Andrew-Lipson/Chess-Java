package View;

import Contract.Contract;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class PromotionView {

    private final Contract.Listener listener;
    private final Stage promotionStage;
    private double xOffset = 0;
    private double yOffset = 0;

    public PromotionView(Contract.Listener listener, boolean isWhite){
        this.listener = listener;
        promotionStage = new Stage();


        promotionStage.setScene(setUpStage(promotionStage,isWhite));

        promotionStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, windowEvent -> {
            Platform.exit();
        });
        promotionStage.showAndWait();

    }


    private Scene setUpStage(Stage promotionStage, boolean isWhite){
        Image icon = new Image("chess-icon.png");
        promotionStage.getIcons().add(icon);
        promotionStage.setTitle("PAWN PROMOTION");
        promotionStage.setWidth(400);
        promotionStage.setHeight(120);
        promotionStage.setResizable(false);
        promotionStage.initModality(Modality.APPLICATION_MODAL);
        promotionStage.initStyle(StageStyle.UNDECORATED);
        Group root = new Group();
        Scene scene = new Scene(root);

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                promotionStage.setX(event.getScreenX() - xOffset);
                promotionStage.setY(event.getScreenY() - yOffset);
            }
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

        return scene;
    }


    private Rectangle initialiseRectangle(int i) {
        Rectangle rectangle = new Rectangle();
        rectangle.setFill(Color.SANDYBROWN);
        rectangle.setWidth(80);
        rectangle.setHeight(80);
        rectangle.setX(20+90*i);
        rectangle.setY(20);
        return rectangle;
    }

    private ImageView initialiseImageView(int i, boolean isWhite){
        String[] fenRep = {"q","r","b","n"};
        ImageView imageview = new ImageView();
        imageview.setX(20+90*i);
        imageview.setY(20);
        imageview.setFitHeight(80);
        imageview.setFitWidth(80);
        imageview.setPickOnBounds(true);
        Image image = new Image( getPNGString(fenRep[i], isWhite));
        imageview.setImage(image);

        imageview.setOnMouseClicked(__ -> {
            this.listener.handlePromotionClicked(fenRep[i]);
            promotionStage.close();
        });
        return imageview;
    }

    private String getPNGString(String character, boolean isWhite) {
        String output = "";
        switch (character) {
            case "q":
                output += "Queen";
                break;
            case "r":
                output += "Rook";
                break;
            case "n":
                output += "Knight";
                break;
            case "b":
                output += "Bishop";
                break;
        }

        output += "-";
        output += isWhite ? "White" : "Black";
        output+=".png";
        return output;
    }
}
