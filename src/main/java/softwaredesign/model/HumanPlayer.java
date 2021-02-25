package softwaredesign.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class HumanPlayer extends Player{
    private ObservableList<Node> cardList;

    public HumanPlayer(int playerNumber, ObservableList<Node> cardList) {
        super(playerNumber);
        this.cardList = cardList;
    }

    public void addCard(Card card)
    {
        this.cardList.add(card);
    }

    public void removeCard(Card card)
    {
        this.cardList.remove(card);
    }

    @Override
    public boolean hasDefuseCard() {
        for(Node cardNode : cardList) {
            if(((Card) cardNode).cardType == Card.CardType.DEFUSE) return true;
        }
        return false;
    }
}
