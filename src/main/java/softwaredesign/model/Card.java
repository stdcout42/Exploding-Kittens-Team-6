package softwaredesign.model;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Card extends Parent {
    public enum CardType {
        DEFUSE, SKIP, SEE_THE_FUTURE, EXPLODING_KITTEN, REVERSE, SHUFFLE, STEAL;
    }

    public final CardType cardType;
    public Card(CardType cardType) {
        this.cardType = cardType;

        Rectangle cardBg = new Rectangle(80, 100);
        cardBg.setArcWidth(20);
        cardBg.setArcHeight(20);
        cardBg.setFill(Color.ALICEBLUE);

        Text text = new Text(toString());
        text.setWrappingWidth(70);
        getChildren().add(new StackPane(cardBg, text));
    }
    @Override
    public String toString() {
        return cardType.toString();
    }
}
