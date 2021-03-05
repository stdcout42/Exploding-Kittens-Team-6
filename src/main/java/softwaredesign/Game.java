package softwaredesign;

import javafx.scene.Node;
import softwaredesign.controller.GameWindowController;
import softwaredesign.model.*;

import java.util.ArrayList;
import java.util.Random;

public class Game{
    private int numberOfPlayers;
    private Deck mainDeckOfCards;
    private ArrayList <Player> playerList;
    private int turnNumber;
    private GameWindowController gameWindowController;
    private Node cardNodeSelected;
    private boolean reverse;
    private boolean gameover;

    public void setupGame(GameWindowController controller){
        this.gameWindowController = controller;
        reverse = false;
        mainDeckOfCards = new Deck();
        cardNodeSelected = null;
        gameover = false;
        turnNumber = 0;
        setupPlayers();
        dealCards();
        gameWindowController.updateHumanCardListClickListeners();
        mainDeckOfCards.addPostPlayerSetupCards(numberOfPlayers);
    }

    public void playerDraws(boolean isHumanPlayer) {
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if(isHumanPlayer && !humanIsAllowedToDraw(playerThatHasTurn)) return;

        Card drawnCard = mainDeckOfCards.drawCard();
        logMoveByPlayer(getPlayerThatHasTurn(), MoveType.DRAW, drawnCard);

        if(drawnCard.cardType == Card.CardType.EXPLODING_KITTEN) {
            playerDrawsKitten(playerThatHasTurn);
            return;
        } else {
            playerThatHasTurn.addCard(drawnCard);
        }
        if(isHumanPlayer) gameWindowController.updateHumanCardListClickListeners();
        startNextTurn();
    }

    public void humanPlacesKittenAt(int index) {
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if(playerThatHasTurn instanceof HumanPlayer && ((HumanPlayer) playerThatHasTurn).getPlayedDefuseCard()) {
            mainDeckOfCards.placeCardAtIndex(index, new Card(Card.CardType.EXPLODING_KITTEN));
            gameWindowController.appendToLog("You've placed the kitten in position: " + index);
            ((HumanPlayer) playerThatHasTurn).setPlayedDefuseCard(false);
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

    public void playerPlaysCard(Card card, boolean isHuman) {
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if(isHuman) {
            if(!humanIsAllowedToPlayCard()) return;
            card = getCardSelectedNodeAndResetNode();
        } else if (!botIsAllowedToPlayCard(card)) return;
        performCardLogic(card, playerThatHasTurn);
    }


    public void setNumberOfPlayers(int numberPlayers) {
        if(numberPlayers < 6)
            this.numberOfPlayers = 1 + numberPlayers;
    }

    public void setCardNodeSelected(Node cardNode) {
        this.cardNodeSelected = cardNode;
    }

    public int getNumCardsInDeck() {
        return mainDeckOfCards.getNumOfCards();
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

    private boolean humanIsAllowedToDraw(Player playerThatHasTurn) {

        if (playerThatHasTurn instanceof BotPlayer) {
            gameWindowController.appendToLog("It's not your turn yet!");
            return false;
        }
        if(playerThatHasTurn.getIsExploding()) {
            gameWindowController.appendToLog("You're exploding, you can't draw a card right now!");
            return false;
        }
        return true;
    }

    private void playerDrawsKitten(Player player) {
        if(gameover){
            return;
        }
        player.setIsExploding(true);
        if (player instanceof BotPlayer) {
            if (player.hasDefuseCard()) {
                playerPlaysCard(player.extractDefuseCard(), false);
            } else killBot(player);
        } else {
            if(player.hasDefuseCard()) {
                gameWindowController.appendToLog("Play your defuse card!!");
            } else {
                gameover = true;
                gameWindowController.appendToLog("GAME OVER");
            }
        }
    }

    private void killBot(Player player) {
        BotAvatar botAvatar = ((BotPlayer) player).getBotAvatar();
        gameWindowController.removeBotAvatar(botAvatar);
        playerList.remove(player);
        gameWindowController.appendToLog(player.toString() + " is ded.");
        startNextTurn();
    }

    private boolean botIsAllowedToPlayCard(Card card) {
        if(gameover){
            return false;
        }
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if (card.cardType == Card.CardType.DEFUSE && !playerThatHasTurn.getIsExploding()) return false;
        if (card.cardType == Card.CardType.STEAL && !otherPlayersHaveCards(false)) return false;

        return true;
    }

    private boolean humanIsAllowedToPlayCard() {
        if(gameover){
            return false;
        }
        Player playerThatHasTurn = getPlayerThatHasTurn();
        if (playerThatHasTurn instanceof BotPlayer) {
            gameWindowController.appendToLog("It's not your turn yet!");
            return false;
        }
        if (cardNodeSelected == null) {
            gameWindowController.appendToLog("Please select a card to play!");
            return false;
        }
        // Above condition passed therefore a cardNode has been selected
        Card card = (Card) cardNodeSelected;
        if (playerThatHasTurn.getIsExploding() && card.cardType != Card.CardType.DEFUSE) {
            gameWindowController.appendToLog("You can't play that, you're on fire!!");
            return false;
        }
        if (card.cardType == Card.CardType.STEAL && !otherPlayersHaveCards(true)) {
            gameWindowController.appendToLog("Other players don't have any cards you can steal.");
            return false;
        }
        if (card.cardType == Card.CardType.DEFUSE) {
            if (!playerThatHasTurn.getIsExploding()) {
                gameWindowController.appendToLog("You can't play that card right now!");
                return false;
            }
        }
        return true;
    }

    private boolean otherPlayersHaveCards(boolean currPlayerIsHuman) {
        if(currPlayerIsHuman) {
            for (Player otherPlayer : playerList) {
                if(otherPlayer instanceof BotPlayer && otherPlayer.hasCards())
                    return true;
            }
        } else {
            Player player = getPlayerThatHasTurn();
            for (Player otherPlayer : playerList) {
                if(player != otherPlayer && otherPlayer.hasCards()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void playerPlaysDefuseCard(Player playerThatHasTurn) {
        if(gameover){
            return;
        }
        logMoveByPlayer(playerThatHasTurn, MoveType.PLAY, new Card(Card.CardType.DEFUSE));
        if(playerThatHasTurn instanceof HumanPlayer) {
            // Played played a valid defuse card
            ((HumanPlayer)playerThatHasTurn).setPlayedDefuseCard(true);
            gameWindowController.appendToLog("Choose an index to where you want to place the exploding kitten.");
        } else {
            placeKittenRandomlyInDeck();
            startNextTurn();
        }
        playerThatHasTurn.setIsExploding(false);
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

    private void performCardLogic(Card cardPlayed, Player playerThatHasTurn) {
        switch (cardPlayed.cardType) {
            case REVERSE:
                flipDirection();
                break;
            case SKIP:
                break;
            case SHUFFLE:
                mainDeckOfCards.shuffleDeck();
                break;
            case SEE_THE_FUTURE:
                playerPlayedSeeFuture();
                break;
            case STEAL:
                playerPlayedSteal();
                break;
            case DEFUSE:
                playerPlaysDefuseCard(playerThatHasTurn);
                break;
        }
        movePlayedCardToPlayedPile(cardPlayed, playerThatHasTurn);
        if(cardPlayed.cardType != Card.CardType.SEE_THE_FUTURE
                && cardPlayed.cardType != Card.CardType.STEAL && cardPlayed.cardType != Card.CardType.DEFUSE)
            logMoveByPlayer(playerThatHasTurn, MoveType.PLAY, cardPlayed);
        if(cardPlayed.cardType == Card.CardType.REVERSE || cardPlayed.cardType == Card.CardType.SKIP) {
            startNextTurn();
            return;
        }
        if(getPlayerThatHasTurn() instanceof BotPlayer) makeRandomBotMove((BotPlayer) getPlayerThatHasTurn());
    }

    private void playerPlayedSteal() {
        Player player = getPlayerThatHasTurn();
        Card stolenCard;
        Player playerToDrawFrom = player;
        logMoveByPlayer(player, MoveType.PLAY, new Card(Card.CardType.STEAL));
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
        gameWindowController.updateHumanCardListClickListeners();
    }

    private void playerPlayedSeeFuture() {
        Player player = getPlayerThatHasTurn();
        logMoveByPlayer(player, MoveType.PLAY, new Card(Card.CardType.SEE_THE_FUTURE));
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
        else {
            if(playerList.size() == 1){
                gameover = true;
                gameWindowController.appendToLog("GAME WON");
                return;
            }
            gameWindowController.updateHumanCardListClickListeners();
            gameWindowController.appendToLog("It's your turn!");
        }
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

    private void logMoveByPlayer(Player player, MoveType moveType, Card card) {
        String playerTitle = (player instanceof HumanPlayer ? "You" : player.toString());
        if(moveType == MoveType.PLAY) gameWindowController.appendToLog(playerTitle + " play(s) a " + card.toString() + " card.");
        else gameWindowController.appendToLog(playerTitle + " draw(s) a " + card.toString() + " card.");
    }

    private void flipDirection() {
        reverse = !reverse;
    }
}
