package softwaredesign.model;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Card extends Parent {
    public enum CardType {
        SKIP, SEE_THE_FUTURE, REVERSE, SHUFFLE, STEAL, DEFUSE, EXPLODING_KITTEN;

        public static int getNumOfCards(CardType cardType){
            switch (cardType) {
                case SKIP:
                    return 6;
                case SEE_THE_FUTURE:
                    return 6;
                case REVERSE:
                    return 6;
                case SHUFFLE:
                    return 6;
                case STEAL:
                    return 6;
                case DEFUSE:
                    return 6;
                case EXPLODING_KITTEN:
                    return 4;
                default:
                    throw new IllegalStateException("Unexpected value: " + cardType);
            }
        }
    }

    private Rectangle cardRectangle;
    public final CardType cardType;
    public Card(CardType cardType) {
        this.cardType = cardType;

        cardRectangle = new Rectangle(80, 100);
        cardRectangle.setArcWidth(20);
        cardRectangle.setArcHeight(20);
        cardRectangle.setFill(Color.GREENYELLOW);

        Text text = new Text(toString());
        text.setWrappingWidth(70);
        getChildren().add(new StackPane(cardRectangle, text));
    }
    @Override
    public String toString() {
        return cardType.toString();
    }
}
