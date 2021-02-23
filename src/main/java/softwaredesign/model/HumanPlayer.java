package softwaredesign.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class HumanPlayer extends Player{
    private ObservableList<Node> cardList;

    public void addCard(Card card)
    {
        this.cardList.add(card);
    }

    public void removeCard(Card card)
    {
        this.cardList.remove(card);
    }
}
