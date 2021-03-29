package softwaredesign.model.players;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import softwaredesign.model.cards.Card;
import softwaredesign.model.cards.DefuseCard;

import java.util.Random;

public class HumanPlayer extends Player{
    private final ObservableList<Node> obvservableNodeList;
    private boolean playedDefuseCard;
    private boolean isStealing;

    public HumanPlayer(int playerNumber, ObservableList<Node> obvservableNodeList) {
        super(playerNumber);
        this.obvservableNodeList = obvservableNodeList;
        isStealing = false;
        playedDefuseCard = false;
    }

    public void addCard(Card card)
    {
        this.obvservableNodeList.add(card);
    }

    public void removeCard(Card card)
    {
        this.obvservableNodeList.remove(card);
    }

    @Override
    public boolean hasDefuseCard() {
        for(Node cardNode : obvservableNodeList) {
            if(cardNode instanceof DefuseCard) return true;
        }
        return false;
    }

    @Override
    public Card extractRandomCard() {
        return (Card) obvservableNodeList.remove(new Random().nextInt(obvservableNodeList.size()));
    }

    @Override
    public Card extractDefuseCard() {
        Card defuseCard = null;
        for (Node nodeCard: obvservableNodeList) {
            Card card = (Card) nodeCard;
            if (card instanceof DefuseCard) {
                defuseCard = card;
                obvservableNodeList.remove(card);
                break;
            }
        }
        return defuseCard;
    }

    @Override
    public boolean hasCards() {
        return !obvservableNodeList.isEmpty();
    }

    public void setIsStealing(boolean stealing) {this.isStealing = stealing;}

    public boolean getIsStealing() {
        return isStealing;
    }

    public boolean getPlayedDefuseCard() {
        return playedDefuseCard;
    }

    public void setPlayedDefuseCard(boolean playedDefuseCard) {
        this.playedDefuseCard = playedDefuseCard;
    }
}
