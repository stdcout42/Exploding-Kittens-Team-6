package softwaredesign.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import softwaredesign.Game;
import softwaredesign.model.Card;
import softwaredesign.model.BotAvatar;
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

    @FXML
    private Label playerSelectLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.setupGame(playerCardHBox.getChildren());
        for (int i  = 2; i <= game.getNumberOfPlayers(); i++)
            botHBox.getChildren().add(new BotAvatar(i));

        updateBotAvatarListClickListeners();
        updateHumanCardListClickListeners();
        initLogTextArea();
    }

    private void updateBotAvatarListClickListeners() {
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

    private void updateHumanCardListClickListeners() {
        for (Node card : playerCardHBox.getChildren()) {
            card.setOnMouseClicked(mouseEvent -> {
                cardSelectLabel.setText(card.toString() + " card selected");
            });
        }
    }

    @FXML
    void drawButtonAction() {
        Card drawnCard;
        if(game.getTurnNumber() == 0) {
            drawnCard = game.currentPlayerDraws();
            appendToLog("You drew a " + drawnCard.toString() + " card");
            if(drawnCard.cardType == Card.CardType.EXPLODING_KITTEN) {
                //TODO
            } else {
                updateHumanCardListClickListeners();
            }
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
