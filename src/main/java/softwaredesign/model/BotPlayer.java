package softwaredesign.model;

import java.util.ArrayList;

public class BotPlayer extends Player{
    private ArrayList<Card> cardList;

    public boolean addCard(Card card) {
        return this.cardList.add(card);
    }

    public boolean removeCard(Card card) {
        return this.cardList.remove(card);
    }
}
