package softwaredesign;

import javafx.application.Application;
import javafx.stage.Stage;
import softwaredesign.view.ViewFactory;

/**
 * JavaFX launcher
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory(new Game());
        viewFactory.showStartWindow();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


