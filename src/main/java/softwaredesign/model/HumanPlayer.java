package softwaredesign.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;

public class HumanPlayer extends Player{
    private ObservableList<Node> cardList;

    public boolean addCard(Card card) {
        return this.cardList.add(card);
    }

    public boolean removeCard(Card card) {
        return this.cardList.remove(card);
    }
}
