package softwaredesign.gamelogic.cards;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import softwaredesign.gamelogic.commands.Command;

public abstract class Card extends Parent {
    protected Command command;
    protected Rectangle cardRectangle;

    protected Card(Command command) {
        this.command = command;
        cardRectangle = new Rectangle(80, 100);
        cardRectangle.setArcWidth(20);
        cardRectangle.setArcHeight(20);

        Text text = new Text(toString());
        text.setWrappingWidth(70);
        getChildren().add(new StackPane(cardRectangle, text));
    }


    public void executeCommand() {
        command.execute();
    }
    public abstract String toString();

}
