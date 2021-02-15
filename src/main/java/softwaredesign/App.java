package softwaredesign;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import softwaredesign.view.ViewFactory;

/**
 * JavaFX launcher
 */
public class App extends Application {

    private double offsetX = 0;
    private double offsetY = 0;

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(new Game());
        viewFactory.showStartWindow();
//        viewFactory.updateStyles();

//        parent.setOnMousePressed(mouseEvent -> {
//            offsetX = mouseEvent.getSceneX();
//            offsetY = mouseEvent.getSceneY();
//        });
//
//        parent.setOnMouseDragged(mouseEvent -> {
//            stage.setX(mouseEvent.getSceneX() - offsetX/50);
//            stage.setY(mouseEvent.getSceneY() - offsetY/50);
//        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}


