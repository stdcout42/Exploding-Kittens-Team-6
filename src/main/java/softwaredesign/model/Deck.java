package softwaredesign.model;

import softwaredesign.model.cards.Card;
import softwaredesign.model.cards.CardFactory;
import softwaredesign.model.cards.CardType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final List<Card> deckPile;
    private final CardFactory cardFactory;

    public Deck(CardFactory cardFactory) {
        this.cardFactory = cardFactory;
        deckPile = new ArrayList<>();
        addPrePlayerSetupCards();
        shuffleDeck();
    }

    private void addPrePlayerSetupCards() {
        for (int i = 0; i < CardType.values().length -2; i++) { // length -2 as defuse and exploding kittens are the last two cards in the Cardtype enum.
            for (int j = 0; j < CardType.getNumOfCards(CardType.values()[i]); j++) {
                deckPile.add(cardFactory.getCard(CardType.values()[i]));
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
        for (int i = 0; i < CardType.getNumOfCards(CardType.DEFUSE) - numberOfPlayers; i++) {
            deckPile.add(cardFactory.getCard((CardType.DEFUSE)));
        }
        for (int i = 0; i < numberOfPlayers - 1; i++) {
            deckPile.add(cardFactory.getCard(CardType.EXPLODING_KITTEN));
        }
    }

    public int getNumOfCards() {
        return deckPile.size();
    }
}
