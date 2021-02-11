package softwaredesign.controller;

import javafx.fxml.FXML;
import softwaredesign.Game;
import softwaredesign.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class StartWindowController extends WindowControler{
    public StartWindowController(Game game, ViewFactory viewFactory, String fxmlName) {
        super(game, viewFactory, fxmlName);
    }

    @FXML
    void playButtonAction() {
        // TODO
    }

    @FXML
    void optionsButtonAction() {
        // TODO
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
