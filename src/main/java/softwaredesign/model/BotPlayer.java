package softwaredesign.model;

import java.util.ArrayList;

public class BotPlayer extends Player{
    private ArrayList<Card> cardList = null;

    public BotPlayer(ArrayList list)
    {
        cardList= list;
    }

    public void addCard(Card card)
    {
       this.cardList.add(card);
    }

    public void removeCard(Card card)
    {
        this.cardList.remove(card);
    }
}
