package softwaredesign.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Deck {
    List<Card> DeckPile = new ArrayList<Card>(35);
    public Card drawCard(){
        return DeckPile.get(0);
    }
    public Card seeCard(int index){
        return DeckPile.get(index);
    }
    public void placeCard(int index, Card card) {
        DeckPile.add(index,card);
    }
    public void shuffleDeck() {
        Collections.shuffle(DeckPile);
    }
}
