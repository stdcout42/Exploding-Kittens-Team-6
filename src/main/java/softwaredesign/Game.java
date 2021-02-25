package softwaredesign;

import javafx.scene.Node;
import softwaredesign.controller.GameWindowController;
import softwaredesign.model.*;

import java.util.ArrayList;
import java.util.Random;

public class Game{
    private int numberOfPlayers = 5;
    private Deck mainDeckOfCards;
    private HumanPlayer humanPlayer;
    private ArrayList <Player> playersArrayList;
    private int turnNumber;
    private GameWindowController gameWindowController;
    private Node cardNodeSelected;

    public enum MoveType {
        PLAY, DRAW
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers > 1 && numberOfPlayers < 6)
            this.numberOfPlayers = numberOfPlayers;
    }

    public void setupGame(GameWindowController controller){
        this.gameWindowController = controller;
        mainDeckOfCards = new Deck();
        cardNodeSelected = null;
        turnNumber = 0;
        setupPlayers();
        dealCards();
        mainDeckOfCards.addPostPlayerSetupCards(numberOfPlayers);
    }

    public void currentPlayerDraws() {
        Player player = getPlayerThatHasTurn();
        if(player.getPlayerNumber() != turnNumber) {
            gameWindowController.appendToLog("It's not your turn yet!");
            return;
        }
        Card drawnCard = mainDeckOfCards.drawCard();
        if(drawnCard.cardType == Card.CardType.EXPLODING_KITTEN) {
            // TODO
            logMoveByPlayer(getPlayerThatHasTurn(), MoveType.DRAW, drawnCard);
            explodingKittenDrawn();
        }
        else {
            logMoveByPlayer(player, MoveType.DRAW, drawnCard);
            player.addCard(drawnCard);
            gameWindowController.updateHumanCardListClickListeners();
        }
        startNextTurn();
    }

    private void explodingKittenDrawn() {
        Player player = getPlayerThatHasTurn();
        if (player.hasDefuseCard()) {
           // TODO
        } else {
            //TODO: GameOver for player
        }
    }

    public void currentPlayerPlays(Card cardToPlay) {
        Player player = getPlayerThatHasTurn();
        if(player.getPlayerNumber() != turnNumber) return;
        if(player instanceof HumanPlayer) {
            if(cardNodeSelected == null) {
                gameWindowController.appendToLog("Please select a card to play!");
                return;
            }
            cardToPlay = (Card) cardNodeSelected;
            cardToPlay.setOnMouseClicked(null);
            cardNodeSelected = null;
            gameWindowController.resetcardSelectLabel();
        }

        player.removeCard(cardToPlay);
        gameWindowController.addCardToPlayedCards(cardToPlay);

        logMoveByPlayer(player, MoveType.PLAY, cardToPlay);
        performCardLogic(cardToPlay);

        if(player instanceof BotPlayer)   makeRandomBotMove((BotPlayer) player);
    }

    private void performCardLogic(Card cardToPlay) {
        switch (cardToPlay.cardType) {
            case REVERSE:
            case SKIP:
                startNextTurn();
                return;
            case SHUFFLE:
                mainDeckOfCards.shuffleDeck();
            case SEE_THE_FUTURE:
                break;
            case STEAL:
                break;
        }
    }

    private void setupPlayers() {
        int playerNumber = 0;
        humanPlayer = new HumanPlayer(playerNumber++, gameWindowController.getPlayerCardHBox().getChildren());
        playersArrayList = new ArrayList<>();
        playersArrayList.add(humanPlayer);
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            BotPlayer botPlayer = new BotPlayer(playerNumber++);
            playersArrayList.add(botPlayer);
            gameWindowController.getBotHBox().getChildren().add(botPlayer.getBotAvatar());
        }
    }

    private void dealCards(){
        int numberOfCardsInHand = 5;

        for (Player player: playersArrayList) {
            player.addCard(new Card(Card.CardType.DEFUSE));
            for (int i = 0; i < numberOfCardsInHand - 1; i++) {
                player.addCard(mainDeckOfCards.drawCard());
            }
        }
    }

    private void startNextTurn() {
        turnNumber = (turnNumber + 1) % numberOfPlayers;
        Player player = getPlayerThatHasTurn();
        if(player instanceof BotPlayer) {
            makeRandomBotMove((BotPlayer) player);
        }
    }

    private void makeRandomBotMove(BotPlayer botPlayer) {
            int moveType = new Random().nextInt(2);
            if(moveType == 0) {
                currentPlayerDraws();
            }
            else {
                makeBotPlayCard(botPlayer);
            }
    }

    private void makeBotPlayCard(BotPlayer botPlayer) {
        Card cardToPlay = botPlayer.getNonDefuseCard();
        currentPlayerPlays(cardToPlay);
    }

    private Player getPlayerThatHasTurn() {
        for (Player player: playersArrayList) {
            if (player.getPlayerNumber() == turnNumber) {
                return player;
            }
        }
        return humanPlayer;
    }

    public void setCardNodeSelected(Node cardNode) {
        this.cardNodeSelected = cardNode;
    }

    private void logMoveByPlayer(Player player, MoveType moveType, Card card) {
        String playerTitle = (player instanceof HumanPlayer ? "You" : player.toString());
        if(moveType == MoveType.PLAY) gameWindowController.appendToLog(playerTitle + " play(s) a " + card.toString() + " card.");
        else gameWindowController.appendToLog(playerTitle + " draw(s) a " + card.toString() + " card.");
    }
}
