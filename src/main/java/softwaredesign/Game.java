package softwaredesign;
import javafx.application.Platform;
import softwaredesign.controller.GameWindowController;
import softwaredesign.model.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game class will contain:
 *  - options such as number of players
 *  - deck of cards
 *  - turn of player
 *  - [timer]
 *  ...
 */
public class Game{
    private int numberOfPlayers = 5;
    private Deck mainDeckOfCards;
    private HumanPlayer humanPlayer;
    private ArrayList <Player> playersArrayList;
    private int turnNumber;
    private GameWindowController gameWindowController;

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers > 1 && numberOfPlayers < 6)
            this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    private void setupPlayers() {
        int playerNumber = 0;
        humanPlayer = new HumanPlayer(playerNumber++, gameWindowController.getPlayerCardHBox().getChildren());
        playersArrayList = new ArrayList<>();
        playersArrayList.add(humanPlayer);
        for (int i = 0; i < numberOfPlayers; i++) {
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

    public void setupGame(GameWindowController controller){
        this.gameWindowController = controller;
        mainDeckOfCards = new Deck();
        turnNumber = 0;
        setupPlayers();
        dealCards();
        mainDeckOfCards.addPostPlayerSetupCards(numberOfPlayers);
    }

    public void currentPlayerDraws() {
        Player player = getPlayerThatHasTurn();
        if(player.getPlayerNumber() != turnNumber) return;
        Card drawnCard = mainDeckOfCards.drawCard();
        if(drawnCard.cardType == Card.CardType.EXPLODING_KITTEN) {
            System.out.println("Explotin' kittan drawn!");
            // TODO
        }
        else {
            System.out.println(player.toString() + " draws " + drawnCard.toString());
            player.addCard(drawnCard);
        }
        startNextTurn();
    }

    private void startNextTurn() {
        turnNumber = (turnNumber + 1) % numberOfPlayers;
        System.out.println("Turn number: " + turnNumber);
        Player player = getPlayerThatHasTurn();
        if(player instanceof BotPlayer) {
            System.out.println(player.toString() + "'s turn!");
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

    private void currentPlayerPlays(Card cardToPlay) {
        Player player = getPlayerThatHasTurn();
        if(player.getPlayerNumber() != turnNumber) return;
        player.removeCard(cardToPlay);
        System.out.println(player.toString() + " plays " + cardToPlay.toString());
        gameWindowController.getPlayedCardsHBox().getChildren().clear();
        gameWindowController.getPlayedCardsHBox().getChildren().add(cardToPlay);

        if(player instanceof BotPlayer) {
            makeRandomBotMove((BotPlayer) player);
        }
    }

    private Player getPlayerThatHasTurn() {
        for (Player player: playersArrayList) {
            if (player.getPlayerNumber() == turnNumber) {
                return player;
            }
        }
        return null;
    }
}
