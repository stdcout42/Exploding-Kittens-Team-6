package softwaredesign;
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
    private BotPlayer botPlayer;
    private ArrayList <BotPlayer> botPlayerArray = new ArrayList<BotPlayer>();


    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers > 1 && numberOfPlayers < 6)
            this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setupPlayers () {
        humanPlayer = new HumanPlayer();
        for (int i = 0; i < getNumberOfPlayers() -1; i++) {
            botPlayerArray.add(new BotPlayer());
        }
    }

    public void dealCards(){
        int numberOfCardsInHand = 5;

        humanPlayer.addCard(new Card(Card.CardType.DEFUSE));
        for (int i = 0; i < getNumberOfPlayers() -1; i++) {
            botPlayerArray.get(i).addCard(new Card(Card.CardType.DEFUSE));
        }

        for (int i = 0; i < numberOfCardsInHand -1; i++) {
            humanPlayer.addCard(mainDeckOfCards.drawCard());
            for (int j = 0; j < getNumberOfPlayers() -1; j++) {
                botPlayerArray.get(j).addCard(mainDeckOfCards.drawCard());
            }
        }
    }

    public void setupGame(){
        mainDeckOfCards = new Deck();
        for (int i = 0; i < Card.CardType.values().length -2; i++) { // length -2 as defuse and exploding kittens are the last two cards in the Cardtype enum.
            for (int j = 0; j < Card.CardType.getNumOfCards(Card.CardType.values()[i]); j++) {
                mainDeckOfCards.addToDeck(new Card(Card.CardType.values()[i]));
            }
        }
        mainDeckOfCards.shuffleDeck();
        dealCards();
        for (int i = 0; i < Card.CardType.getNumOfCards(Card.CardType.DEFUSE) - numberOfPlayers; i++) {
            mainDeckOfCards.addToDeck(new Card(Card.CardType.DEFUSE));
        }
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            mainDeckOfCards.addToDeck(new Card(Card.CardType.EXPLODING_KITTEN));
        }
    }
}
