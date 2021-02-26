package softwaredesign;

import javafx.scene.Node;
import softwaredesign.controller.GameWindowController;
import softwaredesign.model.*;

import java.util.ArrayList;
import java.util.Random;

public class Game{
    private int numberOfPlayers = 5;
    private Deck mainDeckOfCards;
    private ArrayList <Player> playerList;
    private int turnNumber;
    private GameWindowController gameWindowController;
    private Node cardNodeSelected;

    private enum MoveType {
        PLAY, DRAW
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

    public void playerDraws(boolean isHumanPlayer) {
        Player player = getPlayerThatHasTurn();
        if(isHumanPlayer && !(player instanceof HumanPlayer)) {
            gameWindowController.appendToLog("It's not your turn yet!");
            return;
        }
        Card drawnCard = mainDeckOfCards.drawCard();
        if(drawnCard.cardType == Card.CardType.EXPLODING_KITTEN) {
            logMoveByPlayer(getPlayerThatHasTurn(), MoveType.DRAW, drawnCard);
            playerDrawsKitten(player);
            return;
        }
        else {
            logMoveByPlayer(player, MoveType.DRAW, drawnCard);
            player.addCard(drawnCard);
            gameWindowController.updateHumanCardListClickListeners();
        }
        startNextTurn();
    }

    private void playerDrawsKitten(Player player) {
        player.setHasExplodingKitten(true);
        if (player.hasDefuseCard()) {
           if(player instanceof BotPlayer) {
               playerPlaysCard(player.extractDefuseCard(), false);
           } else {
               gameWindowController.appendToLog("Play your defuse card!!");
               // TODO: configure functionality for human player placing defuse card
           }
        } else {
            if(player instanceof BotPlayer) {
                BotAvatar botAvatar = ((BotPlayer) player).getBotAvatar();
                gameWindowController.removeBotAvatar(botAvatar);
                playerList.remove((BotPlayer)player);
                gameWindowController.appendToLog(player.toString() + " is ded.");
                startNextTurn();
            } else {
                //TODO: GameOver for human player
            }
        }
    }

    public void playerPlaysCard(Card card, boolean isHuman) {
        // TODO: clean this function up
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if(isHuman && playerThatHasTurn instanceof BotPlayer) {
            gameWindowController.appendToLog("It's not your turn yet!");
            return;
        }
        if(playerThatHasTurn instanceof HumanPlayer) {
            if(cardNodeSelected == null) {
                gameWindowController.appendToLog("Please select a card to play!");
                return;
            }
            card = getCardSelectedNodeAndResetNode();
        }
        if(card.cardType == Card.CardType.DEFUSE) {
            if(playerThatHasTurn instanceof HumanPlayer) {
                if(!playerThatHasTurn.isExploding()) {
                    gameWindowController.appendToLog("You can't play that card right now!");
                    gameWindowController.updateHumanCardListClickListeners();
                    return;
                }
                // TODO: configure human player placing kitten back into deck functionality
                gameWindowController.appendToLog("Choose an index to where you want to place the exploding kitten.");
            } else {
                placeKittenRandomlyInDeck();
                playerThatHasTurn.setHasExplodingKitten(false);
                movePlayedCardToPlayedPile(card, playerThatHasTurn);
                logMoveByPlayer(playerThatHasTurn, MoveType.PLAY, card);
                startNextTurn();
            }
        } else {
            movePlayedCardToPlayedPile(card, playerThatHasTurn);
            logMoveByPlayer(playerThatHasTurn, MoveType.PLAY, card);
            performCardLogic(card);
        }

    }

    private Card getCardSelectedNodeAndResetNode() {
        Card card = (Card) cardNodeSelected;
        card.setOnMouseClicked(null);
        resetCardSelected();
        return card;
    }

    private void movePlayedCardToPlayedPile(Card cardToPlay, Player player) {
        player.removeCard(cardToPlay);
        gameWindowController.addCardToPlayedCards(cardToPlay);
    }

    private void placeKittenRandomlyInDeck() {
        int index = new Random().nextInt(getNumCardsInDeck());
        mainDeckOfCards.placeCardAtIndex(index, new Card(Card.CardType.EXPLODING_KITTEN));
        gameWindowController.appendToLog("Exploding kitten has been placed back into the deck!");
    }

    private void resetCardSelected() {
        cardNodeSelected = null;
        gameWindowController.resetcardSelectLabel();
    }

    private void performCardLogic(Card cardPlayed) {
        switch (cardPlayed.cardType) {
            case REVERSE:
                // TODO
            case SKIP:
                startNextTurn();
                return;
            case SHUFFLE:
                mainDeckOfCards.shuffleDeck();
                break;
            case SEE_THE_FUTURE:
                // TODO
                break;
            case STEAL:
                // TODO
                break;
        }
        if(getPlayerThatHasTurn() instanceof BotPlayer) makeRandomBotMove((BotPlayer) getPlayerThatHasTurn());
    }

    private void setupPlayers() {
        int playerNumber = 0;
        HumanPlayer humanPlayer = new HumanPlayer(playerNumber++, gameWindowController.getPlayerCardHBox().getChildren());
        playerList = new ArrayList<>();
        playerList.add(humanPlayer);
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            BotPlayer botPlayer = new BotPlayer(playerNumber++);
            playerList.add(botPlayer);
            gameWindowController.getBotHBox().getChildren().add(botPlayer.getBotAvatar());
        }
    }

    private void dealCards(){
        int numberOfCardsInHand = 5;

        for (Player player: playerList) {
            player.addCard(new Card(Card.CardType.DEFUSE));
            for (int i = 0; i < numberOfCardsInHand - 1; i++) {
                player.addCard(mainDeckOfCards.drawCard());
            }
        }
    }

    private void startNextTurn() {
        gameWindowController.setNumCardsInDeckLabel(getNumCardsInDeck());
        turnNumber = (turnNumber + 1) % playerList.size();
        Player player = getPlayerThatHasTurn();
        System.out.println("New turn: " + turnNumber + player.toString() + " turn.");
        if(player instanceof BotPlayer) {
            makeRandomBotMove((BotPlayer) player);
        }
        else gameWindowController.appendToLog("It's your turn!");
    }

    private void makeRandomBotMove(BotPlayer botPlayer) {
        int moveType = new Random().nextInt(2);
        if(!botPlayer.hasNonDefuseCards()) {
            playerDraws(false);
        } else {
            if (moveType == 0) {
                playerDraws(false);
            } else {
                makeBotPlayCard(botPlayer);
            }
        }
    }

    private void makeBotPlayCard(BotPlayer botPlayer) {
        Card cardToPlay = botPlayer.getNonDefuseCard();
        playerPlaysCard(cardToPlay, false);
    }

    private Player getPlayerThatHasTurn() {
//        for (Player player: playersArrayList) {
//            if (player.getPlayerNumber() == turnNumber) {
//                return player;
//            }
//        }
//        return humanPlayer;
        return playerList.get(turnNumber);
    }

    public void setCardNodeSelected(Node cardNode) {
        this.cardNodeSelected = cardNode;
    }

    private void logMoveByPlayer(Player player, MoveType moveType, Card card) {
        String playerTitle = (player instanceof HumanPlayer ? "You" : player.toString());
        if(moveType == MoveType.PLAY) gameWindowController.appendToLog(playerTitle + " play(s) a " + card.toString() + " card.");
        else gameWindowController.appendToLog(playerTitle + " draw(s) a " + card.toString() + " card.");
    }

    public int getNumCardsInDeck() {
       return mainDeckOfCards.getNumOfCards();
    }
}
