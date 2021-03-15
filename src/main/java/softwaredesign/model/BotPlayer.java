package softwaredesign.model;

import java.util.ArrayList;
import java.util.Random;

public class BotPlayer extends Player{
    private ArrayList<Card> cardList;
    private BotAvatar botAvatar;

    public BotPlayer(int playerNumber) {
        super(playerNumber);
        cardList = new ArrayList<>();
        botAvatar = new BotAvatar(playerNumber);
    }

    public void addCard(Card card) {
       this.cardList.add(card);
       botAvatar.incNumOfCards();
    }

    public void removeCard(Card card) {
        this.cardList.remove(card);
        botAvatar.decNumOfCards();
    }

    @Override
    public boolean hasDefuseCard() {
        for(Card card : cardList) {
            if(card.cardType == Card.CardType.DEFUSE) return true;
        }
        return false;
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

    public Card extractDefuseCard() {
        Card defuseCard = null;
        for (Card card: cardList)
            if(card.cardType == Card.CardType.DEFUSE) {
                defuseCard = card;
                cardList.remove(card);
                break;
            }
        return defuseCard;
    }

    @Override
    public boolean hasCards() {
        return !cardList.isEmpty();
    }

    @Override
    public Card extractRandomCard() {
        botAvatar.decNumOfCards();
        return cardList.remove(new Random().nextInt(cardList.size()));
    }
}
