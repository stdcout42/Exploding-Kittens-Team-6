package softwaredesign.model;

import java.util.ArrayList;

public class BotPlayer extends Player{
    private ArrayList<Card> cardList;

    public BotPlayer(int playerNumber)
    {
        super(playerNumber);
        cardList = new ArrayList<>();
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
