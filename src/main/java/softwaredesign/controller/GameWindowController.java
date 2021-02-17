package softwaredesign.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import softwaredesign.Game;
import softwaredesign.model.Card;
import softwaredesign.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class GameWindowController extends WindowControler{
    public GameWindowController(Game game, ViewFactory viewFactory, String fxmlName) {
        super(game, viewFactory, fxmlName);
    }

    @FXML
    private HBox playerCardHBox;

    @FXML
    private TextArea logTextArea;

    @FXML
    private HBox botHBox;

    @FXML
    private HBox deckHBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deckHBox.getChildren().add(new Card(Card.CardType.DEFUSE));
    }
}
