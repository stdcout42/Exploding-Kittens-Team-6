package softwaredesign.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private List<Card> deckPile;

    public Deck() {
        deckPile = new ArrayList<>();
        addPrePlayerSetupCards();
        shuffleDeck();
    }

    private void addPrePlayerSetupCards() {
        for (int i = 0; i < Card.CardType.values().length -2; i++) { // length -2 as defuse and exploding kittens are the last two cards in the Cardtype enum.
            for (int j = 0; j < Card.CardType.getNumOfCards(Card.CardType.values()[i]); j++) {
                deckPile.add(new Card(Card.CardType.values()[i]));
            }
        }
    }

    public Card drawCard(){
        return deckPile.remove(0);
    }
    public List<Card> seeTopThreeCards(){
        ArrayList<Card> topThreeCards = new ArrayList<>();
        for(int i = 0; i < 3 && i < deckPile.size(); i++) topThreeCards.add(deckPile.get(i));
        return topThreeCards;
    }

    public void addToDeck(Card card){
        deckPile.add(card);
    }
    public void placeCardAtIndex(int index, Card card) {
        if(index >= deckPile.size()) deckPile.add(card);
        else deckPile.add(index,card);
    }
    public void shuffleDeck() {
        Collections.shuffle(deckPile);
    }

    public void addPostPlayerSetupCards(int numberOfPlayers) {
        for (int i = 0; i < Card.CardType.getNumOfCards(Card.CardType.DEFUSE) - numberOfPlayers; i++) {
            deckPile.add(new Card(Card.CardType.DEFUSE));
        }
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            deckPile.add(new Card(Card.CardType.EXPLODING_KITTEN));
        }
    }

    public int getNumOfCards() {
        return deckPile.size();
    }
}
