package softwaredesign;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    Parent parent;
    double offsetX = 0;
    double offsetY = 0;

    @Override
    public void start(Stage stage) throws Exception {
        parent = FXMLLoader.load(getClass().getResource("/StartWindow.fxml"));

        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);
        stage.show();

        parent.setOnMousePressed(mouseEvent -> {
            offsetX = mouseEvent.getSceneX();
            offsetY = mouseEvent.getSceneY();
        });

        parent.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getSceneX() - offsetX/50);
            stage.setY(mouseEvent.getSceneY() - offsetY/50);
        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}


