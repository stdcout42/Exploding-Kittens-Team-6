package softwaredesign.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import softwaredesign.Game;
import softwaredesign.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StartWindowController extends WindowControler{
    public StartWindowController(Game game, ViewFactory viewFactory, String fxmlName) {
        super(game, viewFactory, fxmlName);
    }

    @FXML
    private Label titleLabel;

    @FXML
    private void playButtonAction() {
        viewFactory.showGameWindow();
        Stage thisStage = (Stage) titleLabel.getScene().getWindow();
        viewFactory.closeStage(thisStage);
    }

    @FXML
    private void optionsButtonAction() {
        // TODO
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
