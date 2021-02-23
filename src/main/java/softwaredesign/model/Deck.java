package softwaredesign.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    private List<Card> DeckPile = new ArrayList<Card>();
    public Card drawCard(){
        return DeckPile.remove(0);
    }
    public ArrayList<Card> seeTopThreeCards(){
        ArrayList<Card> topThreeCards = new ArrayList<Card>();
        topThreeCards.add(DeckPile.get(0));
        topThreeCards.add(DeckPile.get(1));
        topThreeCards.add(DeckPile.get(2));
        return topThreeCards;
    }

    public void addToDeck(Card card){
        DeckPile.add(card);
    }
    public void placeCardAtIndex(int index, Card card) {
        DeckPile.add(index,card);
    }
    public void shuffleDeck() {
        Collections.shuffle(DeckPile);
    }
}
