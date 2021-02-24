package softwaredesign.view;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BotAvatar extends Parent {
    private final int botNumber;
    private Rectangle botRectangle;

    public BotAvatar(int botNumber) {
        this.botNumber = botNumber;
        botRectangle = new Rectangle(80, 100);
        botRectangle.setArcWidth(20);
        botRectangle.setArcHeight(20);
        botRectangle.setFill(Color.ORANGE);

        Text text = new Text(toString());
        text.setWrappingWidth(70);
        getChildren().add(new StackPane(botRectangle, text));
    }

    @Override
    public String toString() {
        return "Player " + this.botNumber;
    }
}
