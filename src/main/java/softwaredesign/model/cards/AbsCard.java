package softwaredesign.model.cards;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import softwaredesign.gamelogic.Command;
import softwaredesign.gamelogic.Game;

public abstract class AbsCard extends Parent {
    protected Command command;

    protected AbsCard() {

        Rectangle cardRectangle = new Rectangle(80, 100);
        cardRectangle.setArcWidth(20);
        cardRectangle.setArcHeight(20);
        cardRectangle.setFill(Color.GREENYELLOW);

        Text text = new Text(toString());
        text.setWrappingWidth(70);
        getChildren().add(new StackPane(cardRectangle, text));
    }

    public abstract String toString();
}
