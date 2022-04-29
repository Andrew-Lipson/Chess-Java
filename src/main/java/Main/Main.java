package Main;

import Controller.Controller;
import View.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        Controller controller = new Controller(); // Start up Controller
        MainStage mainView = new MainStage(stage, controller);// Start up View
        controller.startApplication(mainView);
    }

    public static void main(String[] args) {
        launch();
    }

}