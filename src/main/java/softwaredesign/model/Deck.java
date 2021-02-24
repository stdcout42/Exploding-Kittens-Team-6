package softwaredesign.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private List<Card> deckPile = new ArrayList<Card>();
    public Card drawCard(){
        return deckPile.remove(0);
    }
    public ArrayList<Card> seeTopThreeCards(){
        ArrayList<Card> topThreeCards = new ArrayList<Card>();
        topThreeCards.add(deckPile.get(0));
        topThreeCards.add(deckPile.get(1));
        topThreeCards.add(deckPile.get(2));
        return topThreeCards;
    }

    public void addToDeck(Card card){
        deckPile.add(card);
    }
    public void placeCardAtIndex(int index, Card card) {
        deckPile.add(index,card);
    }
    public void shuffleDeck() {
        Collections.shuffle(deckPile);
    }
}
