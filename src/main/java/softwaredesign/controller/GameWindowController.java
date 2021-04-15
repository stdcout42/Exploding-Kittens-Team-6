package softwaredesign.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import softwaredesign.model.gamelogic.Game;
import softwaredesign.model.players.BotAvatar;
import softwaredesign.model.cards.Card;
import softwaredesign.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class GameWindowController extends WindowController {
    public GameWindowController(Game game, ViewFactory viewFactory, String fxmlName) {
        super(game, viewFactory, fxmlName);
    }

    public HBox getPlayerCardHBox() {
        return playerCardHBox;
    }

    @FXML
    private HBox playerCardHBox;

    @FXML
    private JFXButton drawButton;

    @FXML
    private JFXButton playCardButton;

    @FXML
    private JFXButton placeButton;

    @FXML
    private TextArea logTextArea;

    @FXML
    private JFXTextField kittenIndexTextField;

    public HBox getPlayedCardsHBox() {
        return playedCardsHBox;
    }

    @FXML
    private HBox playedCardsHBox;

    @FXML
    private HBox botHBox;

    @FXML
    private HBox deckHBox;

    @FXML
    private Label cardSelectLabel;

    @FXML
    private Label playerSelectLabel;

    @FXML
    private Label cardsInDeckLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game.setupGame(this);
        setNumCardsInDeckLabel(game.getNumCardsInDeck());
        updateBotAvatarListClickListeners();
        updateHumanCardListClickListeners();
        initLogTextArea();
    }

    public void updateBotAvatarListClickListeners() {
        for(Node botAvatar : botHBox.getChildren()) {
            botAvatar.setOnMouseClicked(mouseEvent -> {
                playerSelectLabel.setText(botAvatar.toString() + " selected.");
                game.humanStealsFromBot(botAvatar);
            });
        }
    }

    public HBox getBotHBox() {
        return botHBox;
    }

    public void appendToLog(String text) {
        logTextArea.appendText(text + "\n");
    }

    public void resetcardSelectLabel() {
        cardSelectLabel.setText("No cards selected.");
    }

    public void addCardToPlayedCards(Card cardToPlay) {
        playedCardsHBox.getChildren().clear();
        playedCardsHBox.getChildren().add(cardToPlay);
    }

    public void setNumCardsInDeckLabel(int number) {
        cardsInDeckLabel.setText("Cards in deck: " + number);
    }

    public void resetBotSelectLabel() {
        playerSelectLabel.setText("No players selected.");
    }

    public void removeBotAvatar(BotAvatar botAvatar) {
        botHBox.getChildren().remove(botAvatar);
    }

    public void updateHumanCardListClickListeners() {
        for (Node card : playerCardHBox.getChildren()) {
            card.setOnMouseClicked(mouseEvent -> {
                cardSelectLabel.setText(card.toString() + " card selected");
                game.setCardNodeSelected(card);
            });
        }
    }

    private void initLogTextArea() {
        logTextArea.setEditable(false);
        logTextArea.appendText("A new game has been started!\n");
        logTextArea.appendText("It's your turn!\n");
    }

    @FXML
    private void drawButtonAction() {
        game.playerDraws(true);
    }

    @FXML
    private void playCardButtonAction() {
        game.playerPlaysCard(null, true );
    }

    @FXML
    private void placeButtonAction() {
        try {
            int index = Integer.parseInt(kittenIndexTextField.getText());
            game.humanPlacesKittenAt(index);
        } catch (NumberFormatException e) {
            appendToLog("Indices are usually numeric...");
        }
    }

}
