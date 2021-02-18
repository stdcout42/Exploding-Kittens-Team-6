package softwaredesign.model;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Card extends Parent {
    public enum CardType {
        DEFUSE, SKIP, SEE_THE_FUTURE, EXPLODING_KITTEN, REVERSE, SHUFFLE, STEAL;
    }

    public Rectangle getCardRectangle() {
        return cardRectangle;
    }

    private Rectangle cardRectangle;
    public final CardType cardType;
    public Card(CardType cardType) {
        this.cardType = cardType;

        cardRectangle = new Rectangle(80, 100);
        cardRectangle.setArcWidth(20);
        cardRectangle.setArcHeight(20);
        cardRectangle.setFill(Color.ALICEBLUE);


        Text text = new Text(toString());
        text.setWrappingWidth(70);
        getChildren().add(new StackPane(cardRectangle, text));
    }
    @Override
    public String toString() {
        return cardType.toString();
    }
}
