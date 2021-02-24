package softwaredesign.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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

    @FXML
    private TextArea logTextArea;

    @FXML
    private HBox botHBox;

    @FXML
    private HBox deckHBox;

    @FXML
    private Label cardSelectLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.setupGame(playerCardHBox.getChildren());
        updateHumanCardListClickListeners();
        initLogTextArea();
    }

    private void initLogTextArea() {
        logTextArea.setEditable(false);
        logTextArea.appendText("A new game has been started!\n");
        logTextArea.appendText("It's player " + game.getTurnNumber() + "'s turn!\n");
    }

    private void updateHumanCardListClickListeners() {
        for (Node card : playerCardHBox.getChildren()) {
            card.setOnMouseClicked(mouseEvent -> {
                cardSelectLabel.setText(card.toString() + " selected");
            });
        }
    }

    @FXML
    void drawButtonAction() {
        if(game.getTurnNumber() == 1) {
            appendToLog("You drew a card...");
            game.playerDraws();
        }
        else appendToLog("It's not your turn yet...");
    }

    @FXML
    void playCardButtonAction() {

    }

    private void appendToLog(String text) {
        logTextArea.appendText(text + "\n");
    }


}
