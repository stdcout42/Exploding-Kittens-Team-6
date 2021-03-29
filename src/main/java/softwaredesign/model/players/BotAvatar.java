package softwaredesign.model.players;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BotAvatar extends Parent {
    private final int botNumber;
    private int numOfCards;
    private final Text numberOfCardsText;

    public BotAvatar(int botNumber) {
        this.botNumber = botNumber;
        this.numOfCards = 0;
        numberOfCardsText = new Text("Cards: " + numOfCards);
        makeBotRectangle();

    }

    private void makeBotRectangle() {
        Rectangle botRectangle = new Rectangle(80, 80);
        botRectangle.setArcWidth(20);
        botRectangle.setArcHeight(20);
        botRectangle.setFill(Color.ORANGE);

        Text playerName = new Text(toString());
        playerName.setWrappingWidth(70);
        getChildren().add(new VBox(new StackPane(botRectangle, playerName), numberOfCardsText));
    }

    @Override
    public String toString() {
        return "Player " + this.botNumber;
    }

    public void incNumOfCards() {
        numOfCards++;
        numberOfCardsText.setText("Cards: " + numOfCards);
    }
    public void decNumOfCards(){
        numOfCards--;
        numberOfCardsText.setText("Cards: " + numOfCards);
    }
}
