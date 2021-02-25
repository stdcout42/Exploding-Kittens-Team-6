package softwaredesign.model;

import java.util.ArrayList;

public class BotPlayer extends Player{
    private ArrayList<Card> cardList;
    private BotAvatar botAvatar;

    public BotPlayer(int playerNumber)
    {
        super(playerNumber);
        cardList = new ArrayList<>();
        botAvatar = new BotAvatar(playerNumber);
    }

    public void addCard(Card card)
    {
       this.cardList.add(card);
       botAvatar.incNumOfCards();
    }

    public void removeCard(Card card)
    {

        this.cardList.remove(card);
        botAvatar.decNumOfCards();
    }


    public BotAvatar getBotAvatar() {
        return botAvatar;
    }

    public Card getNonDefuseCard() {
        for (Card card: cardList) {
            if(card.cardType != Card.CardType.DEFUSE) return card;
        }
        return null;
    }

    public boolean hasNonDefuseCards() {
        for (Card card: cardList) {
            if(card.cardType != Card.CardType.DEFUSE) return true;
        }
        return false;
    }
}
