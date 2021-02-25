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

    public void playerDraws(boolean isHumanPlayer) {
        Player player = getPlayerThatHasTurn();
        if(isHumanPlayer && !(player instanceof HumanPlayer)) {
            gameWindowController.appendToLog("It's not your turn yet!");
            return;
        }
        Card drawnCard = mainDeckOfCards.drawCard();
        if(drawnCard.cardType == Card.CardType.EXPLODING_KITTEN) {
            logMoveByPlayer(getPlayerThatHasTurn(), MoveType.DRAW, drawnCard);
            explodingKittenDrawn(player);
            return;
        }
        else {
            logMoveByPlayer(player, MoveType.DRAW, drawnCard);
            player.addCard(drawnCard);
            gameWindowController.updateHumanCardListClickListeners();
        }
        startNextTurn();
    }

    private void explodingKittenDrawn(Player player) {
        player.setHasExplodingKitten(true);
        if (player.hasDefuseCard()) {
           if(player instanceof BotPlayer) {
               playerPlaysCard(player.extractDefuseCard());
           } else {
               gameWindowController.appendToLog("Play your defuse card!!");
               // TODO: configure functionality for human player placing defuse card
           }
        } else {
            if(player instanceof BotPlayer) {
                BotAvatar botAvatar = ((BotPlayer) player).getBotAvatar();
                gameWindowController.removeBotAvatar(botAvatar);
                playersArrayList.remove((BotPlayer)player);
                gameWindowController.appendToLog(player.toString() + " is ded.");
                startNextTurn();
            } else {
                //TODO: GameOver for human player
            }
        }
    }

    public void playerPlaysCard(Card cardToPlay) {
        // TODO: clean this function up
        Player player = getPlayerThatHasTurn();
        if(player.getPlayerNumber() != turnNumber) return;
        if(player instanceof HumanPlayer) {
            if(cardNodeSelected == null) {
                gameWindowController.appendToLog("Please select a card to play!");
                return;
            }
            cardToPlay = (Card) cardNodeSelected;
            cardToPlay.setOnMouseClicked(null);
            resetCardSelection();
        }
        if(cardToPlay.cardType == Card.CardType.DEFUSE) {
            if(player instanceof HumanPlayer) {
                if(!player.isExploding()) {
                    gameWindowController.appendToLog("You can't play that card right now!");
                    gameWindowController.updateHumanCardListClickListeners();
                    return;
                }
                // TODO: configure human player placing kitten back into deck functionality
                gameWindowController.appendToLog("Choose an index to where you want to place the exploding kitten.");
            } else {
                placeKittenRandomlyInDeck();
                player.setHasExplodingKitten(false);
                movePlayedCardToPlayedPile(cardToPlay, player);
                logMoveByPlayer(player, MoveType.PLAY, cardToPlay);
                startNextTurn();
            }
        } else {
            movePlayedCardToPlayedPile(cardToPlay, player);
            logMoveByPlayer(player, MoveType.PLAY, cardToPlay);
            performCardLogic(cardToPlay);
        }

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

    private void resetCardSelection() {
        cardNodeSelected = null;
        gameWindowController.resetcardSelectLabel();
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
        if(getPlayerThatHasTurn() instanceof BotPlayer)   makeRandomBotMove((BotPlayer) getPlayerThatHasTurn());
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
        gameWindowController.setNumCardsInDeckLabel(getNumCardsInDeck());
        turnNumber = (turnNumber + 1) % playersArrayList.size();
        System.out.println("New turn: " + turnNumber);
        Player player = getPlayerThatHasTurn();
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
        playerPlaysCard(cardToPlay);
    }

    private Player getPlayerThatHasTurn() {
//        for (Player player: playersArrayList) {
//            if (player.getPlayerNumber() == turnNumber) {
//                return player;
//            }
//        }
//        return humanPlayer;
        return playersArrayList.get(turnNumber);
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
