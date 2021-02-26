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
    private boolean reverse;

    public void humanPlacesKittenAt(int index) {
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if(playerThatHasTurn instanceof HumanPlayer && playerThatHasTurn.isExploding()) {
            mainDeckOfCards.placeCardAtIndex(index, new Card(Card.CardType.EXPLODING_KITTEN));
            playerThatHasTurn.setHasExplodingKitten(false);
            gameWindowController.appendToLog("You've placed the kitten in position: " + index);
            startNextTurn();
        }
        else gameWindowController.appendToLog("You can't do that yet!");
    }

    public void humanStealsFromBot(Node botAvatar) {
        Player player = getPlayerThatHasTurn();
        Card stolenCard;
        if(player instanceof HumanPlayer && ((HumanPlayer) player).getIsStealing()) {
            BotPlayer botPlayer = getBotFromAvatar(botAvatar);
            stolenCard = botPlayer.extractRandomCard();
            player.addCard(stolenCard);
            ((HumanPlayer) player).setIsStealing(false);
            resetBotNodeSelected();
            gameWindowController.updateHumanCardListClickListeners();
            gameWindowController.appendToLog("How sneaky. You stole a " + stolenCard.toString() + " card!");
        }
    }

    private BotPlayer getBotFromAvatar(Node botAvatar) {
        for (Player player : playerList) {
            if(player instanceof BotPlayer && ((BotPlayer) player).getBotAvatar() == botAvatar) {
                return (BotPlayer) player;
            }
        }
        return (BotPlayer) playerList.get(1);
    }

    private enum MoveType {
        PLAY, DRAW
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers > 1 && numberOfPlayers < 6)
            this.numberOfPlayers = numberOfPlayers;
    }

    public void setupGame(GameWindowController controller){
        this.gameWindowController = controller;
        reverse = false;
        mainDeckOfCards = new Deck();
        cardNodeSelected = null;
        turnNumber = 0;
        setupPlayers();
        dealCards();
        gameWindowController.updateHumanCardListClickListeners();
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
        if (player instanceof BotPlayer) {
            if (player.hasDefuseCard()) {
                playerPlaysCard(player.extractDefuseCard(), false);
            } else killBot(player);
        } else {
            if(player.hasDefuseCard()) {
                gameWindowController.appendToLog("Play your defuse card!!");
            } else {
                // TODO: GameOver for human player
            }
        }
    }

    private void killBot(Player player) {
        BotAvatar botAvatar = ((BotPlayer) player).getBotAvatar();
        gameWindowController.removeBotAvatar(botAvatar);
        playerList.remove((BotPlayer) player);
        gameWindowController.appendToLog(player.toString() + " is ded.");
        startNextTurn();
    }

    public void playerPlaysCard(Card card, boolean isHuman) {
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if(isHuman && playerThatHasTurn instanceof BotPlayer) {
            gameWindowController.appendToLog("It's not your turn yet!");
            return;
        }

        if(isHuman) {
            if(cardNodeSelected == null) {
                gameWindowController.appendToLog("Please select a card to play!");
                return;
            }
            card = getCardSelectedNodeAndResetNode();
            if(playerThatHasTurn.isExploding() && card.cardType != Card.CardType.DEFUSE) {
                gameWindowController.appendToLog("You can't play that, you're on fire!!");
                return;
            }
        }
        if(card.cardType == Card.CardType.DEFUSE)
            playerPlaysDefuseCard(playerThatHasTurn, card);
        else {
            movePlayedCardToPlayedPile(card, playerThatHasTurn);
            logMoveByPlayer(playerThatHasTurn, MoveType.PLAY, card);
            performCardLogic(card);
        }
    }

    private void playerPlaysDefuseCard(Player playerThatHasTurn, Card card) {
        if(playerThatHasTurn instanceof HumanPlayer) {
            if(!playerThatHasTurn.isExploding()) {
                gameWindowController.appendToLog("You can't play that card right now!");
                gameWindowController.updateHumanCardListClickListeners();
                return;
            }
            // Played played a valid defuse card
            gameWindowController.appendToLog("Choose an index to where you want to place the exploding kitten.");
            movePlayedCardToPlayedPile(card, playerThatHasTurn);
        } else {
            placeKittenRandomlyInDeck();
            playerThatHasTurn.setHasExplodingKitten(false);
            movePlayedCardToPlayedPile(card, playerThatHasTurn);
            logMoveByPlayer(playerThatHasTurn, MoveType.PLAY, card);
            startNextTurn();
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
        int numCardsInDeck = getNumCardsInDeck();
        if(numCardsInDeck == 0) mainDeckOfCards.addToDeck(new Card(Card.CardType.EXPLODING_KITTEN));
        else {
            int index = new Random().nextInt(numCardsInDeck);
            mainDeckOfCards.placeCardAtIndex(index, new Card(Card.CardType.EXPLODING_KITTEN));
            gameWindowController.appendToLog("Exploding kitten has been placed back into the deck!");
        }
    }

    private void resetCardSelected() {
        cardNodeSelected = null;
        gameWindowController.resetcardSelectLabel();
    }

    private void resetBotNodeSelected() {
        gameWindowController.resetBotSelectLabel();
    }



    private void performCardLogic(Card cardPlayed) {
        switch (cardPlayed.cardType) {
            case REVERSE:
                flipDirection();
                startNextTurn();
                return;
            case SKIP:
                startNextTurn();
                return;
            case SHUFFLE:
                mainDeckOfCards.shuffleDeck();
                break;
            case SEE_THE_FUTURE:
                playerPlayedSeeFuture();
                break;
            case STEAL:
                playerPlayedSteal();
                break;
        }
        if(getPlayerThatHasTurn() instanceof BotPlayer) makeRandomBotMove((BotPlayer) getPlayerThatHasTurn());
    }

    private void playerPlayedSteal() {
        Player player = getPlayerThatHasTurn();
        Card stolenCard = null;
        Player playerToDrawFrom = player;
        if(player instanceof BotPlayer) {
            while(playerToDrawFrom == player || !playerToDrawFrom.hasCards())
                playerToDrawFrom = playerList.get(new Random().nextInt(playerList.size()));

            stolenCard = playerToDrawFrom.extractRandomCard();
            if(playerToDrawFrom instanceof HumanPlayer)
                gameWindowController.appendToLog(player.toString() + " stole your " + stolenCard.toString() + " card!");
             else
                gameWindowController.appendToLog(player.toString() + " steals a card from " + playerToDrawFrom);
            player.addCard(stolenCard);
        } else {
            gameWindowController.appendToLog("Select a player to steal a card from!");
            ((HumanPlayer) player).setIsStealing(true);
        }

    }

    private void playerPlayedSeeFuture() {
        Player player = getPlayerThatHasTurn();
        if(player instanceof HumanPlayer) {
            logTopThreeCards();
        }
    }

    private void logTopThreeCards() {
        gameWindowController.appendToLog("Top three cards are:");
        for (Card card : mainDeckOfCards.seeTopThreeCards()) {
            gameWindowController.appendToLog(card.toString());
        }
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
        turnNumber = (reverse ? (turnNumber - 1) : (turnNumber + 1)) % playerList.size();
        turnNumber = (turnNumber < 0 ? turnNumber + playerList.size() : turnNumber);
        Player player = getPlayerThatHasTurn();
        if(player instanceof BotPlayer) makeRandomBotMove((BotPlayer) player);
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

    private void flipDirection() {
        reverse = !reverse;
    }

    public int getNumCardsInDeck() {
       return mainDeckOfCards.getNumOfCards();
    }
}
