package softwaredesign.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import softwaredesign.Game;
import softwaredesign.controller.GameWindowController;
import softwaredesign.controller.StartWindowController;
import softwaredesign.controller.WindowControler;

import java.io.IOException;
import java.util.ArrayList;

/**
 * ViewFactory will produce views/windows when called from a controller or initially from App
 */
public class ViewFactory {
    private final String startWindowFxmlPath = "/fxml/StartWindow.fxml";
    private final String gameWindowFxmlPath = "/fxml/GameWindow.fxml";

    private Game game;

    public ViewFactory(Game game) {
        this.game = game;
    }

    public void showStartWindow() {
        WindowControler startWindowController = new StartWindowController(game, this,
                startWindowFxmlPath);
        initializeStage(startWindowController);
    }

    private void initializeStage(WindowControler controller) {
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
        WindowControler gameWindowContoller = new GameWindowController(game, this,
                gameWindowFxmlPath);
        initializeStage(gameWindowContoller);
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
