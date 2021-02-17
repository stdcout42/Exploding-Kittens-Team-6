package softwaredesign.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import softwaredesign.Game;
import softwaredesign.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class GameWindowController extends WindowControler{
    public GameWindowController(Game game, ViewFactory viewFactory, String fxmlName) {
        super(game, viewFactory, fxmlName);
    }

    @FXML
    private HBox playerCardHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
