package softwaredesign;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import softwaredesign.model.*;

import java.util.ArrayList;

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
    private ArrayList <BotPlayer> botPlayerArray = new ArrayList<BotPlayer>();
    private int turnNumber;

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

    private void setupPlayers (ObservableList<Node> humanObservableCardList) {
        int playerNumber = 0;
        humanPlayer = new HumanPlayer(playerNumber++, humanObservableCardList);
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            botPlayerArray.add(new BotPlayer(playerNumber++));
        }
    }

    private void dealCards(){
        int numberOfCardsInHand = 5;

        humanPlayer.addCard(new Card(Card.CardType.DEFUSE));
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            botPlayerArray.get(i).addCard(new Card(Card.CardType.DEFUSE));
        }

        for (int i = 0; i < numberOfCardsInHand - 1; i++) {
            humanPlayer.addCard(mainDeckOfCards.drawCard());
            for (int j = 0; j < numberOfPlayers - 1; j++) {
                botPlayerArray.get(j).addCard(mainDeckOfCards.drawCard());
            }
        }
    }

    public void setupGame(ObservableList<Node> observableHumanPlayerList){
        mainDeckOfCards = new Deck();
        turnNumber = 0;
        for (int i = 0; i < Card.CardType.values().length -2; i++) { // length -2 as defuse and exploding kittens are the last two cards in the Cardtype enum.
            for (int j = 0; j < Card.CardType.getNumOfCards(Card.CardType.values()[i]); j++) {
                mainDeckOfCards.addToDeck(new Card(Card.CardType.values()[i]));
            }
        }
        mainDeckOfCards.shuffleDeck();
        setupPlayers(observableHumanPlayerList);
        dealCards();
        for (int i = 0; i < Card.CardType.getNumOfCards(Card.CardType.DEFUSE) - numberOfPlayers; i++) {
            mainDeckOfCards.addToDeck(new Card(Card.CardType.DEFUSE));
        }
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            mainDeckOfCards.addToDeck(new Card(Card.CardType.EXPLODING_KITTEN));
        }
    }

    public Card currentPlayerDraws() {
        Player player = getPlayerThatHasTurn();
        Card drawnCard = mainDeckOfCards.drawCard();
        if(drawnCard.cardType == Card.CardType.EXPLODING_KITTEN) {
            // TODO
        }
        else {
            System.out.println(player.toString() + " draws " + drawnCard.toString());
            player.addCard(drawnCard);
        }
        startNextTurn();
        return drawnCard;
    }

    private void startNextTurn() {
        turnNumber = (turnNumber + 1) % numberOfPlayers;
        System.out.println("Turn number: " + turnNumber);
        Player player = getPlayerThatHasTurn();
        System.out.println(player.toString());
        if(player instanceof BotPlayer) {
            System.out.println("The bots move now");
            makeRandomBotMove();
        }
    }

    private void makeRandomBotMove() {
//        int moveType = new Random().nextInt(2);
//        if(moveType == 0) {
//            // draw
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            playerDraws();
//        } else {
//            // play

//        }

            Thread thread = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    currentPlayerDraws();
                }
            };
            thread.start();

    }

    private Player getPlayerThatHasTurn() {
        for (Player player: botPlayerArray) {
            if (player.getPlayerNumber() == turnNumber) {
                return player;
            }
        }
        return humanPlayer;
    }
}
