package softwaredesign.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import softwaredesign.model.gamelogic.Game;
import softwaredesign.controller.GameWindowController;
import softwaredesign.controller.StartWindowController;
import softwaredesign.controller.WindowController;

import java.io.IOException;

/**
 * ViewFactory will produce views/windows when called from a controller or initially from App
 */
public class ViewFactory {
    private final String startWindowFxmlPath = "/fxml/StartWindow.fxml";
    private final String gameWindowFxmlPath = "/fxml/GameWindow.fxml";
    private final WindowController startWindowController;
    private final WindowController gameWindowController;

    public ViewFactory(Game game) {
        startWindowController = new StartWindowController(game, this, startWindowFxmlPath);
        gameWindowController = new GameWindowController(game, this, gameWindowFxmlPath);
    }

    public void showStartWindow() {
        initializeStage(startWindowController);
    }

    private void initializeStage(WindowController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.show();
    }

    public void showGameWindow() {
        initializeStage(gameWindowController);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
