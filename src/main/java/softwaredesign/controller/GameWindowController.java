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

    public HBox getPlayerCardHBox() {
        return playerCardHBox;
    }

    @FXML
    private HBox playerCardHBox;

    @FXML
    private TextArea logTextArea;

    public HBox getPlayedCardsHBox() {
        return playedCardsHBox;
    }

    @FXML
    private HBox playedCardsHBox;

    public HBox getBotHBox() {
        return botHBox;
    }

    @FXML
    private HBox botHBox;

    @FXML
    private HBox deckHBox;

    @FXML
    private Label cardSelectLabel;

    @FXML
    private Label playerSelectLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.setupGame(this);

        updateBotAvatarListClickListeners();
        updateHumanCardListClickListeners();
        initLogTextArea();
    }

    public void updateBotAvatarListClickListeners() {
        for(Node botAvatar : botHBox.getChildren()) {
            botAvatar.setOnMouseClicked(mouseEvent -> {
                playerSelectLabel.setText(botAvatar.toString() + " selected.");
            });
        }
    }

    private void initLogTextArea() {
        logTextArea.setEditable(false);
        logTextArea.appendText("A new game has been started!\n");
        logTextArea.appendText("It's player " + game.getTurnNumber() + "'s turn!\n");
    }

    public void updateHumanCardListClickListeners() {
        for (Node card : playerCardHBox.getChildren()) {
            card.setOnMouseClicked(mouseEvent -> {
                cardSelectLabel.setText(card.toString() + " card selected");
            });
        }
    }

    @FXML
    void drawButtonAction() {
        game.currentPlayerDraws();
    }

    @FXML
    void playCardButtonAction() {

    }

    public void appendToLog(String text) {
        logTextArea.appendText(text + "\n");
    }


}
