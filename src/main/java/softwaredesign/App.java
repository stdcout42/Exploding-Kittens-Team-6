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

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(new Game());
        viewFactory.showStartWindow();

    }

    public static void main(String[] args) {
        launch(args);
    }
}


